package org.example;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

import java.util.List;
import java.util.Set;

/**
 * To use it via injection.
 * <p>
 * {@code
 *
 * @Inject
 * @RestClient MyRemoteService myRemoteService;
 * <p>
 * public void doSomething() {
 * Set<MyRemoteService.Extension> restClientExtensions = myRemoteService.getExtensionsById("io.quarkus:quarkus-hibernate-validator");
 * }
 * }
 */
@RegisterRestClient(baseUri = "http://localhost:8080")
public interface MyRemoteService {

    @GET
    @Path("/sleep/{message}")
    Uni<String> getMessage(@PathParam("message") String message);
}
