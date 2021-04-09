package com.rescue.team.controller;

import com.rescue.team.annotation.ApiJsonObject;
import com.rescue.team.annotation.ApiJsonProperty;
import com.rescue.team.bean.Elder;
import com.rescue.team.bean.ResponseData;
import com.rescue.team.bean.state.ResponseState;
import com.rescue.team.service.ElderService;
import com.rescue.team.service.FaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    private FaceService faceService;

    @ApiOperation("获取当前用户绑定的所有老人|传参当前用户的uid")
    @PostMapping("/getAll")
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

    @ApiOperation(("新增待审核老人|传参出eid、status之外全参"))
    @PostMapping("/add")
    public ResponseData addElder(@RequestBody Elder elder) {
        String eid = elderService.insertElder(elder);
        if(eid!=null) {
            Map<String,Object> data = new HashMap<>();
            data.put("elder",elderService.getElderByEid(eid));
            boolean b = faceService.addEntity(eid);
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(), data);
        } else {
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }
    }

    @ApiOperation("根据eid获取老人信息|传参老人的eid")
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

    @ApiOperation("更改老人信息|参数全传，修改的传修改后的，未修改的也传")
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

    @ApiOperation("删除绑定的老人|传参老人的eid")
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
