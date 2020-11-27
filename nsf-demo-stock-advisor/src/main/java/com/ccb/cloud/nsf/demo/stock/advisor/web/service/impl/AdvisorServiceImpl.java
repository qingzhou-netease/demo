package com.ccb.cloud.nsf.demo.stock.advisor.web.service.impl;

import com.ccb.cloud.nsf.demo.stock.advisor.web.interceptor.HostHeaderInterceptor;
import com.ccb.cloud.nsf.demo.stock.advisor.web.util.CastKit;
import com.ccb.cloud.nsf.demo.stock.advisor.web.entity.Stock;
import com.ccb.cloud.nsf.demo.stock.advisor.web.exception.AdvisorException;
import com.ccb.cloud.nsf.demo.stock.advisor.web.service.IAdvisorService;
import com.ccb.cloud.nsf.demo.stock.advisor.web.util.StringKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

@Service
public class AdvisorServiceImpl implements IAdvisorService {

    private static Logger log = LoggerFactory.getLogger(AdvisorServiceImpl.class);

    @Autowired
    HostHeaderInterceptor hostHeaderInterceptor;

    @Bean("custom")
    public RestTemplate restTemplate() {
        RestTemplate restTemplate =  new RestTemplate();
        restTemplate.setInterceptors(Arrays.asList(hostHeaderInterceptor));
        return restTemplate;
    }

    @Autowired
    @Qualifier("custom")
    RestTemplate restTemplate;

    @Value("${hot_stock_ids}")
    String hotStockIds;

    @Value("${stock_provider_url}")
    String stockProviderUrl;

    @Value("${stock_viewer_url}")
    String stockViewerrUrl;

    @Value("${instance.version}")
    private String version;

    @Value("${error:false}")
    boolean errorSwitch;

    @Value("${sleep:false}")
    boolean sleepSwitch;

    private int retryCount = -1;

    @Override
    public List<Stock> getHotStocks() throws Exception {

        log.info("getHotStocks is invoked with retry count = " + retryCount);
        retryCount++;

        if (sleepSwitch) {
            Thread.sleep(2000);
        }

        if(errorSwitch){
            throw new AdvisorException();
        }

        List<Stock> stocks = null;
        Object[] params = {};
        if (StringKit.isEmpty(hotStockIds)) return stocks;

        String hotIds = getRecommendStockIds();
        try {
            String finalUrl = stockProviderUrl + "/stocks/" + hotIds;
            String stocksStr = restTemplate.getForObject(finalUrl, String.class, params);
            stocks = CastKit.str2StockList(stocksStr);
            Stock debugInfo = new Stock();
            debugInfo.setId("");
            debugInfo.setName("(第" + retryCount + "次请求)" + "(版本: " + version + ")");
            stocks.add(debugInfo);
            log.info("get hot stocks from {} successful : {}", finalUrl, stocks);
        } catch (Exception e) {
            log.warn("get hot stocks failed", e);
        }

        return stocks;
    }

    /**
     * @return ids separated by comma
     * e.g. xx,yy,zz
     */
    private String getRecommendStockIds() {
        return hotStockIds;
    }

    @Override
    public List<String> batchHi() {

        List<String> results = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            String result = restTemplate.getForObject(stockProviderUrl + "/hi?p=" + i, String.class);
            results.add(result);
        }
        return results;
    }

    @Override
    public String deepInvoke(int times) {

        if (times-- > 0) {
            return restTemplate.getForObject(stockViewerrUrl + "/deepInvoke?times=" + times, String.class);
        }
        return "finish";
    }

    @Override
    public String divide(HttpServletRequest request) {
        // TODO Auto-generated method stub
        List<String> results = new ArrayList<>();

        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        String headerName = null;
        while (headerNames.hasMoreElements()) {
            headerName = headerNames.nextElement();
            headers.add(headerName, request.getHeader(headerName));

            log.info("headerName = " + headerName + " value = " + request.getHeader(headerName));

        }
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(null, headers);

        org.springframework.http.ResponseEntity<String> result = restTemplate.exchange(stockProviderUrl + "/hi?" + request.getQueryString(), HttpMethod.GET, entity, String.class);

        return result.getBody();
    }


}
