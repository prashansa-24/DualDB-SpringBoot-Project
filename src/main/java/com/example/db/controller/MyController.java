package com.example.db.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.example.db.entity.MyEntity;
import com.example.db.entity.MyEntityT;
import com.example.db.entity.ResponseData;
import com.example.db.service.MyService;
import com.example.db.service.MyServiceT;

@RestController
@RequestMapping("/api/twoDBFetch")
public class MyController {

    private final MyService myservice;
    
    private final MyServiceT myserviceT;


    public MyController(MyService myservice, MyServiceT myserviceT) {
        this.myservice = myservice;
        this.myserviceT = myserviceT;
    }

    @GetMapping("/db1")
    public ResponseData<Object> getDb1Data() {
        return new ResponseData<>(HttpStatus.OK.value(), "DB1 data fetched successfully", myservice.getAllMyEntities());
    }

    @GetMapping("/db1/{id}")
    public ResponseData<Object> getDb1DataById(@PathVariable Long id) {
        MyEntity entity = myservice.getMyEntityById(id);
        if (entity == null) {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(), "DB1 record not found");
        }
        return new ResponseData<>(HttpStatus.OK.value(), "DB1 data fetched successfully", entity);
    }

    @PostMapping("/db1")
    public ResponseData<Object> saveDb1Data(@RequestBody MyEntity entity) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "DB1 record inserted successfully",
                myservice.saveMyEntity(entity));
    }

    @GetMapping("/db2")
    public ResponseData<Object> getDb2Data() {
        return new ResponseData<>(HttpStatus.OK.value(), "DB2 data fetched successfully", myserviceT.getAllMyEntities());
    }

    @GetMapping("/db2/{id}")
    public ResponseData<Object> getDb2DataById(@PathVariable Long id) {
        MyEntityT entity = myserviceT.getMyEntityTById(id);
        if (entity == null) {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(), "DB2 record not found");
        }
        return new ResponseData<>(HttpStatus.OK.value(), "DB2 data fetched successfully", entity);
    }

    @PostMapping("/db2")
    public ResponseData<Object> saveDb2Data(@RequestBody MyEntityT entity) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "DB2 record inserted successfully",
                myserviceT.saveMyEntity(entity));
    }

    @GetMapping("/all")
    public ResponseData<Object> getAllData() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("db1", myservice.getAllMyEntities());
        response.put("db2", myserviceT.getAllMyEntities());
        return new ResponseData<>(HttpStatus.OK.value(), "Data fetched successfully from both databases", response);
    }
}
