package com.rescue.team.bean.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @ApiModelProperty(name = "vid", value = "志愿者ID")
    private String vid;

    @ApiModelProperty(name = "tid", value = "任务ID")
    private String tid;

    @ApiModelProperty(name = "picture", value = "头像")
    private String picture;

    @ApiModelProperty(name = "name", value = "姓名")
    private String name;

    @ApiModelProperty(name = "time", value = "消息发送时间")
    private Date time;

    @ApiModelProperty(name = "message", value = "消息内容(仅支持文本)")
    private String message;

}
