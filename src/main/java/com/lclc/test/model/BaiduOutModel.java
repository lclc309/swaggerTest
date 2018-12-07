package com.lclc.test.model;

import com.lclc.test.util.excel.ExcelPattern;

/**
 * @author lichao 2018/5/7
 */
public class BaiduOutModel {

    @ExcelPattern(header = "ua",field = "ua")
    private String ua;

    @ExcelPattern(header = "数量",field = "num")
    private String num;

    @ExcelPattern(header = "占比",field = "part")
    private String part;

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }
}
