package com.example;

import io.quarkus.infinispan.client.Remote;
import org.infinispan.client.hotrod.RemoteCache;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/")
public class ExampleResource {

    @Inject
    @Remote("custom-cache")
    private RemoteCache<Integer, Integer> stockCache;

    @GET
    @Path("/hello/{itemId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String hello(@PathParam("itemId") int itemId) throws Exception {
        int stockedNum = stockCache.getOrDefault(itemId, 0);

        return String.format("商品番号 %d の在庫は %d 個あります", itemId, stockedNum);
    }

    @PUT
    @Path("/hello/{itemId}/{num}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String receiptGoods(@PathParam("itemId")int itemId, @PathParam("num")int num){
        // TODO:APIの例のため以下の様に実装しているが、本来在庫を増減させる場合はServerTaskで実行すること
        stockCache.putIfAbsent(itemId, 0);
        int newStockedNum = stockCache.compute(itemId, (k, v) -> v + num);
        return String.format("商品番号 %d の在庫は %d 個になりました", itemId, newStockedNum);
    }


    @POST
    @Path("/hello/{itemId}/{num}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public OrderResponse order(@PathParam("itemId")int itemId, @PathParam("num")int num) throws Exception {

        Map<String, Object> orderInfo = new HashMap<>(2);
        orderInfo.put("ItemNo", itemId);
        orderInfo.put("Num", num);

        boolean isOrderSuccess = (boolean)this.stockCache.execute("StockAllocationConputeTask", orderInfo, itemId);

        OrderResponse res = new OrderResponse();
        res.itemId = itemId;
        res.num = num;
        res.message = isOrderSuccess ? "注文が成功しました" : "在庫が足りませんでした";

        return res;
    }
}

