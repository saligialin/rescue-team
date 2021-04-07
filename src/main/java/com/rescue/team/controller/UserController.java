package com.rescue.team.controller;

import com.rescue.team.bean.ResponseData;
import com.rescue.team.bean.User;
import com.rescue.team.bean.state.ResponseState;
import com.rescue.team.service.MsgSendService;
import com.rescue.team.service.UserService;
import com.rescue.team.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel",value = "手机号",type = "String"),
            @ApiImplicitParam(name = "password",value = "密码",type = "String"),
            @ApiImplicitParam(name = "code",value = "验证码",type = "String")
    })
    public ResponseData doRegister(@RequestBody Map<String, String> parameter) {
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

    @ApiOperation(value = "用户通过密码登录")
    @PostMapping("/loginByPassword")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel",value = "手机号",type = "String"),
            @ApiImplicitParam(name = "password",value = "密码",type = "String")
    })
    public ResponseData doLoginByPassword(@RequestBody Map<String, String> parameter) {
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

    @ApiOperation(value = "用户通过验证码登录")
    @PostMapping("/loginByMessage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel",value = "手机号",type = "String"),
            @ApiImplicitParam(name = "code",value = "验证码",type = "String")
    })
    public ResponseData doLoginByMessage(@RequestBody Map<String, String> parameter) {
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

    @ApiOperation("更新token")
    @PostMapping("/refreshToken")
    public ResponseData refreshToken(@RequestBody String token) throws Exception {
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
    @ApiOperation("新增用户信息")
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
