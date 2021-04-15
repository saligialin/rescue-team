package com.rescue.team.bean.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @ApiModelProperty(name = "vid", value = "志愿者ID")
    private String vid;

    @ApiModelProperty(name = "tid", value = "任务ID")
    private String tid;

    @ApiModelProperty(name = "picture", value = "头像")
    private String picture;

    @ApiModelProperty(name = "name", value = "姓名")
    private String name;

    @ApiModelProperty(name = "longitude", value = "经度")
    private Double longitude;

    @ApiModelProperty(name = "latitude", value = "纬度")
    private Double latitude;

}
