package com.lclc.test.mycodes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author lichao 2018/5/11
 */
public class BaichuanData {

    public static void main(String[] args) throws IOException {
        //1.读取json文件
        File file  = new File("/Users/lichao/Desktop/baichuan");
        List lines = FileUtils.readLines(file);
        String line  = lines.get(0).toString();

        JSONObject jsonObject = JSONObject.parseObject(line);
        JSONArray datas = jsonObject.getJSONArray("datas");
        JSONObject obj = datas.getJSONObject(0);
        double total = 0;
        for(String key :obj.keySet()){
            total+=obj.getDouble(key)*10;
        }
        System.out.println(total+"============");


    }
}
