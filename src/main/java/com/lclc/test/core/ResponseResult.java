package com.lclc.test.core;

import java.io.Serializable;

import com.lclc.test.util.ErrorCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @name ResponseResult
 * @discription 用于响应的json
 * @author lichao
 * @date 2015年9月6日
 */
@ApiModel
public class ResponseResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "是否成功", required = true)
    private boolean success;

    @ApiModelProperty(value = "代码", required = true)
    private int code;

    @ApiModelProperty(value = "提示消息")
    private String message = "";

    @ApiModelProperty(value = "数据")
    private Object data;

    public static ResponseResult success() {

        return new ResponseResult(true);
    }

    public static ResponseResult failure() {

        return new ResponseResult(false);
    }

    public static ResponseResult success(String message) {

        return new ResponseResult(true, message);
    }

    public static ResponseResult failure(String message) {

        return new ResponseResult(false, message);
    }

    public static ResponseResult failure(ErrorCode error) {

        return new ResponseResult(false, error.getCode(), error.getMessage(), null);
    }

    public ResponseResult() {
        super();
    }

    public ResponseResult(boolean success) {
        this(success, "");
    }

    public ResponseResult(boolean success, String message) {
        this(success, message, null);
    }

    public ResponseResult(boolean success, String message, Object data) {
        this(success, success ? 0 : 1, message, data);
    }

    public ResponseResult(boolean success, int code, String message, Object data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {

        return success;
    }

    public ResponseResult success(boolean success) {

        this.success = success;
        return this;
    }

    public int getCode() {

        return code;
    }

    public ResponseResult code(int code) {

        this.code = code;
        return this;
    }

    public String getMessage() {

        return message;
    }

    public ResponseResult message(String message) {

        this.message = message;
        return this;
    }

    public Object getData() {

        return data;
    }

    public ResponseResult data(Object data) {

        this.data = data;
        return this;
    }

    public static ResponseResult error403() {

        return ResponseResult.failure().code(403).message("Forbidden");
    }

    public static ResponseResult error404() {

        return ResponseResult.failure().code(404).message("NOT FOUND");
    }

    public static ResponseResult error500() {

        return ResponseResult.failure(ErrorCode.SYSERR);
    }
}
