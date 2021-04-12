package com.rescue.team.controller;

import com.rescue.team.bean.ResponseData;
import com.rescue.team.bean.User;
import com.rescue.team.bean.state.ResponseState;
import com.rescue.team.service.OssService;
import com.rescue.team.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/file")
@Api(tags = "文件(照片)相关操作控制器")
public class FileController {

    @Autowired
    private OssService ossService;

    @ApiOperation("前台上传图片|传参图片")
    @PostMapping("/upload")
    public ResponseData upload(@RequestParam("photo") MultipartFile file, @ApiIgnore @ModelAttribute("user")User user) {
        if (!file.isEmpty()) {
            String filename = file.getOriginalFilename();
            int index = 0;
            String type = null;
            if (filename != null) {
                index = filename.lastIndexOf(".");
                type = filename.substring(index+1);
            } else {
                return new ResponseData(ResponseState.FILE_NAME_NULL.getValue(), ResponseState.FILE_NAME_NULL.getMessage());
            }

            boolean isPicture = FileUtil.isPicture(type);
            if(isPicture) {
                String result = ossService.uploadFile(file, user.getUid(),type);
                if (result.equals("error")) {
                    return new ResponseData(ResponseState.FILE_UPLOAD_ERROR.getValue(), ResponseState.FILE_UPLOAD_ERROR.getMessage());
                } else {
                    Map<String, Object> data = new HashMap<>();
                    data.put("url", result);
                    return new ResponseData(ResponseState.FILE_UPLOAD_SUCCESS.getValue(), ResponseState.FILE_UPLOAD_SUCCESS.getMessage(), data);
                }
            } else {
                return new ResponseData(ResponseState.FILE_TYPE_ERROR.getValue(), ResponseState.FILE_TYPE_ERROR.getMessage());
            }
        } else {
            return new ResponseData(ResponseState.File_IS_EMPTY.getValue(), ResponseState.File_IS_EMPTY.getMessage());
        }
    }

    @ApiOperation("后台上传图片|传参图片")
    @PostMapping("/uploadFile")
    public ResponseData upload(@RequestParam("photo") MultipartFile file) {
        if (!file.isEmpty()) {
            String filename = file.getOriginalFilename();
            int index = 0;
            String type = null;
            if (filename != null) {
                index = filename.lastIndexOf(".");
                type = filename.substring(index+1);
            } else {
                return new ResponseData(ResponseState.FILE_NAME_NULL.getValue(), ResponseState.FILE_NAME_NULL.getMessage());
            }

            boolean isPicture = FileUtil.isPicture(type);
            if(isPicture) {
                String result = ossService.uploadFile(file,type);
                if (result.equals("error")) {
                    return new ResponseData(ResponseState.FILE_UPLOAD_ERROR.getValue(), ResponseState.FILE_UPLOAD_ERROR.getMessage());
                } else {
                    Map<String, Object> data = new HashMap<>();
                    data.put("url", result);
                    return new ResponseData(ResponseState.FILE_UPLOAD_SUCCESS.getValue(), ResponseState.FILE_UPLOAD_SUCCESS.getMessage(), data);
                }
            } else {
                return new ResponseData(ResponseState.FILE_TYPE_ERROR.getValue(), ResponseState.FILE_TYPE_ERROR.getMessage());
            }
        } else {
            return new ResponseData(ResponseState.File_IS_EMPTY.getValue(), ResponseState.File_IS_EMPTY.getMessage());
        }
    }

    @ApiOperation("人脸识别照片上传|传参图片")
    @PostMapping("/uploadFace")
    public ResponseData uploadFace(@RequestParam("photo") MultipartFile file) {
        if (!file.isEmpty()) {
            String filename = file.getOriginalFilename();
            int index = 0;
            String type = null;
            if (filename != null) {
                index = filename.lastIndexOf(".");
                type = filename.substring(index+1);
            } else {
                return new ResponseData(ResponseState.FILE_NAME_NULL.getValue(), ResponseState.FILE_NAME_NULL.getMessage());
            }

            boolean isPicture = FileUtil.isPicture(type);
            if(isPicture) {
                String result = ossService.uploadFace(file,type);
                if (result.equals("error")) {
                    return new ResponseData(ResponseState.FILE_UPLOAD_ERROR.getValue(), ResponseState.FILE_UPLOAD_ERROR.getMessage());
                } else {
                    Map<String, Object> data = new HashMap<>();
                    data.put("url", result);
                    return new ResponseData(ResponseState.FILE_UPLOAD_SUCCESS.getValue(), ResponseState.FILE_UPLOAD_SUCCESS.getMessage(), data);
                }
            } else {
                return new ResponseData(ResponseState.FILE_TYPE_ERROR.getValue(), ResponseState.FILE_TYPE_ERROR.getMessage());
            }
        } else {
            return new ResponseData(ResponseState.File_IS_EMPTY.getValue(), ResponseState.File_IS_EMPTY.getMessage());
        }
    }

}
