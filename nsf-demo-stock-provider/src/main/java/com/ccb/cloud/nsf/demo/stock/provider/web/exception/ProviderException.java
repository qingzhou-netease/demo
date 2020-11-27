package com.ccb.cloud.nsf.demo.stock.provider.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author chenjiahan | chenjiahan@corp.netease.com | 2020/7/15
 **/
@ResponseStatus(code= HttpStatus.INTERNAL_SERVER_ERROR, reason ="internal server error")
public class ProviderException extends RuntimeException {
}
