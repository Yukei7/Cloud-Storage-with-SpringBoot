package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private final UserService userService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;
    private final HashService hashService;

    public CredentialController(UserService userService, CredentialService credentialService, EncryptionService encryptionService, HashService hashService) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.hashService = hashService;
    }

    @PostMapping
    public String uploadCredential(@ModelAttribute Credential reqCredential,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("activeTab", "credentials");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        User user = userService.getUser(username);
        if (user == null) {
            redirectAttributes.addFlashAttribute("message", "User not found!");
            return "redirect:/logout";
        }

        // new
        if (reqCredential.getCredentialId() == null) {
            reqCredential.setUserId(user.getUserId());
            credentialService.encryptPassword(reqCredential);
            Integer Id = credentialService.createCredential(reqCredential);
        } else { // modify
            reqCredential.setUserId(user.getUserId());
            credentialService.updateCredentialWithKey(reqCredential);
            credentialService.encryptPassword(reqCredential);
            credentialService.update(reqCredential);
        }
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/result";
    }

    @GetMapping("/delete-credential/{id}")
    public String deleteCredential(@PathVariable Integer id,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("activeTab", "credentials");
        Integer delId = credentialService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/result";
    }

}
