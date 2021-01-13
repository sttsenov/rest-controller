package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "user")
public class User {

    @Id
    private String id;

    private String username;

    private String firstName;
    private String lastName;

    private String email;
    private String password;

    private List<String> recordingIds = new ArrayList<>();
    private List<Recording> files = new ArrayList<>();

    private Description description;

    public User() { }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Recording> getFiles() {
        return files;
    }

    public void setFiles(List<Recording> files) {
        this.files = files;
    }

    public List<String> getRecordingIds() {
        return recordingIds;
    }

    public void setRecordingIds(List<String> recordingIds) {
        this.recordingIds = recordingIds;
    }

    public Description getDescription(){
        return description;
    }

    public void setDescription(Description desc){
        this.description = desc;
    }

    public void addRecordingToUser(Recording recording){
        this.files.add(recording);
    }

    public void addRecordingIdToUser(String recId){
        this.recordingIds.add(recId);
    }
}
