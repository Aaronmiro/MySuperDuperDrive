package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Aaron
 * @date 1/15/22 10:33 AM
 */
@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;

    @PostMapping
    public String uploadFile(Authentication authentication, MultipartFile fileUpload, Model model) throws IOException {
        String filename = fileUpload.getOriginalFilename();
        if(fileService.ifExist(filename)){
            String changeErrorMsg = "Cannot upload two files with the same name";
            model.addAttribute("changeErrorMsg",changeErrorMsg);
            return "/result";
        }
        User user = userService.getUser(authentication.getName());
        File file = new File();
        file.setFilename(filename);
        file.setContentType(fileUpload.getContentType());
        file.setFileSize(String.valueOf(fileUpload.getSize()));
        file.setUserId(user.getUserId());
        file.setFileData(fileUpload.getBytes());

        int result = fileService.insertFile(file);
        if (result > 0) {
            model.addAttribute("changeSuccess", true);
        } else model.addAttribute("changeSuccess", false);
        return "/result";
    }

    @RequestMapping("/delete")
    public String deleteFile(Integer fileId, Model model){
        if (fileService.deleteFileByFileId(fileId)) {
            model.addAttribute("changeSuccess", true);
        } else model.addAttribute("changeSuccess", false);
        return "/result";
    }

    @GetMapping
    public void downloadFile(HttpServletResponse response, Integer fileId){
        File file = fileService.getFileById(fileId);
        response.setContentType(file.getContentType());
        response.setHeader("Content-Length", file.getFileSize());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getFilename() + "\"");
        try {
            FileCopyUtils.copy(file.getFileData(),response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
