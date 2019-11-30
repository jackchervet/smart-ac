package smartAc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.HttpRequest;
import io.micronaut.test.annotation.MicronautTest;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@MicronautTest
class MessageControllerTest {
    @Value("${micronaut.gcp.user:default}")
    private String user;

    @Value("${micronaut.gcp.password:default}")
    private String pass;

    @Inject
    @Client("/")
    RxHttpClient client;

    @Test
    public void basicAuth_testFailureThrowsException() {
        assertThrows(HttpClientResponseException.class, () ->
            client.toBlocking().exchange(HttpRequest.GET("/")));
    }

    @Test
    public void basicAuth_testSuccessfulAuth() {
        HttpRequest request = HttpRequest.GET("/")
            .basicAuth(user, pass);
        HttpResponse rsp = client.toBlocking().exchange(request, String.class);
        assertEquals(HttpStatus.OK, rsp.getStatus());
        assertEquals("Hello " + user, rsp.body());
    }

    @Test
    public void setState_testInvalidState() {
        HttpRequest request = HttpRequest.POST("/setState", "{\"state\":\"low\"}")
            .basicAuth(user, pass);
        assertThrows(HttpClientResponseException.class, () ->
            client.toBlocking().exchange(request, String.class),
            "[ERROR]: Invalid state supplied, no action taken.");
    }

    @Test
    public void setState_testValidState() {
        HttpRequest request = HttpRequest.POST("/setState", "{\"state\":\"on\"}")
            .basicAuth(user, pass);
        HttpResponse rsp = client.toBlocking().exchange(request, String.class);
        assertEquals(HttpStatus.OK, rsp.getStatus());
        assertEquals("State is: ON", rsp.body());
    }

    @Test
    public void getState_testValidResponseCode() {
        HttpRequest request = HttpRequest.GET("/getState")
            .basicAuth(user, pass);
        HttpResponse rsp = client.toBlocking().exchange(request, String.class);
        assertEquals(HttpStatus.OK, rsp.getStatus());
    }
}
