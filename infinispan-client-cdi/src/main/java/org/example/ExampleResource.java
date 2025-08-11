package org.example;

import io.quarkus.infinispan.client.Remote;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.example.cdi.Greeting;
import org.infinispan.client.hotrod.RemoteCache;

@Path("/hello")
public class ExampleResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

    @Inject
    @Remote("test")
    RemoteCache<String, Greeting> cache;

    @GET
    @Path("/cache")
    @Produces(MediaType.TEXT_PLAIN)
    public Greeting cache() {
        cache.put("key", new Greeting("name", "message"));
        return cache.get("key");
    }
}
