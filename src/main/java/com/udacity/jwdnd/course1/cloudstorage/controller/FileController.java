package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping
public class FileController {

    private final UserService userService;
    private final FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("/file-upload")
    public String uploadFile(@ModelAttribute MultipartFile fileUpload,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (fileUpload.getSize() > 5242880) {
            throw new MaxUploadSizeExceededException(fileUpload.getSize());
        }

        redirectAttributes.addFlashAttribute("activeTab", "files");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        User user = userService.getUser(username);
        if (user == null) {
            redirectAttributes.addFlashAttribute("message", "ERROR: User not found");
            return "redirect:/logout";
        }

        File fileDb = fileService.getByFilename(fileUpload.getOriginalFilename());
        if (fileDb != null) {
            redirectAttributes.addFlashAttribute("message", "File already exists!");
            return "redirect:/result";
        }



        try {
            File file = new File(null,
                    StringUtils.cleanPath(fileUpload.getOriginalFilename()),
                    fileUpload.getContentType(),
                    String.valueOf(fileUpload.getSize()),
                    user.getUserId(),
                    fileUpload.getBytes());
            Integer id = fileService.insert(file);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "An error occurs!\n" + e.getMessage() + "\n");
            return "redirect:/result";
        }

        redirectAttributes.addFlashAttribute("success", true);

        return "redirect:/result";
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity downloadFile(@PathVariable("filename") String filename,
                                       Authentication authentication,
                                       RedirectAttributes redirectAttributes) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        File dFile = null;
        try {
            dFile = fileService.getByFilenameAndUserId(filename, userId);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dFile.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename +"\"")
                .body(dFile.getFiledata());
    }

    @GetMapping("/file-delete/{filename}")
    public String deleteFile(@PathVariable("filename") String filename,
                             RedirectAttributes redirectAttributes) {
        try {
            int delId = fileService.deleteByFilename(filename);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "An error occurs!\n" + e.getMessage() + "\n");
            return "redirect:/result";
        }
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/result";
    }

}
