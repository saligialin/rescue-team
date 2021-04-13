package com.rescue.team.controller;

import com.rescue.team.annotation.ApiJsonObject;
import com.rescue.team.annotation.ApiJsonProperty;
import com.rescue.team.bean.ResponseData;
import com.rescue.team.bean.User;
import com.rescue.team.bean.state.ResponseState;
import com.rescue.team.service.MsgSendService;
import com.rescue.team.service.UserService;
import com.rescue.team.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "用户相关操作控制器")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MsgSendService msgSendService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @ApiOperation(value = "获取短信验证码|传参手机号")
    @PostMapping("/getMsgCode")
    public ResponseData getCode(@ApiJsonObject(name = "getCode",value = @ApiJsonProperty(key = "tel",example = "注册用手机号")) @RequestBody Map<String,String> parameter) {
        String tel = parameter.get("tel");
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

    @ApiOperation(value = "用户注册|传参手机号、密码和验证码")
    @PostMapping("/register")
    public ResponseData doRegister(@ApiJsonObject(name = "doRegister",value = {
            @ApiJsonProperty( key = "tel", example = "手机号"),
            @ApiJsonProperty( key = "password", example = "密码"),
            @ApiJsonProperty( key = "code", example = "验证码")
    }) @RequestBody Map<String, String> parameter){
        User user = new User();
        user.setTel(parameter.get("tel"));
        user.setPassword(parameter.get("password"));
        String code = parameter.get("code");
        User u = userService.getUserByTel(user.getTel());
        if(u!=null) return new ResponseData(ResponseState.USER_IS_EXIST.getValue(), ResponseState.USER_IS_EXIST.getMessage());
        boolean b = msgSendService.checkCode(user.getTel(), code);
        if(b) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            boolean insertUser = userService.insertUser(user);
            if(insertUser) {
                return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage());
            } else {
                return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
            }
        } else {
            return new ResponseData(ResponseState.VERIFIED_CODE_ERROR.getValue(), ResponseState.VERIFIED_CODE_ERROR.getMessage());
        }
    }

    @ApiOperation(value = "用户通过密码登录|传参手机号和密码")
    @PostMapping("/loginByPassword")
    public ResponseData doLoginByPassword(@ApiJsonObject(name = "doLoginByPassword",value = {
            @ApiJsonProperty( key = "tel", example = "手机号"),
            @ApiJsonProperty( key = "password", example = "密码")
    }) @RequestBody Map<String, String> parameter) {
        String tel = parameter.get("tel");
        String password = parameter.get("password");

        User user = userService.getUserByTel(tel);
        if(user == null) return new ResponseData(ResponseState.USER_NOT_EXIST.getValue(), ResponseState.USER_NOT_EXIST.getMessage());
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        if(matches) {
            Map<String,Object> data = new HashMap<>();
            data.put("user",user);
            String token = jwtUtil.getToken(user.getUid());
            redisTemplate.opsForValue().set("user-"+user.getUid(),token,14, TimeUnit.DAYS);
            data.put("token",token);
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(),data);
        } else {
            return new ResponseData(ResponseState.PASSWORD_ERROR.getValue(), ResponseState.PASSWORD_ERROR.getMessage());
        }
    }

    @ApiOperation(value = "用户通过验证码登录|传参手机号和验证码")
    @PostMapping("/loginByMessage")
    public ResponseData doLoginByMessage(@ApiJsonObject(name = "doLoginByMessage",value = {
            @ApiJsonProperty( key = "tel", example = "手机号"),
            @ApiJsonProperty( key = "code", example = "验证码")
    }) @RequestBody Map<String, String> parameter)  {
        String tel = parameter.get("tel");
        String code = parameter.get("code");

        User user = userService.getUserByTel(tel);
        if(user == null) return new ResponseData(ResponseState.USER_NOT_EXIST.getValue(), ResponseState.USER_NOT_EXIST.getMessage());
        boolean b = msgSendService.checkCode(tel, code);
        if(b) {
            Map<String,Object> data = new HashMap<>();
            data.put("user",user);
            String token = jwtUtil.getToken(user.getUid());
            redisTemplate.opsForValue().set("user-"+user.getUid(),token,14, TimeUnit.DAYS);
            data.put("token",token);
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(),data);
        } else {
            return new ResponseData(ResponseState.VERIFIED_CODE_ERROR.getValue(), ResponseState.VERIFIED_CODE_ERROR.getMessage());
        }
    }

    @ApiOperation("更新token|传参为用户token")
    @PostMapping("/refreshToken")
    public ResponseData refreshToken(@ApiJsonObject(name = "refreshToken",value = @ApiJsonProperty(key = "token",example = "用户token")) @RequestBody Map<String,String> parameter) throws Exception {
        String token = parameter.get("token");
        User user = jwtUtil.getUser(token);
        if(user==null) return new ResponseData(ResponseState.TOKEN_IS_ERROR.getValue(), ResponseState.TOKEN_IS_ERROR.getMessage());
        String redisToken = redisTemplate.opsForValue().get("user-" + user.getUid());
        if(!redisToken.equals(token)) return new ResponseData(ResponseState.TOKEN_IS_ERROR.getValue(), ResponseState.TOKEN_IS_ERROR.getMessage());
        String refreshToken = jwtUtil.getToken(user.getUid());
        redisTemplate.opsForValue().set("user-"+user.getUid(),token,14,TimeUnit.DAYS);
        Map<String,Object> data = new HashMap<>();
        data.put("token",refreshToken);
        return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(), data);
    }

    /**
     *
     * @param postUser
     * @return 成功返回状态码、状态信息和更改后的User信息。失败返回状态码和状态信息
     */
    @ApiOperation("新增用户信息（摆设）")
    @PostMapping("/addUser")
    public ResponseData addUser(@RequestBody User postUser) {
        boolean b = userService.insertUser(postUser);
        if(b){
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
    @ApiOperation("修改用户信息|传参修改的，必传id。密码、角色属性role不传")
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

    @ApiOperation("更改用户密码|传参用户id、手机号、新密码、短信验证码")
    @PostMapping("/changePassword")
    public ResponseData changePassword(@ApiJsonObject(name = "changePassword",value = {
            @ApiJsonProperty( key = "uid", example = "用户ID"),
            @ApiJsonProperty( key = "tel", example = "手机号"),
            @ApiJsonProperty( key = "newPassword", example = "新密码"),
            @ApiJsonProperty( key = "code", example = "验证码")
    }) @RequestBody Map<String, String> parameter)   {
        String uid = parameter.get("uid");
        String tel = parameter.get("tel");
        String newPassword = parameter.get("newPassword");
        String code = parameter.get("code");
        boolean b = msgSendService.checkCode(tel, code);
        if(b) {
            String password = passwordEncoder.encode(newPassword);
            boolean changePassword = userService.changePassword(password, uid);
            if(changePassword) return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage());
            else return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        } else {
            return new ResponseData(ResponseState.VERIFIED_CODE_ERROR.getValue(), ResponseState.VERIFIED_CODE_ERROR.getMessage());
        }
    }

    /**
     *
     * @param
     * @return 成功返回状态码和状态信息。失败返回状态码和状态信息
     */
    @ApiOperation("删除用户信息|传参用户的uid")
    @PostMapping("/deleteUser")
    public ResponseData deleteUser(@ApiJsonObject(name = "deleteUser",value = @ApiJsonProperty(key = "uid",example = "用户ID")) @RequestBody Map<String,String> parameter) {
        String uid = parameter.get("uid");
        boolean b = userService.deleteUserByUid(uid);
        if(b) {
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage());
        } else {
            return new ResponseData(ResponseState.ERROR.getValue(), ResponseState.ERROR.getMessage());
        }
    }

}
