package com.rescue.team.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("管理员类")
public class Admin {

    @ApiModelProperty(name = "aid",value = "管理员ID")
    private String aid;

    @ApiModelProperty(name = "username",value = "管理员用户名")
    private String username;

    @JsonBackReference("管理员密码")
    @ApiModelProperty(name = "password",value = "管理员密码")
    private String password;

    @ApiModelProperty(name = "level",value = "管理员等级")
    private Integer level;

    @ApiModelProperty(name = "status",value = "管理员状态")
    private Integer status;

    @ApiModelProperty(name = "secondary_password",value = "管理员二级密码")
    private String secondary_password;

}
