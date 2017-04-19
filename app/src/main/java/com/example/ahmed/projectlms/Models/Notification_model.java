package com.example.ahmed.projectlms.Models;

import java.io.Serializable;

/**
 * Created by lenovo on 2/23/2017.
 */

public class Notification_model implements Serializable {
    String teacherClassID ,className,subjectCode,firstName,lastName,notificationData,dateOfNotification,linkNotification,notificationType;
    Class_model class_model ;
    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public Class_model getClass_model() {
        return class_model;
    }

    public void setClass_model(Class_model class_model) {
        this.class_model = class_model;
    }

    public String getTeacherClassID() {
        return teacherClassID;
    }

    public void setTeacherClassID(String teacherClassID) {
        this.teacherClassID = teacherClassID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
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

    public String getNotificationData() {
        return notificationData;
    }

    public void setNotificationData(String notificationData) {
        this.notificationData = notificationData;
    }

    public String getDateOfNotification() {
        return dateOfNotification;
    }

    public void setDateOfNotification(String dateOfNotification) {
        this.dateOfNotification = dateOfNotification;
    }

    public String getLinkNotification() {
        return linkNotification;
    }

    public void setLinkNotification(String linkNotification) {
        this.linkNotification = linkNotification;
    }
//teacher_class_id,class_name,subject_code,firstname,lastname,notification,date_of_notification,link

}
