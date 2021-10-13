package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
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
    private CredentialService credentialService;
    private EncryptionService encryptionService;
    private FileService fileService;

    public HomeController(UserService userService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService, FileService fileService) {
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.fileService = fileService;
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

        // files
        List<File> files = fileService.getAllByUserId(user.getUserId());
        if (files == null) {
            model.addAttribute("errorFile", "Files not founded from this Username!");
            return null;
        }
        model.addAttribute("files", files);

        // credentials
        List<Credential> credentials = credentialService.getAllByUserId(user.getUserId());
        if (credentials == null) {
            model.addAttribute("errorCredential", "Credentials not found from this Username!");
            return null;
        }
        model.addAttribute("credentials", credentials);
        model.addAttribute("encryptionService", encryptionService);

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
