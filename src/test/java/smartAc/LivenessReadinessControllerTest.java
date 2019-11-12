package smartAc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;

@MicronautTest
public class LivenessReadinessControllerTest {

    @Inject
    @Client("/healthy")
    RxHttpClient client;

    @Test
    public void testLive() {
        HttpRequest request = HttpRequest.GET("/live");
        HttpResponse rsp = client.toBlocking().exchange(request, String.class);
        assertEquals(rsp.getStatus(), HttpStatus.OK);
        assertEquals(rsp.body(), "live");
    }

    @Test
    public void testReady() {
        HttpRequest request = HttpRequest.GET("/ready");
        HttpResponse rsp = client.toBlocking().exchange(request, String.class);
        assertEquals(rsp.getStatus(), HttpStatus.OK);
        assertEquals(rsp.body(), "ready");
    }
}
