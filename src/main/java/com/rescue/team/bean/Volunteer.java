package com.rescue.team.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("志愿者类")
public class Volunteer {

    @ApiModelProperty(name = "vid", value = "志愿者ID")
    private String vid;

    @ApiModelProperty(name = "tel", value = "志愿者手机号")
    private String tel;

    @ApiModelProperty(name = "name", value = "志愿者姓名")
    private String name;

    @ApiModelProperty(name = "status", value = "志愿者状态")
    private Integer status;

    @ApiModelProperty(name = "gender", value = "志愿者性别")
    private Integer gender;

    @ApiModelProperty(name = "profession", value = "志愿者职业")
    private String profession;

    @ApiModelProperty(name = "transportation", value = "志愿者进行搜救时的交通工具")
    private String transportation;

    @ApiModelProperty(name = "front_card", value = "志愿者身份证正面照对应的img标签")
    private String front_card;

    @ApiModelProperty(name = "back_card", value = "志愿者身份证反面照对应的img标签")
    private String back_card;

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
