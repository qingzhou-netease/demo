package com.demo.stock.advisor.web.controller;


import com.demo.stock.advisor.web.entity.Stock;
import com.demo.stock.advisor.web.service.IAdvisorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

@RestController
public class AdvisorController {

    private static Logger log = LoggerFactory.getLogger(AdvisorController.class);

    @Autowired
    IAdvisorService advisorService;

    @Value("${instance.version}")
    private String version;

    private int count = 0;
	/*@Autowired
	TestJavaConfigBean testJavaConfigBean;*/

    @GetMapping(value = "/advices/hot", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Stock> getHotAdvice() throws Exception {
        return advisorService.getHotStocks();
    }

    @GetMapping(value = "/hi", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<String> greeting() {
        return advisorService.batchHi();
    }

    @Value("${spring.application.name}")
    String name;

    @GetMapping(value = "/echo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String echo(HttpServletRequest request) {
        log.info("echo advisor invoked");
        String host = request.getServerName();
        int port = request.getServerPort();

        return "echo from " + name + "[" + host + ":" + port + "]" + "---" + version + "---count:" + ++count + System.lineSeparator();
    }

    @GetMapping(value = "/echoWithHeader/{key}/{value}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String echoWithHeader(HttpServletRequest request, @PathVariable(name = "key") String key, @PathVariable(name = "value") String value) {
        log.info(" echo advisor with header invoked");
        return advisorService.echoProviderWithHeaders(request, key, value);
    }

    @GetMapping(value = "/health", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String health() {
        log.info("I am good - from advisor");
        return "I am good!";
    }

    @RequestMapping(value = "/deepInvoke", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String deepInvoke(@RequestParam int times) {
        return advisorService.deepInvoke(times);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String register(@RequestBody String jsonString) {
        return "register json :\r\n" + jsonString;
    }

    @Value("${test:hi}")
    String test;

    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String TestApollo() {
        return test;
    }

    @Value("${test2}")
    String test2;

    @GetMapping(value = "/test2", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String TestApollo2() {
        return test2;
    }

    @GetMapping(value = "/divide", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String divide(HttpServletRequest request) {

        return advisorService.divide(request);


    }
}
