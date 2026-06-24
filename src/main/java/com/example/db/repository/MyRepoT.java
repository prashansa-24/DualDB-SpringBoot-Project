package com.example.db.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.db.entity.MyEntityT;

 public interface MyRepoT extends JpaRepository<MyEntityT, Long> {

 }