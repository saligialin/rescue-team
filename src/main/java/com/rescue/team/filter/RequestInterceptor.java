package com.rescue.team.filter;

import com.alibaba.fastjson.JSON;
import com.rescue.team.bean.ResponseData;
import com.rescue.team.bean.User;
import com.rescue.team.bean.state.ResponseState;
import com.rescue.team.exception.RedisTokenNullException;
import com.rescue.team.exception.TokenErrorException;
import com.rescue.team.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("utf8");
        response.setContentType("text/json;charset = UTF-8");
        String authorization=request.getHeader("Authorization");
        if (authorization==null) {
            String result = JSON.toJSONString(new ResponseData(ResponseState.TOKEN_NOT_PROVIDE.getValue(), ResponseState.TOKEN_NOT_PROVIDE.getMessage()));
            response.getWriter().append(result);
            return false;
        }
        String[] bearer = authorization.split(" ");
        if(!bearer[0].equals("Bearer")) {
            String result = JSON.toJSONString(new ResponseData(ResponseState.TOKEN_IS_ERROR.getValue(), ResponseState.TOKEN_IS_ERROR.getMessage()));
            response.getWriter().append(result);
            return false;
        }
        String token = bearer[1];
        try {
            User user = jwtUtil.getUser(token, request);
            return true;
        } catch (RedisTokenNullException e) {
            log.info("token过期");
            log.info(e.getMessage());
            String result = JSON.toJSONString(new ResponseData(ResponseState.TOKEN_IS_EXPIRED.getValue(), ResponseState.TOKEN_IS_EXPIRED.getMessage()));
            response.getWriter().append(result);
            return false;
        } catch (TokenErrorException e) {
            log.info("token错误");
            log.info(e.getMessage());
            String result = JSON.toJSONString(new ResponseData(ResponseState.TOKEN_IS_ERROR.getValue(), ResponseState.TOKEN_IS_ERROR.getMessage()));
            response.getWriter().append(result);
            return false;
        } catch (Exception e) {
            log.info("出现异常：");
            log.info(e.getMessage());
            String result = JSON.toJSONString(new ResponseData(ResponseState.TOKEN_IS_ERROR.getValue(), ResponseState.TOKEN_IS_ERROR.getMessage()));
            response.getWriter().append(result);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
