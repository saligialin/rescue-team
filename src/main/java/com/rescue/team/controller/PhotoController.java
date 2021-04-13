package com.rescue.team.controller;

import com.rescue.team.annotation.ApiJsonObject;
import com.rescue.team.annotation.ApiJsonProperty;
import com.rescue.team.bean.Photo;
import com.rescue.team.bean.ResponseData;
import com.rescue.team.bean.state.ResponseState;
import com.rescue.team.service.FaceService;
import com.rescue.team.service.PhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/photo")
@Api(tags = "老人照片组相关操作控制器")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private FaceService faceService;

    @ApiOperation("增加照片组（应该用不到，用addPhoto请求逐张增加图片）|传参修改的，必传id")
    @PostMapping("/add")
    public ResponseData addPhotos(@RequestBody Photo photo) {
        boolean b = photoService.insertPhoto(photo);
        if(b) {
            Map<String, Object> data = new HashMap<>();
            data.put("photo",photo);
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(), data);
        } else {
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }
    }

    @ApiOperation("为老人增加照片|which说明：第2张照片参数值为photo1',第2张照片参数值为photo2，同理类推...")
    @PostMapping("/addPhoto")
    public ResponseData addPhoto(@ApiJsonObject(name = "addPhoto",value = {
            @ApiJsonProperty( key = "eid", example = "老人id"),
            @ApiJsonProperty( key = "photo", example = "照片的url地址"),
            @ApiJsonProperty( key = "which", example = "第几张照片")
    }) @RequestBody Map<String,String> parameter) {
        String eid = parameter.get("eid");
        String photo = parameter.get("photo");
        String which = parameter.get("which");
        boolean b = photoService.insertOnePhoto(eid, photo, which);
        if(b) {
            boolean addFace = faceService.addFace(eid, photo);
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage());
        } else {
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }
    }

    @ApiOperation("通过老人ID获得照片组|传参老人的eid")
    @PostMapping("/get")
    public ResponseData getPhoto(@ApiJsonObject(name = "getPhoto",value = @ApiJsonProperty(key = "eid",example = "老人ID")) @RequestBody Map<String,String> parameter) {
        String eid = parameter.get("eid");
        Photo photo = photoService.getPhotoByEid(eid);
        if(photo!=null) {
            Map<String, Object> data = new HashMap<>();
            data.put("photo",photo);
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(), data);
        } else {
            return new ResponseData(ResponseState.RESULT_IS_NULL.getValue(), ResponseState.RESULT_IS_NULL.getMessage());
        }
    }

    @ApiOperation("更改老人照片组信息（摆设）")
    @PostMapping("/change")
    public ResponseData changePhoto(@RequestBody Photo photo) {
        boolean b = photoService.changePhoto(photo);
        if(b) {
            Map<String, Object> data = new HashMap<>();
            data.put("photo",photo);
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(), data);
        } else {
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }
    }

    @ApiOperation("删除老人照片组信息（摆设）")
    @PostMapping("/delete")
    public ResponseData deletePhoto(@ApiJsonObject(name = "deletePhoto",value = @ApiJsonProperty(key = "pid",example = "照片组ID")) @RequestBody Map<String,String> parameter) {
        String pid = parameter.get("pid");
        boolean b = photoService.deletePhoto(pid);
        if(b) {
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage());
        } else {
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }
    }

}
