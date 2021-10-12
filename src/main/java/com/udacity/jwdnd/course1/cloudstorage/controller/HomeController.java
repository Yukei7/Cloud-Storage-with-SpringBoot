package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private UserService userService;
    private NoteService noteService;

    public HomeController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @GetMapping
    public String homeView(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        User user = userService.getUser(username);
        if (user == null) {
            model.addAttribute("errorUpload", "ERROR User not found!");
            return "redirect:/logout";
        }

        // notes
        List<Note> notes = noteService.getAllByUserId(user.getUserId());
        if (notes == null) {
            model.addAttribute("errorNote", "Notes not found from this Username!");
            return null;
        }
        model.addAttribute("notes", notes);

        return "home";
    }
}
