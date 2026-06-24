package com.example.db.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.db.entity.MyEntityT;
import com.example.db.repository.MyRepoT;

@Service
public class MyServiceT {

    public final MyRepoT myrepoT;

    public MyServiceT(MyRepoT myrepoT) {
        this.myrepoT = myrepoT;
    }   

    public MyEntityT getMyEntityTById(Long id) {
        return myrepoT.findById(id).orElse(null);
    }

    public List<MyEntityT> getAllMyEntities() {
        return myrepoT.findAll();
    }

    public MyEntityT saveMyEntity(MyEntityT entity) {
        return myrepoT.save(entity);
    }
    
}
