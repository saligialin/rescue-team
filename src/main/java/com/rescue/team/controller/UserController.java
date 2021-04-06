package com.rescue.team.controller;

import com.rescue.team.bean.ResponseData;
import com.rescue.team.bean.User;
import com.rescue.team.bean.state.ResponseState;
import com.rescue.team.service.MsgSendService;
import com.rescue.team.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "用户相关操作控制器")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MsgSendService msgSendService;

    @ApiOperation(value = "获取短信验证码")
    @PostMapping("/getMsgCode")
    public ResponseData getCode(@RequestParam("tel") String tel) {
        try {
            boolean b = msgSendService.sendVerifiedCode(tel);
            if(b) {
                return new ResponseData(ResponseState.MESSAGE_SEND_SUCCESS.getValue(),ResponseState.MESSAGE_SEND_SUCCESS.getMessage());
            } else {
                return new ResponseData(ResponseState.MESSAGE_SEND_ERROR.getValue(),ResponseState.MESSAGE_SEND_ERROR.getMessage());
            }
        }catch (Exception e) {
            log.info("出现异常:"+e);
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseData doRegister(@RequestParam("tel")String tel,@RequestParam("password") String password,@RequestParam("code") String code) {
        boolean b = msgSendService.checkCode(tel, code);
        if(b) {
            return null;
        } else {
            return new ResponseData(ResponseState.VERIFIED_CODE_ERROR.getValue(), ResponseState.VERIFIED_CODE_ERROR.getMessage());
        }
    }

    @PostMapping("/loginByPassword")
    public ResponseData doLogin(@RequestParam("tel") String tel,@RequestParam("password") String password) {
        System.out.println(tel);
        System.out.println(password);
        return null;
    }

    /**
     *
     * @param postUser
     * @return 成功返回状态码、状态信息和更改后的User信息。失败返回状态码和状态信息
     */
    @ApiOperation("新增用户信息")
    @PostMapping("/addUser")
    public ResponseData addUser(@RequestBody User postUser) {
        boolean b = userService.insertUser(postUser);
        if(b) {
            User user = userService.getUserByUid(postUser.getUid());
            user.setPassword(null);
            Map<String,Object> data = new HashMap<>();
            data.put("user",user);
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(), data);
        } else {
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }
    }

    /**
     *
     * @param postUser
     * @return 成功返回状态码、状态信息和更改后的User信息。失败返回状态码和状态信息
     */
    @ApiOperation("修改用户信息")
    @PostMapping("/changeUser")
    public ResponseData changeUser(@RequestBody User postUser) {
        boolean b = userService.changeUser(postUser);
        if(b) {
            User user = userService.getUserByUid(postUser.getUid());
            user.setPassword(null);
            Map<String,Object> data = new HashMap<>();
            data.put("user",user);
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(), data);
        } else {
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }
    }

    /**
     *
     * @param uid
     * @return 成功返回状态码和状态信息。失败返回状态码和状态信息
     */
    @ApiOperation("删除用户信息")
    @PostMapping("/deleteUser")
    public ResponseData deleteUser(@RequestBody String uid) {
        boolean b = userService.deleteUserByUid(uid);
        if(b) {
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage());
        } else {
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }
    }

}
