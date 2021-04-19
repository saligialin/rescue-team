package com.rescue.team.controller;

import com.rescue.team.bean.*;
import com.rescue.team.bean.state.ResponseState;
import com.rescue.team.service.*;
import com.rescue.team.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "第三方接口控制器")
@RestController
@RequestMapping("/third")
public class ThirdPartController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ElderService elderService;

    @Autowired
    private UserService userService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private FaceService faceService;

    @Autowired
    private OssService ossService;

    @ApiOperation("根据区/县名获取该地区正在进行的任务|传参district：县/区名")
    @GetMapping("/getGoingByDistrict/{district}")
    public ResponseData getGoingTasksByDistrict(@PathVariable("district")String district) {
        List<Task> tasks = taskService.getGoingTasksByDistrict(district);
        if (tasks==null||tasks.isEmpty()) {
            return new ResponseData(ResponseState.RESULT_IS_NULL.getValue(), ResponseState.RESULT_IS_NULL.getMessage());
        } else {
            Map<String,Object> data = new HashMap<>();
            data.put("tasks",tasks);
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(), data);
        }
    }

    @ApiOperation("根据区/县名获取该地区所有的任务|传参district：县/区名")
    @GetMapping("/getTasksByDistrict/{district}")
    public ResponseData getTasksByDistrict(@PathVariable("district")String district) {
        List<Task> tasks = taskService.getTaskByByDistrict(district);
        if(tasks==null||tasks.isEmpty()) return new ResponseData(ResponseState.RESULT_IS_NULL.getValue(), ResponseState.RESULT_IS_NULL.getMessage());
        List<Task> going = new ArrayList<>();
        List<Task> done = new ArrayList<>();
        for (Task task : tasks) {
            if(task.getEnd()==null) going.add(task);
            else done.add(task);
        }
        Map<String,Object> data = new HashMap<>();
        data.put("going",going);
        data.put("done",done);
        return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(), data);
    }

    @ApiOperation("根据任务ID获取任务的详细信息|传参tid：任务ID")
    @GetMapping("/getTaskDetail/{tid}")
    public ResponseData getTaskDetail(@PathVariable("tid") String tid) {
        Task task = taskService.getTaskByTid(tid);
        if(task==null) return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        Elder elder = elderService.getElderByEid(task.getEid());
        if(elder==null) return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        User user = userService.getUserByUid(elder.getUid());
        if(user==null) return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        Photo photo = photoService.getPhotoByEid(elder.getEid());
        Map<String, Object> data = new HashMap<>();
        data.put("task",task);
        data.put("elder",elder);
        data.put("family",user);
        data.put("photo",photo);
        return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(), data);
    }

}
