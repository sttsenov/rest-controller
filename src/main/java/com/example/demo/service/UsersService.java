package com.example.demo.service;

import com.example.demo.model.Recording;
import com.example.demo.model.User;
import com.example.demo.repository.RecordingRepository;
import com.example.demo.repository.UserRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {

    @Autowired
    UserRepository repository;

    @Autowired
    RecordingRepository recRep;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations operations;

    public User getUserById(String id) {
        return repository.findById(id).get();
    }

    public List<User> getUserByFirstName(String firstName){
        return repository.findByFirstName(firstName);
    }

    public List<User> getUserByLastName(String lastName){
        return repository.findByLastName(lastName);
    }

    public User getUserByUsername(String username){ return repository.findByUsername(username); }

    public List<User> getAllUsers(){
        return repository.findAll();
    }

    public void createUser(User user){
        repository.save(user);
    }

    public void deleteUser(String id) {
        repository.deleteById(id);
    }

    public String addRecording(String title, MultipartFile file, User user) throws IOException {
        DBObject metaData = new BasicDBObject();
        metaData.put("type", "audio");
        metaData.put("title", title);

        ObjectId recId = gridFsTemplate.store(
                file.getInputStream(), file.getName(), file.getContentType(), metaData
        );

        user.getRecordingIds().add(recId.toString());
        repository.save(user);

        return recId.toString();
    }

    public Recording getRecording(String id) throws IllegalStateException, IOException{
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        Recording rec = new Recording();

        rec.setTitle(file.getMetadata().get("title").toString());
        rec.setStream(operations.getResource(file).getInputStream());

        return rec;
    }

    public List<Recording> getAllRecordings() throws IllegalStateException{
        List<Recording> files = new ArrayList<>();
        OutputStream out = null;

        GridFSFindIterable file = gridFsTemplate.find(new Query(Criteria.where("_id").exists(true)));

        file.forEach(audio -> {
                    try {
                        files.add(
                                new Recording(
                                        audio.getMetadata().get("title").toString(),
                                        operations.getResource(audio).getInputStream()
                                )
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );

        return files;
    }

    public User getUserWithRecordings(String userId){
        User user = getUserById(userId);

        user.getRecordingIds().stream().forEach(rec -> {
            try {
                user.getFiles().add(getRecording(rec));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return user;
    }
}
