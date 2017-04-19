package com.example.ahmed.projectlms.Models;

import java.io.Serializable;

/**
 * Created by ahmed on 4/8/2017.
 */

public class UserModel implements Serializable {
    String student_id,firstName,lastName,class_id,userName,password,student_img,status;



    public String getStudent_id() {
        return student_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
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

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudent_img() {
        return student_img;
    }

    public void setStudent_img(String student_img) {
        this.student_img = student_img;
    }
}
