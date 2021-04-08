package com.rescue.team.controller;

import com.rescue.team.bean.ResponseData;
import com.rescue.team.bean.Task;
import com.rescue.team.bean.Volunteer;
import com.rescue.team.bean.state.ResponseState;
import com.rescue.team.service.MsgSendService;
import com.rescue.team.service.TaskService;
import com.rescue.team.service.VolunteerService;
import com.rescue.team.utils.VerificationCodeUtil;
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

@Api(tags = "任务相关控制器")
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private MsgSendService msgSendService;

    @Autowired
    private VolunteerService volunteerService;

    @ApiOperation("新增任务")
    @PostMapping("/addTask")
    public ResponseData addTask(@RequestBody Task task) {
        String code = VerificationCodeUtil.getCode();
        task.setCode(code);
        boolean b = taskService.insertTask(task);
        if(b) {
            List<String> tels = volunteerService.getVolunteerTels(task);
            if(tels==null) return new ResponseData(ResponseState.NO_VOLUNTEER_HERE.getValue(), ResponseState.NO_VOLUNTEER_HERE.getMessage());
            try {
                boolean sendTaskCode = msgSendService.sendTaskCode(tels, code);
                if(sendTaskCode) return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage());
                else return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
            } catch (Exception e) {
                return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
            }
        } else {
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }

    }

    @ApiOperation("结束任务")
    @PostMapping("/endTask")
    public ResponseData endTask(@RequestBody Task task) {
        boolean b = taskService.changeTask(task);
        if(b) {
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage());
        } else {
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }
    }

    @ApiOperation("获取任务大厅默认展示的任务")
    @PostMapping("/getDefault")
    public ResponseData getGoingTasks(String vid) {
        Volunteer volunteer = volunteerService.getVolunteerByVid(vid);
        List<Task> goingTasks = taskService.getGoingTasksByDistrict(volunteer.getDistrict());
        if(goingTasks!=null) {
            Map<String,Object> data = new HashMap<>();
            for(int i=0; i<goingTasks.size(); i++) {
                data.put("task"+i,goingTasks.get(i));
            }
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(), data);
        } else {
            return new ResponseData(ResponseState.RESULT_IS_NULL.getValue(), ResponseState.RESULT_IS_NULL.getMessage());
        }
    }

    @ApiOperation("获取任务大厅志愿者筛选的任务")
    @PostMapping("/getSelect")
    public ResponseData getSelectTask(String district) {
        List<Task> goingTasks = taskService.getGoingTasksByDistrict(district);
        if(goingTasks!=null) {
            Map<String,Object> data = new HashMap<>();
            for(int i=0; i<goingTasks.size(); i++) {
                data.put("task"+i,goingTasks.get(i));
            }
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(), data);
        } else {
            return new ResponseData(ResponseState.RESULT_IS_NULL.getValue(), ResponseState.RESULT_IS_NULL.getMessage());
        }
    }
}
