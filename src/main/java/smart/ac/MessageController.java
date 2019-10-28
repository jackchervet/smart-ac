package smart.ac;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;

@Controller("/")
public class MessageController {
    @Get("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String index() {
        return "Hello World";
    }

    @Post("/onOff")
    public String onOff(@Body String body) {
        JsonObject obj = new Gson().fromJson(body, JsonObject.class);
        return "State is: " + obj.get("state").getAsString();
    }
}
