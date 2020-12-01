package com.demo.stock.provider.web.client.mysql;

import com.alibaba.fastjson.JSON;
import com.demo.stock.provider.web.entity.Stock;
import com.demo.stock.provider.web.entity.StockJson;
import com.demo.stock.provider.web.client.StockClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author chenjiahan | chenjiahan@corp.netease.com | 2019/3/25
 **/
public class MysqlClient implements StockClient {

    private StockJsonService service;

    public MysqlClient(StockJsonService service) {
        this.service = service;
    }

    @Override
    public Stock getStockById(String stockId) {
        StockJson stockJson = service.getStockJson(stockId);
        if (stockJson == null) return null;
        return JSON.parseObject(stockJson.getValue(), Stock.class);
    }

    @Override
    public List<Stock> getStockBatchByIds(String stockIds) {
        //do not support
        return new ArrayList<>();
    }

    @Override
    public int order() {
        return 10;
    }
}
