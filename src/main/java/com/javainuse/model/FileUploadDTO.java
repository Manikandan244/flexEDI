package com.javainuse.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "file_upload")
public class FileUploadDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;

    @Column(name = "file_name", nullable = false, updatable = false)
    private String fileName;

    @Column(name = "type", nullable = false, updatable = false)
    private String type;

    @Column(name = "modified_file_name", nullable = false, updatable = false)
    private String modifiedFileName;

    @Column(name = "file_path", nullable = false, updatable = false)
    private String filePath;

    @Column(name = "file_type", nullable = true, updatable = false)
    private String fileType;

    @Column(name = "file_size", nullable = true, updatable = false)
    private String fileSize;

    @Column(name = "is_active", nullable = false)
    private int isActive;

    @Column(name = "is_uploaded", nullable = false)
    private int isUploaded;

    @Column(name = "created_id", nullable = false, updatable = false)
    private int createdId;

    @Column(name = "created_date", updatable = false)
    @CreationTimestamp
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createdDate;

    @Transient
    private String uploadDateTime;

    public FileUploadDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModifiedFileName() {
        return modifiedFileName;
    }

    public void setModifiedFileName(String modifiedFileName) {
        this.modifiedFileName = modifiedFileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getIsUploaded() {
        return isUploaded;
    }

    public void setIsUploaded(int isUploaded) {
        this.isUploaded = isUploaded;
    }

    public int getCreatedId() {
        return createdId;
    }

    public void setCreatedId(int createdId) {
        this.createdId = createdId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUploadDateTime() {
        return uploadDateTime;
    }

    public void setUploadDateTime(String uploadDateTime) {
        this.uploadDateTime = uploadDateTime;
    }
}
