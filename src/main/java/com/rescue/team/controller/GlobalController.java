package com.rescue.team.controller;

import com.rescue.team.bean.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
@RestController
public class GlobalController {

    @ModelAttribute
    public void getModel(Model model, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        model.addAttribute("user",user);
    }
}
