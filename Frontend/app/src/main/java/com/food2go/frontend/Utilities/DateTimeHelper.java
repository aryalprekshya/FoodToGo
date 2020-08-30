package com.food2go.frontend.Utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHelper {
    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm");

    public static String getTime(Date date){
        if(date == null)
            return "";
        return format.format(date);
    }
}
