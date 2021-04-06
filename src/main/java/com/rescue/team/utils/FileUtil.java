package com.rescue.team.utils;

import com.rescue.team.bean.picture.PictureType;

public class FileUtil {

    public static boolean isPicture(String type) {
        PictureType[] types = PictureType.values();
        for (PictureType pictureType : types) {
            if(type.equals(pictureType.getType())) return true;
        }
        return false;
    }
}
