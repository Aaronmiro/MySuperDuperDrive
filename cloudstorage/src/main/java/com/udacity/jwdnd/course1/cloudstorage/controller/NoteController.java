package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
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
 * @date 1/13/22 2:37 PM
 */
@Controller
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;

    @PostMapping
    public String postNote(Authentication authentication, Note note, Model model) {
        User user = userService.getUser(authentication.getName());
        note.setUserId(user.getUserId());
        int result = noteService.insertNote(note);
        if (result > 0) {
            model.addAttribute("changeSuccess", true);
        } else model.addAttribute("changeSuccess", false);
        return "result";
    }

    @GetMapping
    public String deleteNote(Integer noteId, Model model) {
        if (noteService.deleteNoteByNoteId(noteId)) {
            model.addAttribute("changeSuccess", true);
        } else model.addAttribute("changeSuccess", false);
        return "result";
    }
}
