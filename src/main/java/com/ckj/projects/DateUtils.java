package com.ckj.projects;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DateUtils {

    /**
     * 获取从start到end之间的所日期
     * @param start eg:19951214
     * @param end eg:20121023
     * @return
     */
    public static List<String> getAllDate(String start, String end){
        List<String> datelist=new ArrayList<String>();
        String st=start;
        String day=st.substring(st.length()-2);
        String month=st.substring(st.length()-4,st.length()-2);
        String year=st.substring(0,st.length()-4);
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR,Integer.valueOf(year));
        date.set(Calendar.MONTH,Integer.valueOf(month)-1);
        date.set(Calendar.DAY_OF_MONTH,Integer.valueOf(day));
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        int i=0;
        int enddate=Integer.valueOf(end);
        while (i<enddate){
            String str=sdf.format(date.getTime());
            i=Integer.valueOf(str);
            datelist.add(str);
            date.add(Calendar.DAY_OF_MONTH,1);
        }
        return datelist;
    }
}
