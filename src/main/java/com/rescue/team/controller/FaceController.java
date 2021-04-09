package com.rescue.team.controller;

import com.rescue.team.bean.ResponseData;
import com.rescue.team.bean.state.ResponseState;
import com.rescue.team.service.FaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "人脸识别接口")
@RestController
@RequestMapping("/face")
public class FaceController {

    @Autowired
    private FaceService faceService;

    @ApiOperation("人脸对比接口|传参待核实的老人的图片url")
    @RequestMapping("/compared")
    public ResponseData faceCompared(@RequestBody String photo) {
        boolean b = faceService.searchFace(photo);
        if(b) return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage());
        else return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
    }
}
