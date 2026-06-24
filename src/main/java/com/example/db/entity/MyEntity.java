package com.example.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// code for trying two db in one project using spring boot and hibernate


@Entity
@Table(name = "My_Table_CreditC")
public class MyEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    private String UserName;

    private String AccountNumber;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return UserName;
    }       

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }



     


}
