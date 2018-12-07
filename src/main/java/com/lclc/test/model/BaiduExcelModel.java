package com.lclc.test.model;

import com.lclc.test.util.excel.ExcelPattern;

/**
 * @author lichao 2018/5/7
 */
public class BaiduExcelModel {

    @ExcelPattern(header="d.consumer_id")
    private String d_consumer_id;

    @ExcelPattern(header="d.c")
    private String d_c;

    @ExcelPattern(header="t.consumer_id")
    private String t_consumer_id;

    @ExcelPattern(header = "t.user_agent")
    private String t_user_agent;

    public String getD_consumer_id() {
        return d_consumer_id;
    }

    public void setD_consumer_id(String d_consumer_id) {
        this.d_consumer_id = d_consumer_id;
    }

    public String getD_c() {
        return d_c;
    }

    public void setD_c(String d_c) {
        this.d_c = d_c;
    }

    public String getT_consumer_id() {
        return t_consumer_id;
    }

    public void setT_consumer_id(String t_consumer_id) {
        this.t_consumer_id = t_consumer_id;
    }

    public String getT_user_agent() {
        return t_user_agent;
    }

    public void setT_user_agent(String t_user_agent) {
        this.t_user_agent = t_user_agent;
    }
}
