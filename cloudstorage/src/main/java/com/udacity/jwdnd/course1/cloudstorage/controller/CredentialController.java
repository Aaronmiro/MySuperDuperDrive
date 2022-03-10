package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Aaron
 * @date 1/14/22 2:02 PM
 */
@Controller
@RequestMapping("/credential")
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private UserService userService;

    @PostMapping
    public String postCredential(Authentication authentication, Credential credential, Model model) {
        User user = userService.getUser(authentication.getName());
        credential.setUserId(user.getUserId());
        int result = credentialService.insertCredential(credential);
        if (result > 0) {
            model.addAttribute("changeSuccess", true);
        } else model.addAttribute("changeSuccess", false);
        return "/result";
    }

    @GetMapping
    public String deleteCredential(Integer credentialId, Model model) {
        if (credentialService.deleteCredentialById(credentialId)) {
            model.addAttribute("changeSuccess", true);
        } else model.addAttribute("changeSuccess", false);
        return "/result";
    }
}
