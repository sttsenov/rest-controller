package com.example.demo.model;

import org.springframework.data.annotation.Id;

import java.io.InputStream;

public class Recording {

    @Id
    private String id;
    
    private String title;
    private InputStream file;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public InputStream  getFile() {
        return file;
    }

    public void setFile(InputStream file) {
        this.file = file;
    }

    public Recording() {}

    public Recording(String title) {
        this.title = title;
    }

    public Recording(String title, InputStream file){
        this.title = title;
        this.file = file;
    }
}
