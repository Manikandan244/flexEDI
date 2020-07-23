package com.javainuse.controllers;

import com.javainuse.model.InvoiceDetailDTO;
import com.javainuse.model.JobDetailDTO;
import com.javainuse.exception.EdiException;
import com.javainuse.model.MappedAllDTO;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/uploadMultipleFiles")
public class TestMultipleFileUpload {

    //    List<String> files = new ArrayList<String>();
    private final Path rootLocation = Paths.get("F:\\Java\\flex\\");

    private SimpleDateFormat dateFormat;
    private Date curDate;
//    private AppConfig appConfig;
//
//    public FileController() throws EdiException {
//        appConfig = new AppConfig();
//        dateFormat = new SimpleDateFormat("yyyyMMdd.HHmmss");//.HHmmss
//        curDate = new Date();
//    }

    @PostMapping("/sampleFile")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile[] file) throws IOException {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < file.length; i++) {

            Workbook workbook = getWorkBook(file[i]);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            rows.next();
            while (rows.hasNext()) {
                Row row = rows.next();
                System.out.println(row.getCell(0));
                System.out.println(row.getCell(1));
            }


            HashMap image = new HashMap();
            String[] fileList = file[i].getOriginalFilename().split("\\.");
            Date date = new Date();
            String modifiedFile = fileList[0] + "-" + date.getTime();
            File convertFile = new File(rootLocation + modifiedFile + "." + fileList[1]);
            convertFile.createNewFile();
            FileOutputStream fileOut = new FileOutputStream(convertFile);
            fileOut.write(file[i].getBytes());
            fileOut.close();
            image.put("fileName", file[i].getOriginalFilename());
            image.put("filePath", modifiedFile + "." + fileList[1]);
            image.put("isActive", 1);
//            image.put("atchSeqId", 0);
            list.add(image);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    MappedAllDTO mappedAllDTO = null;

    @PostMapping("/savefile")
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile[] files) throws EdiException {
        mappedAllDTO = new MappedAllDTO();
        Arrays.asList(files)
                .stream()
                .map(file1 -> {
                    try {
                        System.out.println("file1 anme: " + file1.getOriginalFilename());
                        readDataFromExcelFile(file1);
                    } catch (EdiException e) {
                        e.printStackTrace();
                    }
                    return mappedAllDTO;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(mappedAllDTO, HttpStatus.OK);
//        generateEDI(mappedJobInvoiceDTO);
    }

    public MappedAllDTO readDataFromExcelFile(@RequestParam("file") MultipartFile file) throws EdiException {
        String message;
        try {
            try {
                if (file.getOriginalFilename().contains("Invoice")) {
                    List<InvoiceDetailDTO> invoiceDetailDTOList = new ArrayList<InvoiceDetailDTO>();
                    insertInvoiceDtl(file, invoiceDetailDTOList);
                } else if (file.getOriginalFilename().contains("Job")) {
                    List<JobDetailDTO> jobDetailDTOList = new ArrayList<JobDetailDTO>();
                    insertJobDtl(file, jobDetailDTOList);
                }
            } catch (Exception e) {
                throw new RuntimeException("FAIL!");
            }
//            files.add(file.getOriginalFilename());

            message = "Successfully uploaded!";
            System.out.println(file.getOriginalFilename() + " Successfully uploaded!");
//            generateEDI(mappedJobInvoiceDTO);
//            return ResponseEntity.status(HttpStatus.OK).body(message);

        } catch (Exception e) {
            message = "Failed to upload!";
            e.printStackTrace();
            System.out.println("error msg:::" + e.getMessage());
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
        return mappedAllDTO;
    }

    private MappedAllDTO insertJobDtl(MultipartFile file, List<JobDetailDTO> jobDetailDTOList) {
        try {
            Workbook workbook = getWorkBook(file);
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

                    jobDetailDTOList.add(jobDetailDTO);
                }
            }
//            mappedAllDTO.setJobDetailDTOS(jobDetailDTOList);
//            for (JobDetailDTO iterJob : mappedAllDTO.getJobDetailDTOS()) {
//                System.out.println(iterJob.getJobNo());
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mappedAllDTO;
    }

    private MappedAllDTO insertInvoiceDtl(MultipartFile file, List<InvoiceDetailDTO> invoiceDetailDTOList) {
        try {
            Workbook workbook = getWorkBook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            rows.next();
            while (rows.hasNext()) {
                Row row = rows.next();
                if (!(row.getCell(0) + "").toString().equalsIgnoreCase("null") && !(row.getCell(0) + "").toString().equals("")) {
                    InvoiceDetailDTO invoiceDetailDTO = new InvoiceDetailDTO();

                    System.out.println((row.getCell(0) + "").toString());
                    System.out.println((row.getCell(0) + "").toString().length());
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


//                    if(row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC){
//                        String phNo = NumberToTextConverter.toText(row.getCell(0).getNumericCellValue());
//                        invoiceDetailDTO.setBillNo(phNo);
//                    }
//                    if(row.getCell(1).getCellType() == Cell.CELL_TYPE_STRING){
//                        invoiceDetailDTO.setDate(row.getCell(1).getStringCellValue());
//                    }
//                    Cell cell =row.getCell(2);
//                    invoiceDetailDTO.setAmount(cell.toString());

                    invoiceDetailDTOList.add(invoiceDetailDTO);
                }
            }
//            mappedAllDTO.setInvoiceDetailDTOS(invoiceDetailDTOList);
//            for (InvoiceDetailDTO iterInv : mappedAllDTO.getInvoiceDetailDTOS()) {
//                System.out.println(iterInv.getBillNo());
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mappedAllDTO;
    }

    private Workbook getWorkBook(MultipartFile file) {
        Workbook workbook = null;
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        try {
            if (extension.equalsIgnoreCase("xlsx")) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else if (extension.equalsIgnoreCase("xls")) {
                workbook = new HSSFWorkbook(file.getInputStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }

    private void generateEDI(MappedAllDTO mappedAllDTO) throws EdiException {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            throw new EdiException(e.getMessage());
        }
        try {

//            String localDirectory = appConfig.getLocalWrkDir();
//            String fileName = appConfig.getFileName();
            String localDirectory = properties.getProperty("spring.edi.temp-dir");
            String fileName = properties.getProperty("spring.edi.ftp-filename");
            dateFormat = new SimpleDateFormat("yyyyMMdd.HHmmss");//.HHmmss
            curDate = new Date();

            File file = new File(localDirectory);
            if (!file.exists()) {
                file.mkdir();
            }
            String ftpFileName = fileName + dateFormat.format(curDate).toString() + ".edi";
            String filePath = localDirectory + ftpFileName;

            FileWriter writer = new FileWriter(filePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write("SAMPLE EDI GENERATED FILE");
            bufferedWriter.newLine();
//            for (InvoiceDetailDTO invoiceDetailDTO : mappedAllDTO.getInvoiceDetailDTOS()) {
//                bufferedWriter.write("ISA*00*          *00*          *ZZ*FLEX(SenderID)           *16*941714834TEST  *" + invoiceDetailDTO.getDate() + "*2102*U*00401*934821619*1*T*");
//                bufferedWriter.newLine();
//                bufferedWriter.write("GS*IM*FLEX(SenderID)*9417148340335*" + invoiceDetailDTO.getDate() + "*2102*934821619*X*004010");
//                bufferedWriter.newLine();
//                bufferedWriter.write("ST*210*934821619");
//                bufferedWriter.newLine();
//                bufferedWriter.write("B3*B*" + invoiceDetailDTO.getBillNo() + "*8050266539*CC*K*" + invoiceDetailDTO.getDate() + "*547200****SCAC");
//                bufferedWriter.newLine();
//                bufferedWriter.write("C3*USD");
//                bufferedWriter.newLine();
//                bufferedWriter.write("N9*MB*" + invoiceDetailDTO.getRefNo());
//                bufferedWriter.newLine();
//                bufferedWriter.write("N9*BM*" + invoiceDetailDTO.getBillNo());
//                bufferedWriter.newLine();
//                bufferedWriter.write("N9*4B*GDL");
//                bufferedWriter.newLine();
//                bufferedWriter.write("N9*4C*SFO");
//                bufferedWriter.newLine();
//                bufferedWriter.write("N9*PO*335436193");
//                bufferedWriter.newLine();
//                bufferedWriter.write("N9*PO*335440741");
//                bufferedWriter.newLine();
//                bufferedWriter.write("N9*PO*335440751");
//                bufferedWriter.newLine();
//                bufferedWriter.write("N9*CR*1744LA_PLATES");
//                bufferedWriter.newLine();
//                bufferedWriter.write("N9*CR*OKLAHOMA");
//                bufferedWriter.newLine();
//                bufferedWriter.write("N9*HY*94-3061570MM");
//                bufferedWriter.newLine();
//                bufferedWriter.write("G62*86*20150827");
//                bufferedWriter.newLine();
//                bufferedWriter.write("R3*SFIT*B**M******ST*FT");
//                bufferedWriter.newLine();
//                bufferedWriter.write("K1*ADD BILL");
//                bufferedWriter.newLine();
//                bufferedWriter.write("N1*SH*Flextronics Manufacturing MEX, SA d");
//                bufferedWriter.newLine();
//                bufferedWriter.write("N3*Carretera Base Aerea 5850 Intl 4*Col. La Mora Zapopan");
//                bufferedWriter.newLine();
//                bufferedWriter.write("N4*Zapopan*JA*45136*MX");
//                bufferedWriter.newLine();
//                bufferedWriter.write("N1*CN*Flextronics International USA Inc");
//                bufferedWriter.newLine();
//                bufferedWriter.write("N3*1077 Gibraltar Dr., Building 8");
//                bufferedWriter.newLine();
//                bufferedWriter.write("N4*Milpitas*CA*95035*US");
//                bufferedWriter.newLine();
//                bufferedWriter.write("N1*BT*FLEXTRONICS LOGISTICS USA*25*G0461860");
//                bufferedWriter.newLine();
//                bufferedWriter.write("N3*847 Gibraltar Drive");
//                bufferedWriter.newLine();
//                bufferedWriter.write("N4*Milpitas*CA*93035*US");
//                bufferedWriter.newLine();
//                bufferedWriter.write("LX*1");
//                bufferedWriter.newLine();
//                bufferedWriter.write("L0*1*11544.0*KG*11544.0*G***26*PCS**K");
//                bufferedWriter.newLine();
//                bufferedWriter.write("L1*1***435400****400****FREIGHT");
//                bufferedWriter.newLine();
//                bufferedWriter.write("L1*2***15000****CSE****BORDER CROSSING");
//                bufferedWriter.newLine();
//                bufferedWriter.write("L1*3***96800****FUE****FSC - FUEL SURCHARGE");
//                bufferedWriter.newLine();
//                bufferedWriter.write("L3*11544.0*G***547200******26*K");
//                bufferedWriter.newLine();
//                bufferedWriter.write("SE*32*934821619");
//                bufferedWriter.newLine();
//                bufferedWriter.write("GE*1*934821619");
//                bufferedWriter.newLine();
//                bufferedWriter.write("IEA*1*934821619");
//            }
            bufferedWriter.close();

//            this.moveFileToServer(filePath, ftpFileName, properties);
        } catch (Exception e) {
            throw new EdiException(e.getMessage());
        }

    }

    private String moveFileToServer(String localFile, String fileName, Properties properties) throws IOException {
        String uploadMsg = "failure";
        FTPClient client = new FTPClient();
//        String ftpIP = appConfig.getFtpIP();
//        String ftpUserName = appConfig.getFtpId();
//        String ftpPassword = appConfig.getFtpPassword();
//        String ftpDataDir = appConfig.getFtpOutboundDir();

        String ftpIP = properties.getProperty("spring.edi.ftp-ip");
        String ftpUserName = properties.getProperty("spring.edi.ftp-id");
        String ftpPassword = properties.getProperty("spring.edi.ftp-password");
        String ftpDataDir = properties.getProperty("spring.edi.ftp-dir");

        InputStream inputStream = null;
        try {
            client.connect(ftpIP);
            client.login(ftpUserName, ftpPassword);
            client.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);


            File inputFile = new File(localFile);
            inputStream = new FileInputStream(inputFile);

            int reply = client.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                client.disconnect();
                System.exit(1);
            }
            client.enterLocalPassiveMode();
            client.changeWorkingDirectory(ftpDataDir);
            client.storeFile(ftpDataDir + "/" + fileName, inputStream);
            uploadMsg = "success";
        } catch (IOException e) {
//	        logger.debug("*** The Following Exception at ftpSendFile Method :  "+e);
            e.printStackTrace();
        } finally {
            inputStream.close();
            client.logout();
            client.disconnect();
        }
        return uploadMsg;
    }


}
