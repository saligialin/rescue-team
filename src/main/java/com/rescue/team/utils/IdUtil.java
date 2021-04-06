package com.rescue.team.utils;

import java.util.Calendar;

public class IdUtil {

    public static String generateId() {
        Calendar now = Calendar.getInstance();
        String id= ""+now.get(Calendar.YEAR);       //年
        //月
        if((now.get(Calendar.MONTH) + 1)<10) {
            id += "0"+(now.get(Calendar.MONTH) + 1);
        } else {
            id += (now.get(Calendar.MONTH) + 1);
        }
        //日
        if(now.get(Calendar.DAY_OF_MONTH)<10) {
            id += "0"+now.get(Calendar.DAY_OF_MONTH);
        } else {
            id += now.get(Calendar.DAY_OF_MONTH);
        }
        //时
        if(now.get(Calendar.HOUR_OF_DAY)<10) {
            id += "0"+now.get(Calendar.HOUR_OF_DAY);
        } else {
            id += now.get(Calendar.HOUR_OF_DAY);
        }
        //分
        if(now.get(Calendar.MINUTE)<10) {
            id += "0"+now.get(Calendar.MINUTE);
        } else {
            id += now.get(Calendar.MINUTE);
        }
        //秒
        if(now.get(Calendar.MINUTE)<10) {
            id += "0"+now.get(Calendar.MINUTE);
        } else {
            id += now.get(Calendar.MINUTE);
        }
        //随机四位
        for (int i=0; i<4; i++) {
            id += (int)(Math.random()*10)+"";
        }

        return id;
    }
}
