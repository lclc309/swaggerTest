package com.lclc.test.guanxiexcel;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lichao 2018/12/3
 */
public class Main {



    public static void main(String[] args) throws Exception {

        Main main = new Main();
        main.readExcels("//Users/lichao/Desktop/excelFiles","//Users/lichao/Desktop/out1.xls");

    }

    private static  Map<Integer,Workbook> map = null;

    /**
     * 读取 excel 进行操作
     * @param dictory 源文件所在的文件夹
     * @param ourFile 输出的合并的文件
     * @throws Exception
     */
    private void readExcels(String dictory,String ourFile) throws Exception {
        System.out.println("处理开始");
        File file = new File(dictory);
        File[] files = file.listFiles((item)->{
            return  item.getName().endsWith(".xls");
        });
        //排序
        Map<Integer,Workbook> _map = Maps.newLinkedHashMap();
        final List<Integer> list1 = Lists.newArrayListWithCapacity(files.length);
        Stream.of(files).forEach((item1)->{
            String name = item1.getName();
            int i = Integer.parseInt(name.substring(0, name.indexOf("--")).replaceAll("-","").replaceAll("\\.", ""));
            try {
                _map.put(i, new HSSFWorkbook(new FileInputStream(item1)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            list1.add(i);
        });
        map = _map;

        List<Integer> list = list1.stream().sorted().collect(Collectors.toList());
        System.out.println(list.size());
        list.stream().forEach(a-> System.out.println(a));

        //1.第一个文件的第一个sheet的头信息
        //将文件转为workbook

        Workbook newWb = new HSSFWorkbook();//写出的workbook
        //创建4个sheet

        createSheet(newWb, list, "交易审单入账明细",1);
        Thread.sleep(1000);
        createSheet(newWb, list, "异常订单入账明细",2);
        Thread.sleep(1000);
        createSheet(newWb, list, "其他扣返款明细",3);
        Thread.sleep(1000);
        createSheet(newWb, list, "保证金返还明细",4);
        Thread.sleep(1000);

        //写出到另一个文件
        ((HSSFWorkbook) newWb).write(new File(ourFile));
        System.out.println("处理完成");

    }

    /**
     * 创建每个sheet
     */
    private void createSheet(Workbook newWb, List<Integer> list, String sheetTile, int sheetIndex) {
        Map<Integer,CellStyle> cache = Maps.newHashMap();
        //处理第一行
        Workbook workbook =map.get(list.get(0));
        Sheet sheet1 = workbook.getSheetAt(sheetIndex);
        System.out.println("正在copy:"+sheet1.getSheetName());

        Sheet sheet = newWb.createSheet(sheetTile);
        Row oldRow = sheet1.getRow(sheet1.getFirstRowNum());

        int start = oldRow.getFirstCellNum();
        int end = oldRow.getLastCellNum();

        Row row = sheet.createRow(0);
        if(oldRow.getRowStyle()!=null){
            row.setRowStyle(oldRow.getRowStyle());
        }

        for (int i = start; i < end; i++) {
            sheet.setColumnWidth(i,sheet1.getColumnWidth(i)); //调整第一列宽度
            Cell srcCell = oldRow.getCell(i);
            createCell(i, srcCell, row, newWb,workbook,cache);

        }

        //其他的行,从各个文件中copy出来
        int i = 1;
        //循环所有的文件
        for (int j = 0; j < list.size(); j++) {
            //获得本文件的当前sheet
            Workbook wb = map.get(list.get(j));
            Sheet oldSheet = wb.getSheetAt(sheetIndex);
            Iterator<Row> rowIterator = oldSheet.rowIterator();
            while (rowIterator.hasNext()){
                //首先在新文件sheet中创建一个行
                Row row1 = rowIterator.next();
                if(row1.getRowNum()==0){
                    continue;
                }
                Row distRow = sheet.createRow(i);


                if(row1.getRowStyle()!=null) {
                    distRow.setRowStyle(row1.getRowStyle());
                }
                //进行copyCell
                int startCell = row1.getFirstCellNum();
                int endCell = row1.getLastCellNum();
                for (int num = startCell; num < endCell; num++) {
                    Cell srcCell = row1.getCell(num);
                    createCell(num, srcCell, distRow, newWb,wb,cache);
                }
                i++;
            }

        }
    }

    //创建单元格
    private Cell createCell(int i, Cell srcCell, Row distRow, Workbook wb,Workbook oldWb,Map<Integer,CellStyle> cache) {
        Cell distCell = distRow.createCell(i);

        //评论
        if (srcCell.getCellComment() != null) {
            distCell.setCellComment(srcCell.getCellComment());
        }
        // 不同数据类型处理
        CellType srcCellType = srcCell.getCellType();
        distCell.setCellType(srcCellType);
        if (srcCellType == CellType.NUMERIC) {
//            if (DateUtil.isCellDateFormatted(srcCell)) {
//                distCell.setCellValue(srcCell.getDateCellValue());
//            } else {
                distCell.setCellValue(srcCell.getNumericCellValue());
//            }
        } else if (srcCellType == CellType.STRING) {
            distCell.setCellValue(srcCell.getRichStringCellValue());
        } else if (srcCellType == CellType.BLANK) {
        } else if (srcCellType == CellType.BOOLEAN) {
            distCell.setCellValue(srcCell.getBooleanCellValue());
        } else if (srcCellType == CellType.ERROR) {
            distCell.setCellErrorValue(srcCell.getErrorCellValue());
        } else if (srcCellType == CellType.FORMULA) {
            distCell.setCellFormula(srcCell.getCellFormula());
        } else {
        }

        CellStyle cellStyle = null;
        if(distRow.getRowNum()>0&&cache.containsKey(i)){

            cellStyle = cache.get(i);

        }else{
            CellStyle newStyle = wb.createCellStyle();
            CellStyle srcStyle = srcCell.getCellStyle();
            //copyCellStyle((HSSFCellStyle)srcStyle,(HSSFCellStyle)newStyle);
            newStyle.cloneStyleFrom(srcStyle);
            newStyle.setFont(oldWb.getFontAt(srcStyle.getFontIndexAsInt()));
            if(distRow.getRowNum()>0){
                cache.put(i,newStyle);
            }
            cellStyle = newStyle;
        }
        //样式
        distCell.setCellStyle(cellStyle);
        return distCell;

    }

}
