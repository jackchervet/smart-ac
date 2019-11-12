package smartAc.controllers;
import java.security.Principal;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;

@Secured("isAuthenticated()")
@Controller("/")
public class MessageController {
    @Get("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String index(Principal principal) {
        return "Hello " + principal.getName();
    }

    @Post("/onOff")
    public String onOff(@Body String body) {
        JsonObject obj = new Gson().fromJson(body, JsonObject.class);
        return "State is: " + obj.get("state").getAsString();
    }
}
