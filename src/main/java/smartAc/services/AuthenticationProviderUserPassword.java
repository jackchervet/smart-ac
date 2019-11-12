package smartAc.services;

import io.micronaut.context.annotation.Value;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.UserDetails;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

import javax.inject.Singleton;
import java.util.ArrayList;

@Singleton
public class AuthenticationProviderUserPassword implements AuthenticationProvider  {

    @Value("${micronaut.gcp.user:default}")
    private String user;

    @Value("${micronaut.gcp.password:default}")
    private String pass;

    @Override
    public Publisher<AuthenticationResponse> authenticate(
        AuthenticationRequest authenticationRequest)
    {
        if ("default".equals(user) || "default".equals(pass)) {
            System.out.println("Environment Variable not loaded correctly, cannot authenticate.");
            return Flowable.just(new AuthenticationFailed());
        }

        if (authenticationRequest.getIdentity().equals(user) &&
            authenticationRequest.getSecret().equals(pass) ) {
            return Flowable.just(
                new UserDetails((String) authenticationRequest.getIdentity(), new ArrayList<>()));
        }
        return Flowable.just(new AuthenticationFailed());
    }
}