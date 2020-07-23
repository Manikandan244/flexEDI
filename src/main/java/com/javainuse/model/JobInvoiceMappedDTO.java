package com.javainuse.model;

import java.io.Serializable;

public class JobInvoiceMappedDTO implements Serializable {

    private int jobId;
    private String jobNo;
    private String importer;
    private String beNo;
    private String beDate;
    private String awbOrblNo;
    private String awbOrblDate;
    private String hawbOrhblNo;
    private String hawbOrhblDate;

    private int invoiceId;
    private String billNo;
    private String date;
    private String organization;
    private String branch;
    private String refNo;
    private String amount;

    private int createdId;

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
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

    public String getBeDate() {
        return beDate;
    }

    public void setBeDate(String beDate) {
        this.beDate = beDate;
    }

    public String getAwbOrblNo() {
        return awbOrblNo;
    }

    public void setAwbOrblNo(String awbOrblNo) {
        this.awbOrblNo = awbOrblNo;
    }

    public String getAwbOrblDate() {
        return awbOrblDate;
    }

    public void setAwbOrblDate(String awbOrblDate) {
        this.awbOrblDate = awbOrblDate;
    }

    public String getHawbOrhblNo() {
        return hawbOrhblNo;
    }

    public void setHawbOrhblNo(String hawbOrhblNo) {
        this.hawbOrhblNo = hawbOrhblNo;
    }

    public String getHawbOrhblDate() {
        return hawbOrhblDate;
    }

    public void setHawbOrhblDate(String hawbOrhblDate) {
        this.hawbOrhblDate = hawbOrhblDate;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getCreatedId() {
        return createdId;
    }

    public void setCreatedId(int createdId) {
        this.createdId = createdId;
    }
}
