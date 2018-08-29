package com.ckj.projects;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

/**
 * created by ChenKaiJu on 2018/8/29  11:57
 */
public class ExcelUtils {

    public static JSONObject objToSingleJsonObj(Object obj){
        JSONObject object=new JSONObject();
        object=objToSingleJsonObj("",obj,object);
        return object;
    }

    public static List<JSONObject> arrayToSingleJsonArray(List list){
        List<JSONObject> array=new LinkedList<>();
        int len=list.size();
        for(int i=0;i<len;i++){
            JSONObject jsonObject=objToSingleJsonObj(list.get(i));
            array.add(jsonObject);
        }
        return array;
    }

    private static <T>JSONObject objToSingleJsonObj(String keyname,Object obj,JSONObject objMap){
        Class<T> tClass= (Class<T>) obj.getClass();
        T jsonObject1=JSONObject.parseObject(JSON.toJSONString(obj),tClass,Feature.InitStringFieldAsEmpty);
        JSONObject jsonObject=JSON.parseObject(JSON.toJSONString(jsonObject1));
        Set<String> keyset=jsonObject.keySet();
        Iterator<String> keys=keyset.iterator();
        while(keys.hasNext()){
            String key=keys.next();
            try{
                JSONObject o=jsonObject.getJSONObject(key);
                objToSingleJsonObj(keyname+"&"+key,o,objMap);
            }catch (Exception e){
                String titleName=keyname+"&"+key;
                objMap.put(titleName,jsonObject.get(key));
            }
        }
        return objMap;
    }

    public static List<String> getAllTitle(List<JSONObject> objs){
        List<String> titles=new LinkedList<>();
        int len=objs.size();
        Set<String> titleSet=new LinkedHashSet<>();
        for(int i=0;i<len;i++){
            titleSet.addAll(objs.get(i).keySet());
        }
        titles.addAll(titleSet);
        return titles;
    }

    public static int containsCell(Row row,String content){
        int count=row.getLastCellNum();
        for(int i=0;i<count;i++){
            Cell cell=row.getCell(i);
            if(cell!=null&&content.equals(cell.getStringCellValue())){
                return i;
            }
        }
        return -1;
    }

    private static int appendCell(Row row,String content){
        int count=row.getLastCellNum();
        if(count<0){
            count=0;
        }
        row.createCell(count).setCellValue(content);
        return count;
    }

    /**
     * 只支持xls格式的文件，xlsx格式的不支持，请勿调用
     * @param list
     * @param fileNamePath
     * @param sheetName
     * @return
     */
    public static boolean writeArrayToNewExcel(List list,String fileNamePath,String sheetName){
        List<JSONObject> array=arrayToSingleJsonArray(list);
        List<String> titils=getAllTitle(array);
        Workbook wb = null;
        try {
            File file = new File(fileNamePath);
            String name = file.getName();
            if (name.endsWith(".xls")) {
                wb = new HSSFWorkbook();
            } else if (name.endsWith(".xlsx")) {
                wb = new XSSFWorkbook();
            } else {
                throw new RuntimeException("the file's type must .xls or .xlsx");
            }
            Sheet sheet = wb.createSheet(sheetName);
            Row row = sheet.createRow(0);
            for (int i = 0; i < titils.size(); i++) {
                String title = titils.get(i);
                row.createCell(i).setCellValue(title);
            }
            for(int i=0;i<array.size();i++){
                try{
                    JSONObject obj=array.get(i);
                    Row row1=sheet.createRow(i+1);
                    for (int j = 0; j < titils.size(); j++) {
                        try{
                            String title = titils.get(j);
                            Object value=obj.get(title);
                            if(!value.getClass().getName().equals("java.lang.String")){
                                row1.createCell(j).setCellValue(JSON.toJSONString(value));
                            }else {
                                row1.createCell(j).setCellValue(value.toString());
                            }
                        }catch (Exception e){
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
