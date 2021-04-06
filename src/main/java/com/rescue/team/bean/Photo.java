package com.rescue.team.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("老人照片组类")
public class Photo {

    @ApiModelProperty(name = "pid", value = "老年照片组ID")
    private String pid;

    @ApiModelProperty(name = "eid", value = "老年人ID")
    private String eid;

    @ApiModelProperty(name = "photo1", value = "老人照片1对应的img标签")
    private String photo1;

    @ApiModelProperty(name = "photo2", value = "老人照片2对应的img标签")
    private String photo2;

    @ApiModelProperty(name = "photo3", value = "老人照片3对应的img标签")
    private String photo3;

    @ApiModelProperty(name = "photo4", value = "老人照片4对应的img标签")
    private String photo4;

    @ApiModelProperty(name = "photo5", value = "老人照片5对应的img标签")
    private String photo5;

    @ApiModelProperty(name = "photo6", value = "老人照片6对应的img标签")
    private String photo6;

    @ApiModelProperty(name = "photo7", value = "老人照片7对应的img标签")
    private String photo7;

    @ApiModelProperty(name = "photo8", value = "老人照片8对应的img标签")
    private String photo8;

    @ApiModelProperty(name = "photo9", value = "老人照片9对应的img标签")
    private String photo9;

}
