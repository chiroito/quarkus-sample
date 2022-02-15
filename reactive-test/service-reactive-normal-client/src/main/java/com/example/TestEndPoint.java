package com.example;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletableFuture;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TestEndPoint {

    @Inject
    @RestClient
    TestClientStr strClient;

    @GET
    public Uni<Message> endpoint1() {
//        return Uni.createFrom().item(strClient.getBlocking()); // NG
        // -Djava.util.concurrent.ForkJoinPool.common.parallelismで並列度を変更する必要がある
        return Uni.createFrom().completionStage(CompletableFuture.supplyAsync(() -> strClient.getBlocking()));
    }
}
