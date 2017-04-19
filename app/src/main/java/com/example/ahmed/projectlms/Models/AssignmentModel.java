package com.example.ahmed.projectlms.Models;

/**
 * Created by Mohab on 4/7/2017.
 */

public class AssignmentModel
{
    String fileName, dateUpload, assDescription, assGrade;
    String file_loc ;
    String id ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFile_loc() {
        return file_loc;
    }

    public void setFile_loc(String file_loc) {
        this.file_loc = file_loc;
    }

    public String getAssGrade() {
        return assGrade;
    }

    public void setAssGrade(String assGrade) {
        this.assGrade = assGrade;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(String dateUpload) {
        this.dateUpload = dateUpload;
    }

    public String getAssDescription() {
        return assDescription;
    }

    public void setAssDescription(String assDescription) {
        this.assDescription = assDescription;
    }
}
