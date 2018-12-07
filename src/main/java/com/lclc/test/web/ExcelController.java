package com.lclc.test.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lclc.test.model.BaiduExcelModel;
import com.lclc.test.model.BaiduOutModel;
import com.lclc.test.util.excel.ExcelReader;
import com.lclc.test.util.excel.ExcelResult;
import com.lclc.test.util.excel.ExcelWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author lichao 2018/5/7
 */
public class ExcelController {

    public static void main(String[] args) throws Exception {

        ExcelReader<BaiduExcelModel> reader = new ExcelReader<>(BaiduExcelModel.class);
        ExcelResult<BaiduExcelModel> result = reader.readFromExcel(new File("/Users/lichao/Desktop/baiduQuestion/4-17-9406-ua.xlsx"));
        List<BaiduExcelModel> values = result.getValues();
        if(values==null||values.isEmpty()){
            System.out.println("有问题");
        }

        Map<String, List<BaiduExcelModel>> collect = Maps.newHashMap();
        for(BaiduExcelModel model : values){
            if(model.getD_consumer_id()==null||Objects.equals(model.getD_consumer_id(),"NULL")){
                continue;
            }
            List<BaiduExcelModel> baiduExcelModels = collect.get(model.getT_user_agent());

            if(baiduExcelModels==null){
                baiduExcelModels = Lists.newArrayList();
                collect.put(model.getT_user_agent(),baiduExcelModels);
            }

            baiduExcelModels.add(model);
        }


        int total = collect.values().stream().mapToInt(List::size).reduce(0,(item1,item2)->item1+item2);
        System.out.println(total);
        List<BaiduOutModel> outModels = Lists.newArrayList();
        for(Map.Entry<String,List<BaiduExcelModel>> item:collect.entrySet()){
            int size = item.getValue().size();
            BaiduOutModel model = new BaiduOutModel();
            model.setUa(item.getKey());
            model.setNum(size+"");
            model.setPart(((size*10000)/total)+"%%");
            outModels.add(model);
        }
        ExcelWriter writer = new ExcelWriter(BaiduOutModel.class,outModels);
        writer.writeToExcel(new FileOutputStream(new File("/Users/lichao/Desktop/baiduQuestion/4-17-9406-ua-rs.xls")));

        System.out.println("done");


    }




}
