package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signupView() {
        return "signup";
    }

    @PostMapping
    public String signupUser(@ModelAttribute User user,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        String signupError = null;

        if (!userService.isUsernameAvailable(user.getUsername())) {
            signupError = "Username already exists. Please try again.";
        }

        if (signupError == null) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0) {
                signupError = "An error occurs when trying to sign you up. Please try again.";
            }
        }

        if (signupError == null) {
            redirectAttributes.addFlashAttribute("success", true);
            return "redirect:/login";
        } else {
            model.addAttribute("signupError", signupError);
            return "signup";
        }

    }
}
