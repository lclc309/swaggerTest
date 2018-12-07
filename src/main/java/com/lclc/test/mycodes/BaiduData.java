package com.lclc.test.mycodes;

import com.google.common.collect.Lists;
import com.lclc.test.model.BaiduExcelModel2;
import com.lclc.test.util.excel.ExcelReader;
import com.lclc.test.util.excel.ExcelResult;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author lichao 2018/5/14
 */
public class BaiduData {

    public static void main(String[] args) throws IOException {

        ExcelReader<BaiduExcelModel2> reader = new ExcelReader<>(BaiduExcelModel2.class);
        ExcelResult<BaiduExcelModel2> baiduExcelModel2ExcelResult = reader.readFromExcel(new File("/Users/lichao/Desktop/baidu.xlsx"));
        List<BaiduExcelModel2> values = baiduExcelModel2ExcelResult.getValues();
        List<BaiduExcelModel2> list = Lists.newArrayList();
        for (BaiduExcelModel2 model : values){
            if(isContains(model.getaTuiaId())){
                list.add(model);
            }
        }

        System.out.println(list.size());

    }

    public static boolean isContains(String tuiaId){
        if(StringUtils.isBlank(tuiaId)){
            return false;
        }

        return tuiaId.startsWith("taw-")||tuiaId.startsWith("hdtool-")||tuiaId.startsWith("plugin_");

    }
}
