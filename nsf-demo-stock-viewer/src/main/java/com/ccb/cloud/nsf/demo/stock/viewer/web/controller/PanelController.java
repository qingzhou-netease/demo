package com.ccb.cloud.nsf.demo.stock.viewer.web.controller;

import com.ccb.cloud.nsf.demo.stock.viewer.web.service.IStockService;
import com.ccb.cloud.nsf.demo.stock.viewer.web.entity.HttpResponse;
import com.ccb.cloud.nsf.demo.stock.viewer.web.entity.Stock;
import com.ccb.cloud.nsf.demo.stock.viewer.web.manager.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * @author Chen Jiahan | chenjiahan@corp.netease.com
 */
@Controller
public class PanelController {

    private static Logger log = LoggerFactory.getLogger(PanelController.class);

    @Autowired
    IStockService stockService;

    @GetMapping(value = {"" , "/index"})
    public String indexPage(){
        return "index_non_dubbo";
    }

    @GetMapping(value = "/stocks", produces = "application/json")
    @ResponseBody
    public HttpResponse getStockList(@RequestParam(name = "delay", required = false, defaultValue = "0") int delay) {

        List<Stock> stocks;
        try {
            stocks = stockService.getStockList(delay);
        } catch (Exception e) {
            log.warn("get stock list failed ...");
            log.warn("", e);
            return handleExceptionResponse(e);
        }
        return new HttpResponse(stocks);
    }

    @GetMapping(value = "/advices/hot", produces = "application/json")
    @ResponseBody
    public HttpResponse getHotAdvice() {

        List<Stock> stocks;
        try {
            stocks = stockService.getHotStockAdvice();
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("get hot stock advice failed ...");
            log.warn("", e);
            return handleExceptionResponse(e);
        }
        return new HttpResponse(stocks);
    }

    @GetMapping("/stocks/{stockId}")
    @ResponseBody
    public Stock getStockById(@PathVariable String stockId) {

        Stock stock = null;
        try {
            stock = stockService.getStockById(stockId);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("get stock[{}] info failed ...", stockId);
        }
        return stock;
    }

    @GetMapping("/logs")
    @ResponseBody
    public HttpResponse getHttpLog() {
        return new HttpResponse(LogManager.logs());
    }

    @GetMapping("/logs/clear")
    @ResponseBody
    public HttpResponse clearLogs() {
        LogManager.clear();
        return new HttpResponse("clear logs success");
    }

    @GetMapping("/echo/advisor")
    @ResponseBody
    public HttpResponse echoAdvisor(HttpServletRequest request,
                                    @RequestParam(name = "time", defaultValue = "10", required = false) int time) {
        String result = stockService.echoAdvisor(time);
        LogManager.put(UUID.randomUUID().toString(), result);
        return new HttpResponse(result);
    }

    @GetMapping("/echo/provider")
    @ResponseBody
    public HttpResponse echoProvider(HttpServletRequest request,
                                     @RequestParam(name = "time", defaultValue = "10", required = false) int time) {
        String result = stockService.echoProvider(time);
        LogManager.put(UUID.randomUUID().toString(), result);
        return new HttpResponse(result);
    }

    @GetMapping("/echo/advisor/{key}/{value}")
    @ResponseBody
    public HttpResponse echoAdvisorWithHeader(@RequestParam(name = "time", defaultValue = "1", required = false) int time, @PathVariable(name = "key") String key, @PathVariable(name = "value") String value) {
        Map<String, String> headers = new HashMap<>();
        headers.put(key, value);
        String result = stockService.echoAdvisorWithHeader(time, key, value);
        LogManager.put(UUID.randomUUID().toString(), result);
        return new HttpResponse(result);
    }

    @GetMapping("/echo/provider/{key}/{value}")
    @ResponseBody
    public HttpResponse echoProviderWithHeader(@RequestParam(name = "time", defaultValue = "1", required = false) int time, @PathVariable(name = "key") String key, @PathVariable(name = "value") String value) {
        Map<String, String> headers = new HashMap<>();
        headers.put(key, value);
        String result = stockService.echoProviderWithHeader(time, key, value);
        LogManager.put(UUID.randomUUID().toString(), result);
        return new HttpResponse(result);
    }

    @GetMapping("/health")
    @ResponseBody
    public String health() {
        return "I am good!";
    }

    @RequestMapping("/deepInvoke")
    @ResponseBody
    public String deepInvoke(@RequestParam int times) {
        return stockService.deepInvoke(times);
    }

    @GetMapping("/stress")
    @ResponseBody
    public HttpResponse stress() {

        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("stress --vm 2 --vm-bytes 256M --timeout 30s");
        } catch (IOException e) {
            log.warn("exec stress command failed", e);
        }
        String result = "malloc 256M for 30 seconds";
        LogManager.put(UUID.randomUUID().toString(), result);
        return new HttpResponse(result);
    }


    private HttpResponse handleExceptionResponse(Exception e) {
        NsfExceptionUtil.NsfExceptionWrapper nsfException = NsfExceptionUtil.parseException(e);
        log.error(nsfException.getThrowable().getMessage());
        if (nsfException.getType() == NsfExceptionUtil.NsfExceptionType.NORMAL_EXCEPTION) {
            return new HttpResponse(nsfException.getThrowable().getMessage());
        }
        return new HttpResponse(nsfException.getType().getDesc());
    }

}