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
@ApiModel("文章类")
public class Article {

    @ApiModelProperty(name = "id", value = "文章ID")
    private String id;

    @ApiModelProperty(name = "cover", value = "文章封面")
    private String cover;

    @ApiModelProperty(name = "title", value = "文章标题")
    private String title;

    @ApiModelProperty(name = "username", value = "文章发布者")
    private String username;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(name = "time", value = "文章发布时间")
    private Date time;

    @ApiModelProperty(name = "context", value = "文章内容")
    private String context;
}
