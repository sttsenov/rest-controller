package com.example.demo.service;

import com.example.demo.model.Recording;
import com.example.demo.repository.RecordingRepository;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecordingService {
    @Autowired
    RecordingRepository recRep;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations operations;

    public Recording getRecording(String id) throws IllegalStateException, IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        Recording rec = new Recording();

        rec.setTitle(file.getMetadata().get("title").toString());
        rec.setFile(operations.getResource(file).getInputStream());

        return rec;
    }

    public List<Recording> getAllRecordings() throws IllegalStateException{
        List<Recording> files = new ArrayList<>();

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
}
