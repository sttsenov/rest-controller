package com.example.demo.controller;

import com.example.demo.model.Description;
import com.example.demo.model.Recording;
import com.example.demo.model.User;
import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UsersService service;

    @GetMapping("/")
    public List<User> getHomepage(Model model){
        return service.getAllUsers();
    }

    @PostMapping("/login")
    public ResponseEntity<User> validateUser(@RequestBody User user){
        User check = service.getUserByUsername(user.getUsername());

        if (check.getPassword().equals(user.getPassword())){
            return ResponseEntity.ok(check);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity registerPage(@RequestBody User user){
        if(service.getUserByUsername( user.getUsername() ) == null){
            service.createUser(user);
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("{user}/add/recording")
    public ResponseEntity postRecording(@PathVariable User user, @RequestParam String title, @RequestParam("file") MultipartFile file) throws IOException {
        service.addRecording(title, file, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recording/{id}")
    public String getRecording(@PathVariable String id, Model model) throws Exception{
        Recording rec = service.getRecording(id);
        model.addAttribute("title", rec.getTitle());
        model.addAttribute("url", "/recording/stream/" + id);
        return "recording";
    }

    @GetMapping("/recording/stream/{id}")
    public void streamRecording(@PathVariable String id, HttpServletResponse response) throws Exception {
        Recording rec = service.getRecording(id);
        FileCopyUtils.copy(rec.getFile(), response.getOutputStream());
    }

    @GetMapping("/allRecordings")
    public List<Recording> getAllRecordings() {
        List<Recording> files = service.getAllRecordings();
        return files;
    }

    @DeleteMapping("/user/{id}")
    public String deleteUserById(@PathVariable("id") String id){
        try{
            service.deleteUser(id);
            return "User successfully deleted";
        } catch (Exception e){
            return e.getMessage();
        }
    }
}
