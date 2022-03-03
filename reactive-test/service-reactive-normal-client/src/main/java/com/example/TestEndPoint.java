package com.example;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletableFuture;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/")
public class TestEndPoint {

    @Inject
    @RestClient
    BlockingRestClient blockingRestClient;

    @GET
    @Path("/api")
    public Message endpoint1() {
        // メソッドの戻り値をUniにしないことで、このメソッドをブロッキングで実行する
        return blockingRestClient.invoke();
    }

    @GET
    @Path("/annotation/api")
    @Blocking
    public Uni<Message> endpoint2() {
        // メソッドの戻り値はUniを返すが、このメソッドはブロッキングで実行するようアノテーションで明示的に指定する
        return Uni.createFrom().item(blockingRestClient.invoke());
    }

    @GET
    @Path("/completableFuture/api")
    public Uni<Message> endpoint3() {
        // Javaの標準APIを使ってノンブロッキング用のスレッドから非同期処理を実行する
        // 並列度は-Djava.util.concurrent.ForkJoinPool.common.parallelismでチューニングする必要がある
        return Uni.createFrom().completionStage(CompletableFuture.supplyAsync(() -> blockingRestClient.invoke()));
    }

    @GET
    @Path("/blocking/api")
    public Uni<Message> endpointNG() {
        // ノンブロッキング用のスレッドでブロッキング処理を呼ぶ
        return Uni.createFrom().item(blockingRestClient.invoke());
    }
}
