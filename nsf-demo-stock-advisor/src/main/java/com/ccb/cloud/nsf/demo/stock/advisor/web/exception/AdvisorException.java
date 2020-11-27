package com.ccb.cloud.nsf.demo.stock.advisor.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author chenjiahan | chenjiahan@corp.netease.com | 2019/3/13
 **/
@ResponseStatus(code=HttpStatus.BAD_GATEWAY, reason ="bad gateway")
public class AdvisorException extends Exception{

}
