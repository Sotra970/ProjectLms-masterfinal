package com.example.ahmed.projectlms.Models;

/**
 * Created by Mohab on 4/8/2017.
 */

public class DownloadableModel
{
    private String  downloadbleFileName, downloadableDescription,
            downloadableUploadedBy, downloadableDateUpload, downloadableFileLoc;

    public String getDownloadableFileLoc() {
        return downloadableFileLoc;
    }

    public void setDownloadableFileLoc(String downloadableFileLoc) {
        this.downloadableFileLoc = downloadableFileLoc;
    }

    public String getDownloadbleFileName() {
        return downloadbleFileName;
    }

    public void setDownloadbleFileName(String downloadbleFileName) {
        this.downloadbleFileName = downloadbleFileName;
    }

    public String getDownloadableDescription() {
        return downloadableDescription;
    }

    public void setDownloadableDescription(String downloadableDescription) {
        this.downloadableDescription = downloadableDescription;
    }

    public String getDownloadableUploadedBy() {
        return downloadableUploadedBy;
    }

    public void setDownloadableUploadedBy(String downloadableUploadedBy) {
        this.downloadableUploadedBy = downloadableUploadedBy;
    }

    public String getDownloadableDateUpload() {
        return downloadableDateUpload;
    }

    public void setDownloadableDateUpload(String downloadableDateUpload) {
        this.downloadableDateUpload = downloadableDateUpload;
    }
}
