package com.rescue.team.bean.picture;

public enum PictureType {
    JPG("jpg"),
    PNG("png"),
    BMP("bmp"),
    SVG("svg"),
    PSD("psd"),
    WEBP("webp");

    private String type;

    PictureType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
