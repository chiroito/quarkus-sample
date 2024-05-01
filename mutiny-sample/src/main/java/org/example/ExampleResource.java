package org.example;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.function.BiFunction;

@Path("/hello")
public class ExampleResource {

    /**
     * Uni<String>を返すリモートのREST API
     */
    @Inject
    @RestClient
    MyRemoteService service;

    /**
     * リアクティブな処理の結果を、別のリアクティブな処理の引数にする例
     * @return
     */
    @GET
    @Path("/serial")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> serial() {
        Uni<String> uni1 = service.getMessage("hello");
        return uni1.onItem().transformToUni(uni1Result -> service.getMessage(uni1Result + "こんにちは"));
    }

    /**
     * 複数のリアクティブな処理を同時に実行し、それぞれの結果を足し合わせる例
     * @return
     */
    @GET
    @Path("/parallel")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> parallel() {
        Uni<String> uni1 = service.getMessage("hello");
        Uni<String> uni2 = service.getMessage("こんにちは");
        return Uni.combine().all().unis(uni1, uni2).with(new CombileResult());
    }

    /**
     * 複数のリアクティブな処理の結果を足し合わせる関数
     */
    class CombileResult implements BiFunction<String, String, String>{
        @Override
        public String apply(String o, String o2) {
            return o + o2;
        }
    }
}
