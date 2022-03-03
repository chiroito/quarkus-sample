package com.example;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TestEndPoint {

    @Inject
    @RestClient
    BlockingRestClient blockingRestClient;

    @GET
    public Message endpoint() {
        return blockingRestClient.invoke();
    }
}
