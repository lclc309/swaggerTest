package com.lclc.test.model;

import com.lclc.test.util.excel.ExcelPattern;

/**
 * @author lichao 2018/5/14
 */
public class BaiduExcelModel2 {

    @ExcelPattern(header="a.tuia_id")
    private String aTuiaId;

    public String getaTuiaId() {
        return aTuiaId;
    }

    public void setaTuiaId(String aTuiaId) {
        this.aTuiaId = aTuiaId;
    }
}
