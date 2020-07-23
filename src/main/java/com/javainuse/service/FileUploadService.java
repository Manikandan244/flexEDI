package com.javainuse.service;

import com.javainuse.controllers.EdiController;
import com.javainuse.controllers.FileStorageProperties;
import com.javainuse.exception.EdiException;
import com.javainuse.exception.ResourceNotFoundException;
import com.javainuse.model.*;
import com.javainuse.org.response.ApiResponse;
import com.javainuse.repository.FileUploadRepository;
import com.javainuse.repository.InvoiceDetailRepository;
import com.javainuse.repository.JobDetailRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class FileUploadService {

    @Autowired
    private FileUploadRepository fileUploadRepository;

    @Autowired
    private FileStorageProperties fileStorageProperties;

    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    @Autowired
    private JobDetailRepository jobDetailRepository;

    @Autowired
    private EdiProcessService ediProcessService;

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(EdiController.class);

    public static final String SUCCESS = "success";

    @Transactional(rollbackFor = Exception.class)
    public ApiResponse save(MultipartFile file, String getType, int userId) throws EdiException, Throwable {
        LOGGER.info("ENTERING [ fileUpload ] controller.");

        List<Object> list = new ArrayList<>();
        FileOutputStream fileOut = null;
        try {
//            for (int i = 0; i < file.length; i++) {
            FileUploadDTO fileUploadDTO = new FileUploadDTO();

            String[] fileList = file.getOriginalFilename().split("\\.");
            Date date = new Date();

            String modifiedFileName = fileList[0] + "-" + date.getTime();
            String localDirectory = fileStorageProperties.getDirectory();
            if (localDirectory == null) {
                localDirectory = "D:\\tmp\\fileUpload\\";
            }
            LOGGER.info(" Local Directory to create files [ " + localDirectory + "]");
            String filePath = localDirectory + modifiedFileName + "." + fileList[1];
            LOGGER.info(" Temp Path to generate file : [" + filePath + "]");

            File convertFile = new File(filePath);
            convertFile.createNewFile();

            fileOut = new FileOutputStream(convertFile);
            fileOut.write(file.getBytes());
            fileOut.close();

            String fileSize = String.valueOf(Math.round(file.getSize() / 1024)) + " KB";
            fileUploadDTO.setFileName(file.getOriginalFilename());
            fileUploadDTO.setType(getType);
            fileUploadDTO.setModifiedFileName(modifiedFileName + "." + fileList[1]);
            fileUploadDTO.setFilePath(filePath);
            fileUploadDTO.setFileType(fileList[1]);
            fileUploadDTO.setFileSize(fileSize);
            fileUploadDTO.setIsActive(1);
            fileUploadDTO.setCreatedId(userId);

            list.add(fileUploadDTO);
            FileUploadDTO fileUploadDTO1 = fileUploadRepository.save(fileUploadDTO);
//            }
            return new ApiResponse(HttpStatus.OK, "Saved Successfully..!!", find(fileUploadDTO1.getId()));
        } catch (IOException e) {
            LOGGER.error(e);
            throw new EdiException(e.getMessage());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            LOGGER.error(throwable);
            throw new EdiException(throwable.getMessage());
        }
    }

    public FileUploadDTO find(int id) throws Throwable {
        try {
            FileUploadDTO fileUploadDTO = fileUploadRepository.findById(id);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
            String stringDate = dateFormat.format(fileUploadDTO.getCreatedDate());
            fileUploadDTO.setUploadDateTime(stringDate);
            return fileUploadDTO;
        } catch (Exception e) {
            LOGGER.error("Package: com.javainuse.controllers, Class: FileUploadController, Method: find()-> Exception" + e + "\n,Exception Message: " + e.getMessage());
            throw new EdiException(e);
        }
    }

    public MappedAllDTO fetchAllRecords() throws EdiException {
        MappedAllDTO mappedAllDTO = null;
        try {
            mappedAllDTO = new MappedAllDTO();

            mappedAllDTO.setFileUploadDTOS(getAllFileRecord());
            /*mappedAllDTO.setJobDetailDTOS(getAllJobRecord());
            mappedAllDTO.setInvoiceDetailDTOS(getAllInvoiceRecord());*/
            mappedAllDTO.setJobInvoiceMappedDTOS(getAllJobInvoiceMappedDetails());
            mappedAllDTO.setGenerateEdiDTOS(ediProcessService.getAllEDIRecord());
            mappedAllDTO.setGenerateFtpDTOS(ediProcessService.getAllFTPRecord());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mappedAllDTO;
    }

    private List<FileUploadDTO> getAllFileRecord() throws EdiException {
        List<FileUploadDTO> fileUploadDTOList = null;
        try {
            fileUploadDTOList = fileUploadRepository.findByIsActiveAndIsUploaded(1, 0);
            for (FileUploadDTO fileUploadDTO : fileUploadDTOList) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
                String stringDate = dateFormat.format(fileUploadDTO.getCreatedDate());
                fileUploadDTO.setUploadDateTime(stringDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUploadDTOList;
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiResponse update(int id) throws EdiException {
        FileUploadDTO fileUploadDTO1 = null;
        try {
            FileUploadDTO fileUploadDTO = fileUploadRepository.findById(id);
            fileUploadDTO.setIsActive(0);

            fileUploadDTO1 = fileUploadRepository.save(fileUploadDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fileUploadDTO1 != null) {
            return new ApiResponse(HttpStatus.OK, "Updated Successfully..!!", getAllFileRecord());
        } else {
            return new ApiResponse(HttpStatus.BAD_REQUEST, "Id Not exists.. can not update.!!", getAllFileRecord());
        }
    }

//    public ApiResponse submit(Map<String, Object>[] stuffs) throws EdiException {
//            for (Map<String, Object> map : stuffs) {
//                for (Map.Entry<String, Object> entry : map.entrySet()) {
//                    System.out.println(entry.getKey() + " - " + entry.getValue());
//                }
//            }

//            Arrays.stream(stuffs).forEach(map -> {
//                map.entrySet().forEach(entry -> {
//                    String key = entry.getKey();

//                    if (key.equalsIgnoreCase("id")) {
//                        int id = (int) entry.getValue();
//                        fileUploadDTO.setId(id);
//                        LOGGER.info("id: " + id);
//                    } else if (key.equalsIgnoreCase("filePath")) {
//                        String filePath = (String) entry.getValue();
//                        fileUploadDTO.setFilePath(filePath);
//                        LOGGER.info("file path: " + filePath);
//                    } else if (key.equalsIgnoreCase("fileType")) {
//                        String fileType = (String) entry.getValue();
//                        fileUploadDTO.setFileType(fileType);
//                        LOGGER.info("extension: " + fileType);
//                    } else if (key.equalsIgnoreCase("type")) {
//                        String type = (String) entry.getValue();
//                        fileUploadDTO.setType(type);
//                        LOGGER.info("file type: " + type);
//                    }
//                });
//        });


    @Transactional(rollbackFor = Exception.class)
    public ApiResponse submit(MappedAllDTO mappedFileJobInvoiceDTO) throws EdiException {
        LOGGER.info("ENTERING [ submit ] controller.");
        try {
            for (FileUploadDTO fileUploadDTO : mappedFileJobInvoiceDTO.getFileUploadDTOS()) {
                fileUploadDTO.setIsActive(1);
                fileUploadDTO.setIsUploaded(1);
                fileUploadRepository.save(fileUploadDTO);

                if (fileUploadDTO.getFilePath() != null) {
                    FileInputStream fileInputStream = null;
                    try {
                        File file = new File(fileUploadDTO.getFilePath());
                        fileInputStream = new FileInputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        LOGGER.error(e.getMessage());
                        throw new ResourceNotFoundException("File path not found ::" + fileUploadDTO.getFilePath());
                    }

                    if (fileUploadDTO.getType().equalsIgnoreCase("Invoice")) {
                        insertInvoiceDtl(fileInputStream, mappedFileJobInvoiceDTO, fileUploadDTO.getFileType(), fileUploadDTO.getCreatedId());
                    } else if (fileUploadDTO.getType().equalsIgnoreCase("Job")) {
                        insertJobDtl(fileInputStream, mappedFileJobInvoiceDTO, fileUploadDTO.getFileType(), fileUploadDTO.getCreatedId());
                    }
                } else {
                    throw new ResourceNotFoundException("File path not found ::" + fileUploadDTO.getFilePath());
                }
            }
            mappedFileJobInvoiceDTO.setFileUploadDTOS(getAllFileRecord());
            /*mappedFileJobInvoiceDTO.setInvoiceDetailDTOS(getAllInvoiceRecord());
            mappedFileJobInvoiceDTO.setJobDetailDTOS(getAllJobRecord());*/
            mappedFileJobInvoiceDTO.setJobInvoiceMappedDTOS(getAllJobInvoiceMappedDetails());


//            if ((mappedFileJobInvoiceDTO.getJobDetailDTOS() != null) && (mappedFileJobInvoiceDTO.getInvoiceDetailDTOS() != null)) {
            if (mappedFileJobInvoiceDTO.getJobInvoiceMappedDTOS() != null) {
                return new ApiResponse(HttpStatus.OK, "Saved Successfully..!!", mappedFileJobInvoiceDTO);
            } else {
                return new ApiResponse(HttpStatus.BAD_REQUEST, "Something went wrong..!!", null);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new EdiException(e.getMessage());
        }
    }

    private Workbook getWorkBook(FileInputStream fileInputStream, String extension) throws EdiException {
        Workbook workbook = null;
        try {
            if (extension.equalsIgnoreCase("xlsx")) {
                workbook = new XSSFWorkbook(fileInputStream);
            } else if (extension.equalsIgnoreCase("xls")) {
                workbook = new HSSFWorkbook(fileInputStream);
            }
        } catch (Exception e) {
            LOGGER.error(e);
            throw new EdiException(e.getMessage());
        }
        return workbook;
    }

    private MappedAllDTO insertInvoiceDtl(FileInputStream fileInputStream, MappedAllDTO mappedFileJobInvoiceDTO, String extension, int userId) {
        LOGGER.info("entering invoice insert method: ");
        try {
            Workbook workbook = getWorkBook(fileInputStream, extension);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            rows.next();
            while (rows.hasNext()) {
                Row row = rows.next();
                if (!(row.getCell(0) + "").toString().trim().equalsIgnoreCase("null") && !(row.getCell(0) + "").toString().trim().equals("")) {
                    InvoiceDetailDTO invoiceDetailDTO = new InvoiceDetailDTO();

                    invoiceDetailDTO.setDate((row.getCell(0) + "").toString());
                    invoiceDetailDTO.setBillNo((row.getCell(1) + "").toString());

                    invoiceDetailDTO.setOrganization((row.getCell(2) + "").toString());
                    invoiceDetailDTO.setBranch((row.getCell(3) + "").toString());
                    invoiceDetailDTO.setRefNo((row.getCell(4) + "").toString());

                    invoiceDetailDTO.setAmount((row.getCell(5) + "").toString());
                    invoiceDetailDTO.setRemarks((row.getCell(6) + "").toString());
//                    if(row.getCell(6).getStringCellValue() != null && !(row.getCell(6).getStringCellValue().equals(""))) {
//                        invoiceDetailDTO.setRemarks(row.getCell(6).getStringCellValue());
//                    } else {
//                        invoiceDetailDTO.setRemarks("");
//                    }
                    invoiceDetailDTO.setAgencyServiceCharges((row.getCell(7) + "").toString());
                    invoiceDetailDTO.setAllInclusiveCharges((row.getCell(8) + "").toString());
                    invoiceDetailDTO.setSurveyChargesFlex((row.getCell(9) + "").toString());
                    invoiceDetailDTO.setSurveyChargesNT((row.getCell(10) + "").toString());

                    invoiceDetailDTO.setCgst((row.getCell(11) + "").toString());
                    invoiceDetailDTO.setSgst((row.getCell(12) + "").toString());

                    invoiceDetailDTO.setIsSubmitted(0);
                    invoiceDetailDTO.setCreatedId(userId);


//                    if(row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC){
//                        String phNo = NumberToTextConverter.toText(row.getCell(0).getNumericCellValue());
//                        invoiceDetailDTO.setBillNo(phNo);
//                    }
//                    if(row.getCell(1).getCellType() == Cell.CELL_TYPE_STRING){
//                        invoiceDetailDTO.setDate(row.getCell(1).getStringCellValue());
//                    }
//                    Cell cell =row.getCell(2);
//                    invoiceDetailDTO.setAmount(cell.toString());

                    invoiceDetailRepository.save(invoiceDetailDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mappedFileJobInvoiceDTO;
    }

    private List<InvoiceDetailDTO> getAllInvoiceRecord() throws EdiException {
        List<InvoiceDetailDTO> invoiceDetailDTOS = null;
        try {
            invoiceDetailDTOS = invoiceDetailRepository.findByIsSubmitted(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invoiceDetailDTOS;
    }

    private MappedAllDTO insertJobDtl(FileInputStream fileInputStream, MappedAllDTO mappedFileJobInvoiceDTO, String extension, int userId) {
        LOGGER.info("entering job insert method: ");
        try {
            Workbook workbook = getWorkBook(fileInputStream, extension);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            rows.next();
            while (rows.hasNext()) {
                Row row = rows.next();
                if (!(row.getCell(0) + "").toString().equalsIgnoreCase("null") && !(row.getCell(0) + "").toString().equals("")) {
                    JobDetailDTO jobDetailDTO = new JobDetailDTO();

                    jobDetailDTO.setJobNo((row.getCell(0) + "").toString());
                    jobDetailDTO.setImporter((row.getCell(1) + "").toString());
                    jobDetailDTO.setBeNo((row.getCell(2) + "").toString());
                    jobDetailDTO.setBeDate((row.getCell(3) + "").toString());
                    jobDetailDTO.setTypeOfBE((row.getCell(4) + "").toString());

                    jobDetailDTO.setAwbOrblNo((row.getCell(5) + "").toString());
                    jobDetailDTO.setAwbOrblDate((row.getCell(6) + "").toString());
                    jobDetailDTO.setHawbOrhblNo((row.getCell(7) + "").toString());
                    jobDetailDTO.setHawbOrhblDate((row.getCell(8) + "").toString());

                    jobDetailDTO.setNoOfPkgs((row.getCell(9) + "").toString());
                    jobDetailDTO.setPkgUnit((row.getCell(10) + "").toString());

                    jobDetailDTO.setGrossWeight((row.getCell(11) + "").toString());
                    jobDetailDTO.setGwUnit((row.getCell(12) + "").toString());

                    jobDetailDTO.setNetWeight((row.getCell(13) + "").toString());
                    jobDetailDTO.setNwUnit((row.getCell(14) + "").toString());

                    jobDetailDTO.setVesselOrFlight((row.getCell(15) + "").toString());
                    jobDetailDTO.setVoyageNo((row.getCell(16) + "").toString());

                    jobDetailDTO.setCustomHouse((row.getCell(17) + "").toString());
                    jobDetailDTO.setPortOfShipment((row.getCell(18) + "").toString());
                    jobDetailDTO.setImporterRefNo((row.getCell(19) + "").toString());

                    jobDetailDTO.setInvoiceNumber((row.getCell(20) + "").toString());
                    jobDetailDTO.setInvoiceDate((row.getCell(21) + "").toString());

                    jobDetailDTO.setToi((row.getCell(22) + "").toString());
                    jobDetailDTO.setTotalInvValue((row.getCell(23) + "").toString());
                    jobDetailDTO.setCifAmount((row.getCell(24) + "").toString());
                    jobDetailDTO.setAssasableValue((row.getCell(25) + "").toString());
                    jobDetailDTO.setTotalDuty((row.getCell(26) + "").toString());

                    jobDetailDTO.setConsignor((row.getCell(27) + "").toString());

                    jobDetailDTO.setBillNo((row.getCell(28) + "").toString());
                    jobDetailDTO.setBillDate((row.getCell(29) + "").toString());

                    jobDetailDTO.setIsSubmitted(0);
                    jobDetailDTO.setCreatedId(userId);


//                    if(row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING){
//                        jobDetailDTO.setJobNo(row.getCell(0).getStringCellValue());
//                    }
//                    if(row.getCell(1).getCellType() == Cell.CELL_TYPE_STRING){
//                        jobDetailDTO.setImporter(row.getCell(1).getStringCellValue());
//                    }
//                    jobDetailDTO.setBeDate(row.getCell(2).getDateCellValue());
//                    if(row.getCell(3).getCellType() == Cell.CELL_TYPE_STRING){
//                        jobDetailDTO.setTypeOfBE(row.getCell(3).getStringCellValue());
//                    }
//                    if(row.getCell(4).getCellType() == Cell.CELL_TYPE_NUMERIC){
//                        String blNo = NumberToTextConverter.toText(row.getCell(4).getNumericCellValue());
//                        jobDetailDTO.setAwbOrblNo(blNo);
//                    }
//                    jobDetailDTO.setAwbOrblDate(row.getCell(5).getDateCellValue());
//                    if(row.getCell(6).getCellType() == Cell.CELL_TYPE_NUMERIC){
//                        String hblNo = NumberToTextConverter.toText(row.getCell(6).getNumericCellValue());
//                        jobDetailDTO.setHawbOrhblNo(hblNo);
//                    }
//                    jobDetailDTO.setHawbOrhblDate(row.getCell(7).getDateCellValue());
//                    if(row.getCell(9).getCellType() == Cell.CELL_TYPE_STRING){
//                        jobDetailDTO.setPkgUnit(row.getCell(9).getStringCellValue());
//                    }
//                    Cell cell =row.getCell(2);
//                    jobDetailDTO.setBillNo(cell.toString());

                    jobDetailRepository.save(jobDetailDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mappedFileJobInvoiceDTO;
    }

    private List<JobDetailDTO> getAllJobRecord() throws EdiException {
        List<JobDetailDTO> jobDetailDTOS = null;
        try {
            jobDetailDTOS = jobDetailRepository.findByIsSubmitted(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobDetailDTOS;
    }

    public List<JobInvoiceMappedDTO> getAllJobInvoiceMappedDetails() throws EdiException {
        List<JobInvoiceMappedDTO> jobInvoiceMappedDTOS = new ArrayList<>();
        try {
            List<InvoiceDetailDTO> invoiceDetailDTOList = getAllInvoiceRecord();
            List<JobDetailDTO> jobDetailDTOList = getAllJobRecord();

            for (InvoiceDetailDTO invoiceDetailDTO : invoiceDetailDTOList) {
                JobInvoiceMappedDTO jobInvoiceMappedDTO = new JobInvoiceMappedDTO();

                for (JobDetailDTO jobDetailDTO : jobDetailDTOList) {
                    if (jobDetailDTO.getJobNo().contains(invoiceDetailDTO.getRefNo())) {
                        jobInvoiceMappedDTO.setJobId(jobDetailDTO.getId());
                        jobInvoiceMappedDTO.setJobNo(jobDetailDTO.getJobNo());
                        jobInvoiceMappedDTO.setImporter(jobDetailDTO.getImporter());
                        jobInvoiceMappedDTO.setBeNo(jobDetailDTO.getBeNo());
                        jobInvoiceMappedDTO.setBeDate(jobDetailDTO.getBeDate());
                        jobInvoiceMappedDTO.setAwbOrblNo(jobDetailDTO.getAwbOrblNo());
                        jobInvoiceMappedDTO.setAwbOrblDate(jobDetailDTO.getAwbOrblDate());
                        jobInvoiceMappedDTO.setHawbOrhblNo(jobDetailDTO.getHawbOrhblNo());
                        jobInvoiceMappedDTO.setHawbOrhblDate(jobDetailDTO.getHawbOrhblDate());
                        break;
                    }
                }
                jobInvoiceMappedDTO.setInvoiceId(invoiceDetailDTO.getId());
                jobInvoiceMappedDTO.setBillNo(invoiceDetailDTO.getBillNo());
                jobInvoiceMappedDTO.setDate(invoiceDetailDTO.getDate());
                jobInvoiceMappedDTO.setOrganization(invoiceDetailDTO.getOrganization());
                jobInvoiceMappedDTO.setBranch(invoiceDetailDTO.getBranch());
                jobInvoiceMappedDTO.setRefNo(invoiceDetailDTO.getRefNo());
                jobInvoiceMappedDTO.setAmount(invoiceDetailDTO.getAmount());

                jobInvoiceMappedDTOS.add(jobInvoiceMappedDTO);
            }
        } catch (EdiException e) {
            e.printStackTrace();
        }
        return jobInvoiceMappedDTOS;
    }

}
