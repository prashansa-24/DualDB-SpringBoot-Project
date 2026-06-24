package com.example.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.db.entity.MyEntity;

public interface MyRepo extends JpaRepository<MyEntity, Long> {
    
}
