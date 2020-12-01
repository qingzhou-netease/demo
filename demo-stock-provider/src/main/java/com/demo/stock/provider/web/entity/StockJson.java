package com.demo.stock.provider.web.entity;


/**
 * @Author chenjiahan | chenjiahan@corp.netease.com | 2019/3/25
 **/
public class StockJson {

    private Long id;

    private String value;

    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
