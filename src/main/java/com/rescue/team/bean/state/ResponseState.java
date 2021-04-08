package com.rescue.team.bean.state;

public enum ResponseState {
    SUCCESS("操作成功",200),
    MESSAGE_SEND_SUCCESS("短信发送成功",201),
    FILE_UPLOAD_SUCCESS("文件上传成功",202),
    ERROR("操作失败",100),
    MESSAGE_SEND_ERROR("短信发送成功",101),
    USER_NOT_EXIST("用户不存在",102),
    PASSWORD_ERROR("密码错误",103),
    USER_IS_EXIST("用户已存在",104),
    VERIFIED_CODE_ERROR("验证码错误",105),
    FILE_UPLOAD_ERROR("文件上传失败",106),
    FILE_TYPE_ERROR("文件类型错误",107),
    File_IS_EMPTY("文件为空",108),
    RESULT_IS_NULL("返回结果为空",109),
    ALREADY_DID_IT("此行为已经操作过",110),
    TASK_NOT_EXIST("任务不存在",111),
    TOKEN_NOT_PROVIDE("未提供token",112),
    TOKEN_IS_ERROR("token错误",113),
    TOKEN_IS_EXPIRED("token过期",114),
    NO_VOLUNTEER_HERE("这个城市没有可服务的志愿者",115)
    ;

    private String message;
    private int value;


    ResponseState(String message, int value) {
        this.message = message;
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public int getValue() {
        return value;
    }
}
