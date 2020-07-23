package com.javainuse.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "miscellaneous")
public class MiscellaneousDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;

    @Column(name = "region")
    private String region;

    @Column(name = "country")
    private String country;

    @Column(name = "company")
    private String company;

    @Column(name = "as2_qualifier")
    private String as2Qualifier;

    @Column(name = "as2_isa_test_id")
    private String as2ISATestId;

    @Column(name = "as2_isa_prod_id")
    private String as2ISAProdId;

    @Column(name = "gs_id_test_or_prod")
    private String gsIdTestOrProd;

    @Column(name = "test_qualifier")
    private String testQualifier;

    @Column(name = "test_isa_id")
    private String testISAID;

    @Column(name = "test_gs_id")
    private String testGSID;

    @Column(name = "prod_qualifier")
    private String prodQualifier;

    @Column(name = "prod_isa_id")
    private String prodISAID;

    @Column(name = "prod_gs_id")
    private String prodGSID;

    @Column(name = "is_active")
    private int isActive;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAs2Qualifier() {
        return as2Qualifier;
    }

    public void setAs2Qualifier(String as2Qualifier) {
        this.as2Qualifier = as2Qualifier;
    }

    public String getAs2ISATestId() {
        return as2ISATestId;
    }

    public void setAs2ISATestId(String as2ISATestId) {
        this.as2ISATestId = as2ISATestId;
    }

    public String getAs2ISAProdId() {
        return as2ISAProdId;
    }

    public void setAs2ISAProdId(String as2ISAProdId) {
        this.as2ISAProdId = as2ISAProdId;
    }

    public String getGsIdTestOrProd() {
        return gsIdTestOrProd;
    }

    public void setGsIdTestOrProd(String gsIdTestOrProd) {
        this.gsIdTestOrProd = gsIdTestOrProd;
    }

    public String getTestQualifier() {
        return testQualifier;
    }

    public void setTestQualifier(String testQualifier) {
        this.testQualifier = testQualifier;
    }

    public String getTestISAID() {
        return testISAID;
    }

    public void setTestISAID(String testISAID) {
        this.testISAID = testISAID;
    }

    public String getTestGSID() {
        return testGSID;
    }

    public void setTestGSID(String testGSID) {
        this.testGSID = testGSID;
    }

    public String getProdQualifier() {
        return prodQualifier;
    }

    public void setProdQualifier(String prodQualifier) {
        this.prodQualifier = prodQualifier;
    }

    public String getProdISAID() {
        return prodISAID;
    }

    public void setProdISAID(String prodISAID) {
        this.prodISAID = prodISAID;
    }

    public String getProdGSID() {
        return prodGSID;
    }

    public void setProdGSID(String prodGSID) {
        this.prodGSID = prodGSID;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
}
