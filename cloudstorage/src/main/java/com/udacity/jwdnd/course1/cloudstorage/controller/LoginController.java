package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Aaron
 * @date 1/12/22 8:44 PM
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String loginView() {
        return "login";
    }
}
