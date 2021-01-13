package com.example.demo.model.form;

import com.example.demo.model.Recording;
import org.springframework.web.multipart.MultipartFile;

public class RecordingForm {
    private MultipartFile file;
    private String username;

    public RecordingForm(){}

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
