package com.rescue.team.controller;

import com.rescue.team.bean.Elder;
import com.rescue.team.bean.ResponseData;
import com.rescue.team.bean.state.ResponseState;
import com.rescue.team.service.ElderService;
import com.rescue.team.service.PhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/elder")
@Api(tags = "老人相关控制器")
public class ElderController {

    @Autowired
    private ElderService elderService;

    @Autowired
    private PhotoService photoService;

    @ApiOperation("获取当前用户绑定的所有老人")
    @PostMapping("/getAll")
    @ApiParam(name = "uid", value = "当前用户ID", type = "String", required = true)
    public ResponseData getAll(@RequestBody String uid) {
        List<Elder> elders = elderService.getElderByUid(uid);
        if(elders != null) {
            Map<String, Object> data = new HashMap<>();
            for(int i=0; i<elders.size(); i++) {
                data.put("elder"+i,elders.get(i));
            }
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(), data);
        } else {
            return new ResponseData(ResponseState.RESULT_IS_NULL.getValue(), ResponseState.RESULT_IS_NULL.getMessage());
        }
    }

    @ApiOperation(("新增待审核老人"))
    @PostMapping("/add")
    public ResponseData addElder(@RequestBody Elder elder) {
        boolean b = elderService.insertElder(elder);
        if(b) {
            Map<String,Object> data = new HashMap<>();
            data.put("elder",elder);
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(), data);
        } else {
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }
    }

    @ApiOperation("根据eid获取老人信息")
    @PostMapping("/get")
    public ResponseData getElder(@RequestBody String eid) {
        Elder elder = elderService.getElderByEid(eid);
        if(elder!=null) {
            Map<String, Object> data = new HashMap<>();
            data.put("elder",elder);
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(), data);
        } else {
            return new ResponseData(ResponseState.RESULT_IS_NULL.getValue(), ResponseState.RESULT_IS_NULL.getMessage());
        }
    }

    @ApiOperation("更改老人信息")
    @PostMapping("/change")
    public ResponseData changeElder(@RequestBody Elder elder) {
        boolean b = elderService.changeElder(elder);
        if(b) {
            Map<String, Object> data = new HashMap<>();
            data.put("elder",elder);
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(), data);
        } else {
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }
    }

    @ApiOperation("删除绑定的老人")
    @PostMapping("/delete")
    public ResponseData deleteElder(@RequestBody String eid) {
        boolean b1 = elderService.deleteElder(eid);
        if(b1) {
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage());
        } else {
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }
    }
}
