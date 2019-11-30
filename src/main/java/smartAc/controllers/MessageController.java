package smartAc.controllers;
import java.security.Principal;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import smartAc.models.State;

@Secured("isAuthenticated()")
@Controller("/")
public class MessageController {
    private State currentState = State.OFF;

    @Get("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String index(Principal principal) {
        return "Hello " + principal.getName();
    }

    @Post("/setState")
    public HttpResponse setState(@Body String body) {
        String stateString = new Gson().fromJson(body, JsonObject.class).get("state").getAsString();
        try {
            this.currentState = State.valueOf(stateString.toUpperCase());
            return HttpResponse.ok("State is: " + currentState.name());
        } catch (IllegalArgumentException | NullPointerException ex) {
            return HttpResponse.status(HttpStatus.UNPROCESSABLE_ENTITY,
                "[ERROR]: Invalid state supplied, no action taken.");
        }
    }

    @Get("/getState")
    public String getState() {
        return this.currentState.name();
    }
}
