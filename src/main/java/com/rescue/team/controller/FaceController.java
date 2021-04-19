package com.rescue.team.controller;

import com.rescue.team.annotation.ApiJsonObject;
import com.rescue.team.annotation.ApiJsonProperty;
import com.rescue.team.bean.ResponseData;
import com.rescue.team.bean.state.ResponseState;
import com.rescue.team.service.FaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(tags = "人脸识别接口")
@RestController
@RequestMapping("/face")
public class FaceController {

    @Autowired
    private FaceService faceService;

    @ApiOperation("人脸对比接口|传参待核实的老人的图片url")
    @PostMapping("/compared")
    public ResponseData faceCompared(@ApiJsonObject(name = "faceCompared",value = {
            @ApiJsonProperty( key = "photo", example = "待识别照片"),
            @ApiJsonProperty( key = "eid", example = "老人ID")
    }) @RequestBody Map<String, String> parameter) {
        String photo = parameter.get("photo");
        String eid = parameter.get("eid");
        boolean b = faceService.searchFace(photo,eid);
        if(b) return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage());
        else return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
    }
}
