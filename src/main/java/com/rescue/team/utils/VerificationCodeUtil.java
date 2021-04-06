package com.rescue.team.utils;

public class VerificationCodeUtil {

    public static String getCode() {
        String code = "";
        for (int i=0; i<6; i++) {
            code += (int)(Math.random()*10)+"";
        }
        return code;
    }
}
