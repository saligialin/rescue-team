package com.rescue.team.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户类")
public class User {

    @ApiModelProperty(name = "uid", value = "用户ID")
    private String uid;

    @ApiModelProperty(name = "tel", value = "用户手机号码")
    private String tel;

    @JsonBackReference("密码")
    @ApiModelProperty(name = "password", value = "用户密码")
    private String password;

    @ApiModelProperty(name = "role", value = "用户角色")
    private Integer role;

    @ApiModelProperty(name = "picture", value = "用户头像存储地址对应的img标签")
    private String picture;

    @ApiModelProperty(name = "name", value = "用户姓名")
    private String name;

    @ApiModelProperty(name = "gender", value = "用户性别")
    private Integer gender;

    @ApiModelProperty(name = "birthday", value = "用户生日")
    private Date birthday;

}
