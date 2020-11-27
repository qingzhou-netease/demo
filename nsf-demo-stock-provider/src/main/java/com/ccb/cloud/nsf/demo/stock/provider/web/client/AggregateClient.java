package com.ccb.cloud.nsf.demo.stock.provider.web.client;

import com.ccb.cloud.nsf.demo.stock.provider.web.entity.Stock;

import java.util.Collections;
import java.util.List;

/**
 * @Author chenjiahan | chenjiahan@corp.netease.com | 2019/3/26
 **/
public class AggregateClient implements StockClient {

    private List<StockClient> stockClients;

    public AggregateClient(List<StockClient> stockClients) {
        Collections.sort(stockClients, (o1, o2) -> o2.order() - o1.order());
        this.stockClients = stockClients;
    }

    @Override
    public Stock getStockById(String stockId) throws Exception {

        for (StockClient client : stockClients) {
            Stock stock = client.getStockById(stockId);
            if (stock != null) return stock;
        }
        throw new RuntimeException("can not get stock by clients");
    }

    @Override
    public List<Stock> getStockBatchByIds(String stockIds) throws Exception {

        for (StockClient client : stockClients) {
            List<Stock> stocks = client.getStockBatchByIds(stockIds);
            if (stocks != null && stocks.size() != 0) return stocks;
        }
        throw new RuntimeException("can not get stocks by clients");
    }
}
