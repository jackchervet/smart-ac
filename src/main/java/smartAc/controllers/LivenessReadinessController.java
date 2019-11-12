package smartAc.controllers;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;

@Secured("isAnonymous()")
@Controller("/healthy")
public class LivenessReadinessController {

    @Get("/live")
    @Produces(MediaType.TEXT_PLAIN)
    public String live() {
        return "live";
    }

    @Get("/ready")
    @Produces(MediaType.TEXT_PLAIN)
    public String ready() {
        return "ready";
    }
}
