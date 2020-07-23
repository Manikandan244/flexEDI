package com.javainuse.model;

import java.util.ArrayList;
import java.util.List;

public class MappedAllDTO {

    List<FileUploadDTO> fileUploadDTOS = new ArrayList<>();
    /*List<JobDetailDTO> jobDetailDTOS = new ArrayList<>();
    List<InvoiceDetailDTO> invoiceDetailDTOS = new ArrayList<>();*/
    List<JobInvoiceMappedDTO> jobInvoiceMappedDTOS = new ArrayList<>();
    List<GenerateEdiDTO> generateEdiDTOS = new ArrayList<>();
    List<GenerateEdiDTO> generateFtpDTOS = new ArrayList<>();

    public List<FileUploadDTO> getFileUploadDTOS() {
        return fileUploadDTOS;
    }

    public void setFileUploadDTOS(List<FileUploadDTO> fileUploadDTOS) {
        this.fileUploadDTOS = fileUploadDTOS;
    }

    /*public List<JobDetailDTO> getJobDetailDTOS() {
        return jobDetailDTOS;
    }

    public void setJobDetailDTOS(List<JobDetailDTO> jobDetailDTOS) {
        this.jobDetailDTOS = jobDetailDTOS;
    }

    public List<InvoiceDetailDTO> getInvoiceDetailDTOS() {
        return invoiceDetailDTOS;
    }

    public void setInvoiceDetailDTOS(List<InvoiceDetailDTO> invoiceDetailDTOS) {
        this.invoiceDetailDTOS = invoiceDetailDTOS;
    }

    public List<JobInvoiceMappedDTO> getJobInvoiceMappedDTOS() {
        return jobInvoiceMappedDTOS;
    }*/

    public List<JobInvoiceMappedDTO> getJobInvoiceMappedDTOS() {
        return jobInvoiceMappedDTOS;
    }

    public void setJobInvoiceMappedDTOS(List<JobInvoiceMappedDTO> jobInvoiceMappedDTOS) {
        this.jobInvoiceMappedDTOS = jobInvoiceMappedDTOS;
    }

    public List<GenerateEdiDTO> getGenerateEdiDTOS() {
        return generateEdiDTOS;
    }

    public void setGenerateEdiDTOS(List<GenerateEdiDTO> generateEdiDTOS) {
        this.generateEdiDTOS = generateEdiDTOS;
    }

    public List<GenerateEdiDTO> getGenerateFtpDTOS() {
        return generateFtpDTOS;
    }

    public void setGenerateFtpDTOS(List<GenerateEdiDTO> generateFtpDTOS) {
        this.generateFtpDTOS = generateFtpDTOS;
    }
}
