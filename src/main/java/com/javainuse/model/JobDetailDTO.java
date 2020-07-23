package com.javainuse.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "job_detail")
public class JobDetailDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;

    @Column(name = "job_no", nullable = false, updatable = false)
    private String jobNo;

    @Column(name = "importer", nullable = true, updatable = false)
    private String importer;

    @Column(name = "be_no", nullable = true, updatable = false)
    private String beNo;

    @Column(name = "be_date", nullable = true, updatable = false)
    private String beDate;

    @Column(name = "type_of_be", nullable = true, updatable = false)
    private String typeOfBE;

    @Column(name = "awbno_or_blno", nullable = true, updatable = false)
    private String awbOrblNo;

    @Column(name = "awbdate_or_bldate", nullable = true, updatable = false)
    private String awbOrblDate;

    @Column(name = "hawbno_or_hblno", nullable = true, updatable = false)
    private String hawbOrhblNo;

    @Column(name = "hawbdate_or_hbldate", nullable = true, updatable = false)
    private String hawbOrhblDate;

    @Column(name = "no_of_pkgs", nullable = true, updatable = false)
    private String NoOfPkgs;

    @Column(name = "pkgs_unit", nullable = true, updatable = false)
    private String pkgUnit;

    @Column(name = "gross_weight", nullable = true, updatable = false)
    private String grossWeight;

    @Column(name = "grossweight_unit", nullable = true, updatable = false)
    private String gwUnit;

    @Column(name = "net_weight", nullable = true, updatable = false)
    private String netWeight;

    @Column(name = "netweight_unit", nullable = true, updatable = false)
    private String nwUnit;

    @Column(name = "vessel_or_flight", nullable = true, updatable = false)
    private String vesselOrFlight;

    @Column(name = "voyage_no", nullable = true, updatable = false)
    private String voyageNo;

    @Column(name = "custom_house", nullable = true, updatable = false)
    private String customHouse;

    @Column(name = "port_of_shipment", nullable = true, updatable = false)
    private String portOfShipment;

    @Column(name = "importer_ref_no", nullable = true, updatable = false)
    private String importerRefNo;

    @Column(name = "invoice_number", nullable = true, updatable = false)
    private String invoiceNumber;

    @Column(name = "invoice_date", nullable = true, updatable = false)
    private String invoiceDate;

    @Column(name = "toi", nullable = true, updatable = false)
    private String toi;

    @Column(name = "total_inv_value", nullable = true, updatable = false)
    private String totalInvValue;

    @Column(name = "cif_amount", nullable = true, updatable = false)
    private String cifAmount;

    @Column(name = "assasable_value", nullable = true, updatable = false)
    private String assasableValue;

    @Column(name = "total_duty", nullable = true, updatable = false)
    private String totalDuty;

    @Column(name = "consignor", nullable = true, updatable = false)
    private String consignor;

    @Column(name = "bill_no", nullable = true, updatable = false)
    private String billNo;

    @Column(name = "bill_date", nullable = true, updatable = false)
    private String billDate;

    @Column(name = "is_submitted", nullable = false)
    private int isSubmitted;

    @Column(name = "created_id", nullable = false, updatable = false)
    private int createdId;

    @Column(name = "created_date", updatable = false)
    @CreationTimestamp
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createdDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTypeOfBE() {
        return typeOfBE;
    }

    public void setTypeOfBE(String typeOfBE) {
        this.typeOfBE = typeOfBE;
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

    public String getNoOfPkgs() {
        return NoOfPkgs;
    }

    public void setNoOfPkgs(String noOfPkgs) {
        NoOfPkgs = noOfPkgs;
    }

    public String getPkgUnit() {
        return pkgUnit;
    }

    public void setPkgUnit(String pkgUnit) {
        this.pkgUnit = pkgUnit;
    }

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getGwUnit() {
        return gwUnit;
    }

    public void setGwUnit(String gwUnit) {
        this.gwUnit = gwUnit;
    }

    public String getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(String netWeight) {
        this.netWeight = netWeight;
    }

    public String getNwUnit() {
        return nwUnit;
    }

    public void setNwUnit(String nwUnit) {
        this.nwUnit = nwUnit;
    }

    public String getVesselOrFlight() {
        return vesselOrFlight;
    }

    public void setVesselOrFlight(String vesselOrFlight) {
        this.vesselOrFlight = vesselOrFlight;
    }

    public String getVoyageNo() {
        return voyageNo;
    }

    public void setVoyageNo(String voyageNo) {
        this.voyageNo = voyageNo;
    }

    public String getCustomHouse() {
        return customHouse;
    }

    public void setCustomHouse(String customHouse) {
        this.customHouse = customHouse;
    }

    public String getPortOfShipment() {
        return portOfShipment;
    }

    public void setPortOfShipment(String portOfShipment) {
        this.portOfShipment = portOfShipment;
    }

    public String getImporterRefNo() {
        return importerRefNo;
    }

    public void setImporterRefNo(String importerRefNo) {
        this.importerRefNo = importerRefNo;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getToi() {
        return toi;
    }

    public void setToi(String toi) {
        this.toi = toi;
    }

    public String getTotalInvValue() {
        return totalInvValue;
    }

    public void setTotalInvValue(String totalInvValue) {
        this.totalInvValue = totalInvValue;
    }

    public String getCifAmount() {
        return cifAmount;
    }

    public void setCifAmount(String cifAmount) {
        this.cifAmount = cifAmount;
    }

    public String getAssasableValue() {
        return assasableValue;
    }

    public void setAssasableValue(String assasableValue) {
        this.assasableValue = assasableValue;
    }

    public String getTotalDuty() {
        return totalDuty;
    }

    public void setTotalDuty(String totalDuty) {
        this.totalDuty = totalDuty;
    }

    public String getConsignor() {
        return consignor;
    }

    public void setConsignor(String consignor) {
        this.consignor = consignor;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
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
