package com.rescue.team.controller;

import com.rescue.team.annotation.ApiJsonObject;
import com.rescue.team.annotation.ApiJsonProperty;
import com.rescue.team.bean.*;
import com.rescue.team.bean.state.ResponseState;
import com.rescue.team.service.MemberService;
import com.rescue.team.service.TaskService;
import com.rescue.team.service.VolunteerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "志愿者相关控制器")
@RestController
@RequestMapping("/volunteer")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private TaskService taskService;

    @ApiOperation("获取当前用户对应的志愿者信息|传参当前用户的uid")
    @PostMapping("/get")
    public ResponseData getVolunteer(@RequestBody String uid) {
        Volunteer volunteer = volunteerService.getVolunteerByVid(uid);
        if(volunteer!=null) {
            Map<String, Object> data = new HashMap<>();
            data.put("volunteer",volunteer);
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(), data);
        } else {
            return new ResponseData(ResponseState.RESULT_IS_NULL.getValue(), ResponseState.RESULT_IS_NULL.getMessage());
        }
    }

    @ApiOperation("增加待审批志愿者|传参除vid，status之外全部")
    @PostMapping("/add")
    public ResponseData addVolunteer(@RequestBody Volunteer volunteer, @ApiIgnore @ModelAttribute("user")User user) {
        volunteer.setVid(user.getUid());
        boolean b = volunteerService.insertVolunteer(volunteer);
        if (b) {
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage());
        } else {
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }
    }

    @ApiOperation("更该志愿者信息|参数全传，修改的传修改后的，未修改的也传")
    @PostMapping("/change")
    public ResponseData changeVolunteer(@RequestBody Volunteer volunteer) {
        boolean b = volunteerService.changeVolunteer(volunteer);
        if (b) {
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage());
        } else {
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }
    }

    @ApiOperation("注销志愿者服务|传参志愿者vid")
    @PostMapping("/delete")
    public ResponseData deleteVolunteer(@RequestBody String vid) {
        boolean b = volunteerService.deleteVolunteer(vid);
        if (b) {
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage());
        } else {
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }
    }


    @ApiOperation("志愿者状态更改为繁忙|传参志愿者vid")
    @PostMapping("/beBusy")
    public ResponseData beBusy(@RequestBody String vid) {
        boolean b = volunteerService.beBusy(vid);
        if (b) {
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage());
        } else {
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }
    }

    @ApiOperation("志愿者设备故障|传参志愿者vid")
    @PostMapping("/beBusy")
    public ResponseData beFault(@RequestBody String vid) {
        boolean b = volunteerService.beFault(vid);
        if (b) {
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage());
        } else {
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }
    }

    @ApiOperation("志愿者状态更改为空闲|传参志愿者vid")
    @PostMapping("byIdle")
    public ResponseData beIdle(@RequestBody String vid) {
        boolean b = volunteerService.beIdle(vid);
        if (b) {
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage());
        } else {
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }
    }

    @ApiOperation("获取历史任务|传参志愿者vid")
    @PostMapping("/getMyTasks")
    public ResponseData getPastTasks(@RequestBody String vid) {
        List<Member> members = memberService.getMemberByVid(vid);
        if(members != null) {
            Map<String, Object> data = new HashMap<>();
            for(int i=0; i<members.size(); i++) {
                Task task = taskService.getTaskByTid(members.get(i).getTid());
                data.put("task"+i,task);
            }
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(), data);
        } else {
            return new ResponseData(ResponseState.RESULT_IS_NULL.getValue(), ResponseState.RESULT_IS_NULL.getMessage());
        }
    }

    @ApiOperation("加入任务|传参志愿者vid、任务tid")
    @PostMapping("/joinTask")
    public ResponseData joinTask(@RequestBody Member postMember) {
        Member member = memberService.getMemberByTidAndVid(postMember.getTid(), postMember.getVid());

        Task task = taskService.getTaskByTid(postMember.getTid());
        if(task == null) {
            return new ResponseData(ResponseState.TASK_NOT_EXIST.getValue(), ResponseState.TASK_NOT_EXIST.getMessage());
        } else {
            Map<String, Object> data = new HashMap<>();
            data.put("task",task);

            if(member==null) {
                boolean b = memberService.insertMember(postMember);
                return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(), data);
            } else {
                return new ResponseData(ResponseState.ALREADY_DID_IT.getValue(), ResponseState.ALREADY_DID_IT.getMessage(), data);
            }
        }

    }

    @ApiOperation("防BUG用，无视")
    @PostMapping("/ggBug")
    public ResponseData zzTest(@ApiJsonObject(name = "zzTest",value = {
            @ApiJsonProperty( key = "zz", description = "zzz"),
            @ApiJsonProperty( key = "zzz", description = "zz")
    }) @RequestBody Map<String, Object> parameter) {
        return null;
    }

}
