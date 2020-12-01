package com.demo.stock.viewer.web.service.impl;

import com.demo.stock.viewer.web.service.IStockService;
import com.demo.stock.viewer.web.entity.Stock;
import com.demo.stock.viewer.web.util.CastKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

@Service
public class StockServiceImpl implements IStockService {

    private static Logger log = LoggerFactory.getLogger(StockServiceImpl.class);

    @Autowired
//    @Qualifier("custom")
    RestTemplate restTemplate;

    @Value("${stock_provider_url}")
    String stockProviderUrl;

    @Value("${stock_advisor_url}")
    String stockAdvisorUrl;

    @Value("${instance.version}")
    private String version;

    @Value("${spring.application.name}")
    String app;

    // 超时
    @Override
    public List<Stock> getStockList(int delay) throws Exception {

        log.info("start to get stock list ...");

        List<Stock> stocks;
        String finalUrl = stockProviderUrl + "/stocks?delay=" + delay;
        String stocksStr = restTemplate.getForObject(finalUrl, String.class);
        stocks = CastKit.str2StockList(stocksStr);
        log.info("get all stocks from {} successful : {}", finalUrl, stocks);
        return stocks;
    }


    @Override
    public Stock getStockById(String stockId) throws Exception {

        Stock stock = null;
        String finalUrl = stockProviderUrl + "/stocks/" + stockId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("stockCode", stockId);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(finalUrl, HttpMethod.GET, entity, String.class);

        stock = CastKit.str2Stock(respEntity.getBody());
        log.info("get stock from {} by {} successful : {}", finalUrl, stockId, stock);
        return stock;
    }


    @Override
    public List<Stock> getHotStockAdvice() throws Exception {

        log.info("start to get hot stock advice ...");
        List<Stock> stocks;
//        counter++;
//        if (counter % 3 != 0) {
//            throw new Exception("cannot not fetch hot advice : connection failed");
//        }
        String finalUrl = stockAdvisorUrl + "/advices/hot";
        String stocksStr = restTemplate.getForObject(finalUrl, String.class);
        stocks = CastKit.str2StockList(stocksStr);
        log.info("get hot stock advice from {} successful : {}", finalUrl, stocks);

        return stocks;
    }

    @Override
    public String echoAdvisor(int times) {
        return doEcho(stockAdvisorUrl, times);
    }

    @Override
    public String echoProvider(int times) {
        return doEcho(stockProviderUrl, times);
    }

    private String doEcho(String url, int times) {
        StringBuilder sBuilder = new StringBuilder();
        url = url + "/echo";
        while (times-- > 0) {
            String result;
            try {
                result = restTemplate.getForObject(url + "?p=" + times, String.class);
            } catch (Exception e) {
                result = e.getMessage() + "\r\n";
            }

            sBuilder.append(result);
        }
        return sBuilder.toString();
    }

    @Override
    public String echoAdvisorWithHeader(int times, String key, String value) {
        StringBuilder sb = new StringBuilder();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(key, value);
        requestHeaders.add("color-chain", String.format("%s:%s", app, version));

        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        String url = stockAdvisorUrl + "/echoWithHeader/" + key + "/" + value + "?p=" + times;

        while (times-- > 0) {
            ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            sb.append(result.getBody());
        }
        return sb.toString();
    }

    @Override
    public String echoProviderWithHeader(int times, String key, String value) {
        StringBuilder sb = new StringBuilder();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(key, value);

        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        String url = stockProviderUrl + "/echoWithHeader/" + key + "/" + value + "?p=" + times;

        while (times-- > 0) {
            ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

            HttpHeaders responseHeaders = result.getHeaders();
            Set<String> headerKeys = responseHeaders.keySet();

            sb.append("------Response headers------" + System.lineSeparator());
            headerKeys.stream().forEach(k -> sb.append(k + ":" + responseHeaders.get(k) + System.lineSeparator()));
            sb.append("------Response body------" + System.lineSeparator());
            sb.append(result.getBody());
        }
        return sb.toString();
    }


    @Override
    public String deepInvoke(int times) {
        if (times-- > 0) {
            return restTemplate.getForObject(stockAdvisorUrl + "/deepInvoke?times=" + times, String.class);
        }
        return "finish";
    }

    @Override
    public String pressProvider() {

        log.info("press for provider");

        String finalUrl = stockProviderUrl + "/stress";
        String result = restTemplate.getForObject(finalUrl, String.class);
        return result;
    }
}
