package com.rescue.team.controller;

import com.rescue.team.annotation.ApiJsonObject;
import com.rescue.team.annotation.ApiJsonProperty;
import com.rescue.team.bean.Photo;
import com.rescue.team.bean.ResponseData;
import com.rescue.team.bean.Task;
import com.rescue.team.bean.Volunteer;
import com.rescue.team.bean.state.ResponseState;
import com.rescue.team.service.*;
import com.rescue.team.utils.VerificationCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "任务相关接口")
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private MsgSendService msgSendService;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private FaceService faceService;

    @ApiOperation("新增任务|传参除结束时间end、tid、code之外全部")
    @PostMapping("/addTask")
    public ResponseData addTask(@RequestBody Task task) {
        Photo photo = photoService.getPhotoByEid(task.getEid());
        List<String> photoList = photoService.getPhotoList(photo);
        if(photoList.isEmpty()) return new ResponseData(ResponseState.ELDER_NO_PHOTO.getValue(), ResponseState.ELDER_NO_PHOTO.getMessage());
        String code = VerificationCodeUtil.getCode();
        task.setCode(code);
        boolean b = taskService.insertTask(task);
        if(b) {
            List<String> tels = volunteerService.getVolunteerTels(task);
            if(tels==null) return new ResponseData(ResponseState.NO_VOLUNTEER_HERE.getValue(), ResponseState.NO_VOLUNTEER_HERE.getMessage());
            try {
                boolean addEntity = faceService.addEntity(task.getEid());
                if (!addEntity) {
                    Task tmp = taskService.getTaskByCode(code);
                    boolean deleteTask = taskService.deleteTask(tmp.getTid());
                    return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
                } else {
                    boolean sendTaskCode = msgSendService.sendTaskCode(tels, code);
                    if(!sendTaskCode) {
                        for (String url : photoList) {
                            faceService.addFace(task.getEid(),url);
                        }
                        return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage());
                    } else {
                        boolean deleteEntity = faceService.deleteEntity(task.getEid());
                        return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
                    }
                }
            } catch (Exception e) {
                return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
            }
        } else {
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }

    }

    @ApiOperation("结束任务|传参修改的，必传id")
    @PostMapping("/endTask")
    public ResponseData endTask(@RequestBody Task task) {
        task.setEnd(new Date());
        boolean b = taskService.changeTask(task);
        if(b) {
            boolean deleteEntity = faceService.deleteEntity(task.getEid());
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage());
        } else {
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }
    }

    @ApiOperation("获取任务大厅默认展示的任务|传参志愿者vid")
    @PostMapping("/getDefault")
    public ResponseData getGoingTasks(@ApiJsonObject(name = "getGoingTasks",value = @ApiJsonProperty(key = "vid",example = "志愿者ID")) @RequestBody Map<String,String> parameter) {
        String vid = parameter.get("vid");
        Volunteer volunteer = volunteerService.getVolunteerByVid(vid);
        List<Task> goingTasks = taskService.getGoingTasksByDistrict(volunteer.getDistrict());
        if(goingTasks!=null) {
            Map<String,Object> data = new HashMap<>();
            data.put("task",goingTasks);
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(), data);
        } else {
            return new ResponseData(ResponseState.RESULT_IS_NULL.getValue(), ResponseState.RESULT_IS_NULL.getMessage());
        }
    }

    @ApiOperation("获取任务大厅志愿者筛选的任务|传参最后所筛选县/区district")
    @PostMapping("/getSelect")
    public ResponseData getSelectTask(@ApiJsonObject(name = "getSelectTask",value = @ApiJsonProperty(key = "district",example = "县/区/同级行政单位")) @RequestBody Map<String,String> parameter) {
        String district = parameter.get("district");
        List<Task> goingTasks = taskService.getGoingTasksByDistrict(district);
        if(goingTasks!=null) {
            Map<String,Object> data = new HashMap<>();
            data.put("task",goingTasks);
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(), data);
        } else {
            return new ResponseData(ResponseState.RESULT_IS_NULL.getValue(), ResponseState.RESULT_IS_NULL.getMessage());
        }
    }

    @ApiOperation("根据短信接收到的任务码查询任务|传参任务码code")
    @PostMapping("/getTaskByCode")
    public ResponseData getTaskByCode(@ApiJsonObject(name = "getTaskByCode",value = @ApiJsonProperty(key = "code",example = "任务码")) @RequestBody Map<String,String> parameter) {
        String code = parameter.get("code");
        Task task = taskService.getTaskByCode(code);
        if(task==null) return new ResponseData(ResponseState.TASK_NOT_EXIST.getValue(), ResponseState.TASK_YET_END.getMessage());
        Date end = task.getEnd();
        if(end!=null) return new ResponseData(ResponseState.TASK_YET_END.getValue(), ResponseState.TASK_YET_END.getMessage());
        Map<String,Object> data = new HashMap<>();
        data.put("task",task);
        return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(),data);
    }
}
