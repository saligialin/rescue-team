package com.rescue.team.controller;

import com.rescue.team.bean.Photo;
import com.rescue.team.bean.ResponseData;
import com.rescue.team.bean.state.ResponseState;
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

    @ApiOperation("增加照片组")
    @PostMapping("/add")
    public ResponseData addPhoto(@RequestBody Photo photo) {
        boolean b = photoService.insertPhoto(photo);
        if(b) {
            Map<String, Object> data = new HashMap<>();
            data.put("photo",photo);
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(), data);
        } else {
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }
    }

    @ApiOperation("通过老人ID获得照片组")
    @PostMapping("/get")
    public ResponseData getPhoto(@RequestBody String eid) {
        Photo photo = photoService.getPhotoByEid(eid);
        if(photo!=null) {
            Map<String, Object> data = new HashMap<>();
            data.put("photo",photo);
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(), data);
        } else {
            return new ResponseData(ResponseState.RESULT_IS_NULL.getValue(), ResponseState.RESULT_IS_NULL.getMessage());
        }
    }

    @ApiOperation("更改老人照片组信息")
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

    public ResponseData deletePhoto(@RequestBody String pid) {
        boolean b = photoService.deletePhoto(pid);
        if(b) {
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage());
        } else {
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }
    }

}
