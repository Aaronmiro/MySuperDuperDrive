package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.models.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Aaron
 * @date 1/12/22 9:16 PM
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @GetMapping
    public String homeView(Authentication authentication, Model model) {
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();

        List<Note> notes = noteService.getNotesByUserId(userId);

        List<Credential> credentialsEnc = credentialService.getCredentialsByUserId(user.getUserId());
        List<CredentialForm> credentials = credentialService.decryptPassword(credentialsEnc);

        List<File> files = fileService.getFilesByUserId(userId);
        List<FileForm> fileForms = fileService.getFileForms(files);

        model.addAttribute("notes", notes);
        model.addAttribute("credentials", credentials);
        model.addAttribute("fileForms", fileForms);
        return "home";
    }


}
