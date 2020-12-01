package com.demo.stock.provider.web.controller;

import com.demo.stock.provider.web.entity.Stock;
import com.demo.stock.provider.web.exception.ProviderException;
import com.demo.stock.provider.web.service.IProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Chen Jiahan | chenjiahan@corp.netease.com
 */
@RestController
public class StockController {

    private static Logger log = LoggerFactory.getLogger(StockController.class);

    @Autowired
    IProviderService stockService;

    @Value("${instance.version}")
    private String version;

    private int _count = 0;

    @Value("${error:false}")
    boolean errorSwitch;

    @Value("${spring.application.name}")
    String app;
    /**
     * @param stockIds 以","分隔 , 单个id也可查询
     * @return
     * @throws InterruptedException
     */
    @GetMapping(value = "/stocks/{stockIds}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Stock> getStocksByIds(@PathVariable String stockIds,
                                      @RequestParam(name = "delay", required = false, defaultValue = "0") int delay) throws InterruptedException {
        Thread.sleep(delay * 1000);
        log.info("get /stocks/{} success", stockIds);
        return stockService.getStocksByIds(stockIds);
    }

    @GetMapping(value = "/stocks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Stock> getAllStocks(@RequestParam(name = "delay", required = false, defaultValue = "0") int delay)
            throws InterruptedException {
        Thread.sleep(delay * 1000);
        return stockService.getAllStocks();
    }

    @Value("${spring.application.name}")
    String name;

    @GetMapping(value = "/hi", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String greeting(HttpServletRequest request) {

        String host = request.getServerName();
        int port = request.getServerPort();

        return "greeting from " + name + "[" + host + ":" + port + "]" + System.lineSeparator();
    }

    @GetMapping(value = "/echo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String echo(HttpServletRequest request) {
        log.info(" echo provider invoked");
        checkIfThrowException();
        String host = request.getServerName();
        int port = request.getServerPort();

        return "echo from " + name + "[" + host + ":" + port + "]" + "---" + version + "---count:" + ++_count + System.lineSeparator();
    }

    @GetMapping(value = "/echoWithHeader/{key}/{value}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String echoWithHeader(HttpServletRequest request, @PathVariable(name = "key") String key, @PathVariable(name = "value") String value) {
        log.info(" echo provider with header invoked");
        String host = request.getServerName();
        int port = request.getServerPort();
        StringBuilder log = new StringBuilder();
        log.append("REQUEST HEADER:[").append(System.lineSeparator());
        Enumeration<String> header = request.getHeaderNames();
        while (header.hasMoreElements()) {
            String headerName = header.nextElement();
            String headerVal = request.getHeader(headerName);
            if (headerName.equals("color-chain")) {
                headerVal += String.format("-> %s:%s", app, version);
                log.append(headerName).append(":").append(headerVal).append(System.lineSeparator());
            }
        }
        log.append("]").append(System.lineSeparator());
        log.append("SERVICE:[").append(name).append("]").append(System.lineSeparator());
        log.append("SERVICE-VERSION:[").append(version).append("]").append(System.lineSeparator());
        log.append("---------------------------------------------------------").append(System.lineSeparator());
        return log.toString();
    }

    @PostMapping(value = "echoPost", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object echoPost(@RequestBody Object obj) {
        return obj;
    }

    @GetMapping(value = "/health", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String health() {
        log.info("I am good - from provider");
        return "I am good!";
    }

    @GetMapping("/stress")
    @ResponseBody
    public String stress() {

        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("stress --vm 2 --vm-bytes 128M --timeout 30s");
        } catch (IOException e) {
            log.warn("exec stress command failed", e);
        }
        String result = "malloc 128M for 30 seconds for provider";
        return result;
    }

    /**
     * 熔断测试
     */
    int count = 0;

    @GetMapping(value = "/sleepgw", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String sleepgw(HttpServletRequest request, String msg) throws InterruptedException {
        if (count++ % 5 < 3) {
            TimeUnit.SECONDS.sleep(10);
        }
        return "第" + count + "次sleepgw,参数:" + msg + ",响应服务地址:" + request.getServerName() + ":" + request.getServerPort();
    }

    /**
     * 测试网关自定义插件
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/getQueryString", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getQueryString(HttpServletRequest request) {
        String result = "No Query String";

        Map<String, String[]> names = request.getParameterMap();
        if (name != null && names.size() > 0) {
            result = "";
            for (String key : names.keySet()) {
                result = result + key + " : " + names.get(key)[0] + "<br/>";
            }
        }

        return result;
    }

    private void checkIfThrowException() {
        if (errorSwitch) {
            throw new ProviderException();
        }
    }
}
