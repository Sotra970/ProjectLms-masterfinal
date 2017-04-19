package com.example.ahmed.projectlms.Models;

import java.io.Serializable;

/**
 * Created by ahmed on 4/6/2017.
 */

public class Class_model implements Serializable {
    String classimg;
    String teacherClassID;
    String subjectCode;
    String teacherfirstName,teacherlastName,location;
    String classMateFirstName,getClassMateLastName,getClassMateImg;
    String className;
    String classID;

    public String getClassMateFirstName() {
        return classMateFirstName;
    }

    public void setClassMateFirstName(String classMateFirstName) {
        this.classMateFirstName = classMateFirstName;
    }

    public String getGetClassMateLastName() {
        return getClassMateLastName;
    }

    public void setGetClassMateLastName(String getClassMateLastName) {
        this.getClassMateLastName = getClassMateLastName;
    }

    public String getGetClassMateImg() {
        return getClassMateImg;
    }

    public void setGetClassMateImg(String getClassMateImg) {
        this.getClassMateImg = getClassMateImg;
    }

    //teacher_class_id,class_id,class_name,subject_code,firstname,lastname,location
    public String getClassimg() {
        return classimg;
    }

    public void setClassimg(String classimg) {
        this.classimg = classimg;
    }



    public String getTeacherClassID() {
        return teacherClassID;
    }

    public void setTeacherClassID(String teacherClassID) {
        this.teacherClassID = teacherClassID;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getTeacherfirstName() {
        return teacherfirstName;
    }

    public void setTeacherfirstName(String teacherfirstName) {
        this.teacherfirstName = teacherfirstName;
    }

    public String getTeacherlastName() {
        return teacherlastName;
    }

    public void setTeacherlastName(String teacherlastName) {
        this.teacherlastName = teacherlastName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }




    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


}
