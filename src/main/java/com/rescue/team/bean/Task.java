package com.rescue.team.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("任务类")
public class Task {

    @ApiModelProperty(name = "tid", value = "任务ID")
    private String tid;

    @ApiModelProperty(name = "code", value = "任务编号")
    private String code;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(name = "start", value = "任务开始时间")
    private Date start;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(name = "end", value = "任务结束时间")
    private Date end;

    @ApiModelProperty(name = "eid", value = "任务所救援老人")
    private String eid;

    @ApiModelProperty(name = "description", value = "家属备注老人描述")
    private String description;

    @ApiModelProperty(name = "longitude", value = "经度")
    private String longitude;

    @ApiModelProperty(name = "latitude", value = "纬度")
    private String latitude;

    @ApiModelProperty(name = "province", value = "省份")
    private String province;

    @ApiModelProperty(name = "city", value = "城市")
    private String city;

    @ApiModelProperty(name = "district", value = "县/区")
    private String district;

    @ApiModelProperty(name = "address", value = "地址")
    private String address;

    @ApiModelProperty(name = "place", value = "位置名")
    private String place;

}
