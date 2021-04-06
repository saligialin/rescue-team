package com.rescue.team.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("成员绑定类：任务与志愿者")
public class Member {

    @ApiModelProperty(name = "mid", value = "成员绑定类ID")
    private String mid;

    @ApiModelProperty(name = "tid", value = "任务ID")
    private String tid;

    @ApiModelProperty(name = "vid", value = "志愿者ID")
    private String vid;

}
