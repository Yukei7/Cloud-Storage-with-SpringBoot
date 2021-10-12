package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }



    @PostMapping()
    public String uploadNote(@ModelAttribute Note reqNote,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("activeTab", "notes");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        User user = userService.getUser(username);
        if (user == null) {
            redirectAttributes.addFlashAttribute("message", "User not found!");
            return "redirect:/logout";
        }

        reqNote.setUserId(user.getUserId());

        if (reqNote.getNoteId() == null) {
            Integer id = noteService.insertOne(reqNote);
        } else {
            noteService.updateOne(reqNote);
        }
        redirectAttributes.addFlashAttribute("success", true);

        return "redirect:/result";
    }
}
