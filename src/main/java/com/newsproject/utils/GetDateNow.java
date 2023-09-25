package com.newsproject.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetDateNow {
    public static Date getDateNow(){
        Date res = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date now = new Date();
        String dateNow = sdf.format(now);
        try{
            res = sdf.parse(dateNow);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }
}
