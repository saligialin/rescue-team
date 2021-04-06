package com.rescue.team.filter;

import com.alibaba.fastjson.JSON;
import com.rescue.team.bean.ResponseData;
import com.rescue.team.bean.state.ResponseState;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SimpleAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        ResponseData responseData=new ResponseData(ResponseState.ERROR.getValue(),ResponseState.ERROR.getMessage());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().append(JSON.toJSONString(responseData));
    }
}
