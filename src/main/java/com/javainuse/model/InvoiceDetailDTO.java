package com.javainuse.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "invoice_detail")
public class InvoiceDetailDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;

    @Column(name = "date", nullable = true, updatable = false)
    private String date;

    @Column(name = "bill_no", nullable = false, updatable = false)
    private String billNo;

    @Column(name = "organization", nullable = true, updatable = false)
    private String organization;

    @Column(name = "branch", nullable = true, updatable = false)
    private String branch;

    @Column(name = "ref_no", nullable = true, updatable = false)
    private String refNo;

    @Column(name = "amount", nullable = true, updatable = false)
    private String amount;

    @Column(name = "remarks", nullable = true, updatable = false)
    private String remarks;

    @Column(name = "agency_service_charges", nullable = true, updatable = false)
    private String agencyServiceCharges;

    @Column(name = "all_inclusive_charges", nullable = true, updatable = false)
    private String allInclusiveCharges;


    //    private String steamerAgentCharges;

    @Column(name = "survey_charges_flex", nullable = true, updatable = false)
    private String surveyChargesFlex;

    @Column(name = "survey_charges_nt", nullable = true, updatable = false)
    private String surveyChargesNT;

    @Column(name = "cgst", nullable = true, updatable = false)
    private String cgst;

    @Column(name = "sgst", nullable = true, updatable = false)
    private String sgst;

    @Column(name = "is_submitted", nullable = false)
    private int isSubmitted;

    @Column(name = "created_id", nullable = false, updatable = false)
    private int createdId;

    @Column(name = "created_date", updatable = false)
    @CreationTimestamp
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createdDate;

//    private String deliveryCharges;
//    private String transportationCharges;
//    private String cfsCharges;
//    private String iaaiCharges;
//    private String steamerAgentChargesNT;
//    private String bondFormalitiesT;
//    private String haltingCharges;
//    private String cfsChargesNT;
//    private String insuranceChargesNT;
//    private String fumigationCharges;
//    private String loadingUnloadingCharges;
//    private String certificateOfOriginChargesNT;
//    private String parkingCharges;
//    private String ediCharges;
//    private String igst;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAgencyServiceCharges() {
        return agencyServiceCharges;
    }

    public void setAgencyServiceCharges(String agencyServiceCharges) {
        this.agencyServiceCharges = agencyServiceCharges;
    }

    public String getAllInclusiveCharges() {
        return allInclusiveCharges;
    }

    public void setAllInclusiveCharges(String allInclusiveCharges) {
        this.allInclusiveCharges = allInclusiveCharges;
    }

    public String getSurveyChargesFlex() {
        return surveyChargesFlex;
    }

    public void setSurveyChargesFlex(String surveyChargesFlex) {
        this.surveyChargesFlex = surveyChargesFlex;
    }

    public String getSurveyChargesNT() {
        return surveyChargesNT;
    }

    public void setSurveyChargesNT(String surveyChargesNT) {
        this.surveyChargesNT = surveyChargesNT;
    }

    public String getCgst() {
        return cgst;
    }

    public void setCgst(String cgst) {
        this.cgst = cgst;
    }

    public String getSgst() {
        return sgst;
    }

    public void setSgst(String sgst) {
        this.sgst = sgst;
    }

    public int getIsSubmitted() {
        return isSubmitted;
    }

    public void setIsSubmitted(int isSubmitted) {
        this.isSubmitted = isSubmitted;
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
}
