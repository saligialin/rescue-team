package com.rescue.team.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("老年人类")
public class Elder {

    @ApiModelProperty(name = "eid", value = "老年人ID")
    private String eid;

    @ApiModelProperty(name = "name", value = "老年人名字")
    private String name;

    @ApiModelProperty(name = "gender", value = "老年人性别")
    private Integer gender;

    @ApiModelProperty(name = "birthday", value = "老年人生日")
    private Date birthday;

    @ApiModelProperty(name = "height", value = "老年人身高")
    private Double height;

    @ApiModelProperty(name = "level", value = "老年人病情等级")
    private Integer level;

    @ApiModelProperty(name = "front_card", value = "老年人身份证正面照对应的img标签")
    private String front_card;

    @ApiModelProperty(name = "back_card", value = "老年人身份证反面照对应的img标签")
    private String back_card;

    @ApiModelProperty(name = "bind_tel", value = "绑定老年人的用户ID")
    private String uid;

    @ApiModelProperty(name = "status", value = "老年人的审核状态")
    private Integer status;

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
