package com.example.db.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.db.entity.MyEntity;
import com.example.db.repository.MyRepo;

@Service
public class MyService {

    public final  MyRepo myrepo;

    public MyService(MyRepo myrepo) {
        this.myrepo = myrepo;
    }   
    public MyEntity getMyEntityById(Long id) {
        return myrepo.findById(id).orElse(null);
    }

    public List<MyEntity> getAllMyEntities() {
        return myrepo.findAll();
    }

    public MyEntity saveMyEntity(MyEntity entity) {
        return myrepo.save(entity);
    }
    
}

