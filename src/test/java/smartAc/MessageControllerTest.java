package smartAc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Value;
import io.micronaut.context.env.Environment;
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
        assertEquals(rsp.getStatus(), HttpStatus.OK);
        assertEquals(rsp.body(), "Hello " + user);
    }
}
