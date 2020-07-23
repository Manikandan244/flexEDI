package com.javainuse.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "generate_edi")
@NamedQueries({
        @NamedQuery(name = "GenerateEdiDTO.fetchJobReportByFromDateToDate",
                query = "SELECT j.jobNo, i.billNo, i.date, s.ediFileName, s.isEdiGenerated, s.generatedDate, s.sequenceNo, s.isFtpUpload, s.modifiedDate " +
                        " FROM GenerateEdiDTO as s " +
                        " INNER JOIN JobDetailDTO as j ON s.jobId = j.id " +
                        " INNER JOIN InvoiceDetailDTO as i ON s.invoiceId = i.id " +
                        " WHERE s.isActive = 1")
})
public class GenerateEdiDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;

    @Column(name = "job_id", nullable = false, updatable = false)
    private int jobId;

    @Column(name = "invoice_id", nullable = false, updatable = false)
    private int invoiceId;

    @Column(name = "is_active", nullable = false)
    private int isActive;

    @Column(name = "is_edi_generated", nullable = false)
    private int isEdiGenerated;

    @Column(name = "generated_date", nullable = true)
    private Date generatedDate;

    @Column(name = "edi_file_path", nullable = true)
    private String ediFilePath;

    @Column(name = "edi_file_name", nullable = true)
    private String ediFileName;

    @Column(name = "sequence_id", nullable = true)
    private int sequenceId;

    @Column(name = "sequence_no", nullable = true)
    private String sequenceNo;

    @Column(name = "is_ftp_upload", nullable = true)
    private int isFtpUpload;

    @Column(name = "created_id", nullable = false, updatable = false)
    private int createdId;

    @Column(name = "created_date", updatable = false)
    @CreationTimestamp
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "modified_id", nullable = true)
    private int modifiedId;

    @Column(name = "modified_date", nullable = true)
    @UpdateTimestamp
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modifiedDate;

    @Transient
    private String jobNo;

    @Transient
    private String importer;

    @Transient
    private String beNo;

    @Transient
    private String billNo;

    @Transient
    private String date;

    @Transient
    private String ediGeneratedDateTime;

    public GenerateEdiDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getIsEdiGenerated() {
        return isEdiGenerated;
    }

    public void setIsEdiGenerated(int isEdiGenerated) {
        this.isEdiGenerated = isEdiGenerated;
    }

    public Date getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(Date generatedDate) {
        this.generatedDate = generatedDate;
    }

    public String getEdiFilePath() {
        return ediFilePath;
    }

    public void setEdiFilePath(String ediFilePath) {
        this.ediFilePath = ediFilePath;
    }

    public String getEdiFileName() {
        return ediFileName;
    }

    public void setEdiFileName(String ediFileName) {
        this.ediFileName = ediFileName;
    }

    public int getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }

    public String getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public int getIsFtpUpload() {
        return isFtpUpload;
    }

    public void setIsFtpUpload(int isFtpUpload) {
        this.isFtpUpload = isFtpUpload;
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

    public int getModifiedId() {
        return modifiedId;
    }

    public void setModifiedId(int modifiedId) {
        this.modifiedId = modifiedId;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getImporter() {
        return importer;
    }

    public void setImporter(String importer) {
        this.importer = importer;
    }

    public String getBeNo() {
        return beNo;
    }

    public void setBeNo(String beNo) {
        this.beNo = beNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEdiGeneratedDateTime() {
        return ediGeneratedDateTime;
    }

    public void setEdiGeneratedDateTime(String ediGeneratedDateTime) {
        this.ediGeneratedDateTime = ediGeneratedDateTime;
    }
}
