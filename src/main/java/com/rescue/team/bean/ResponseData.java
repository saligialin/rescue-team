package com.rescue.team.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("返回数据类")
public class ResponseData {

    @ApiModelProperty(name = "status", value = "返回状态码")
    private Integer status;

    @ApiModelProperty(name = "msg", value = "返回信息")
    private String msg;

    @ApiModelProperty(name = "data", value = "返回数据组")
    private Map<String,Object> data;

    public ResponseData(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
