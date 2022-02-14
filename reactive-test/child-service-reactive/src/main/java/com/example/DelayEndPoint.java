package com.example;

import io.smallrye.mutiny.Uni;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Duration;

@Path("/delay")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DelayEndPoint {

    @GET
    public Uni<Message> endpoint() throws InterruptedException {
//        TimeUnit.MILLISECONDS.sleep(10);
//        return Uni.createFrom().item(new Message("Hello"));
        return Uni.createFrom().item(new Message("Hello"))
                .onItem().delayIt().by(Duration.ofMillis(10));
    }
}