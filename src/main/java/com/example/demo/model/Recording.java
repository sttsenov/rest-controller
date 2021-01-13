package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;

public class Recording {

    @Id
    private String id;
    
    private String title;
    private InputStream stream;
    private MultipartFile file;

    public Recording() {}

    public Recording(String title) {
        this.title = title;
    }

    public Recording(String title, InputStream stream){
        this.title = title;
        this.stream = stream;
    }

    public Recording(String title, MultipartFile file){
        this.title = title;
        this.file = file;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public InputStream getStream() {
        return stream;
    }

    public void setStream(InputStream stream) {
        this.stream = stream;
    }
}
