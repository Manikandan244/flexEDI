package com.javainuse.service;

import com.javainuse.controllers.EdiController;
import com.javainuse.controllers.FileStorageProperties;
import com.javainuse.exception.EdiException;
import com.javainuse.exception.FileStorageException;
import com.javainuse.exception.MyFileNotFoundException;
import com.javainuse.model.*;
import com.javainuse.org.response.ApiResponse;
import com.javainuse.repository.GenerateEdiRepository;
import com.javainuse.repository.InvoiceDetailRepository;
import com.javainuse.repository.JobDetailRepository;
import com.javainuse.repository.MiscellaneousRepository;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class EdiProcessService {

    @Autowired
    private GenerateEdiRepository generateEdiRepository;

    @Autowired
    private JobDetailRepository jobDetailRepository;

    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    @Autowired
    private FileStorageProperties fileStorageProperties;

    @Autowired
    MiscellaneousRepository miscellaneousRepository;

    @Autowired
    private FileUploadService fileUploadService;

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(EdiController.class);

    public static final String SUCCESS = "success";

    private final Path fileStorageLocation;

    @Autowired
    public EdiProcessService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getDirectory())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource != null) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    @Transactional
    public ApiResponse addJobDetails(JobDetailDTO jobDetailDTO) throws EdiException {
        LOGGER.info("Entering [ addJobDetails ].");
        String saveMessage = "";
        try {
            saveMessage = "Record Saved Sucessfully";
            return new ApiResponse(HttpStatus.OK, saveMessage, jobDetailDTO);
        } catch (Exception e) {
            LOGGER.error(e);
            throw new EdiException(e.getMessage());
        }
    }

    /*
    @Transactional(rollbackFor = Exception.class)
    public MappedAllDTO jobInvoiceSubmit(MappedAllDTO mappedJobInvoiceDTO) throws EdiException {
        LOGGER.info(" EdiProcessService service start....");
        MappedAllDTO mappedJobInvoiceEdiReturnObj = null;
        try {
            mappedJobInvoiceEdiReturnObj = new MappedAllDTO();
            List<JobDetailDTO> jobDetailDTOList = mappedJobInvoiceDTO.getJobDetailDTOS();
            int jobId = jobDetailDTOList.get(0).getId();

            for (JobDetailDTO jobDetailDTO : jobDetailDTOList) {
                jobDetailDTO.setIsSubmitted(1);
                jobDetailRepository.save(jobDetailDTO);
            }
            mappedJobInvoiceEdiReturnObj.setJobDetailDTOS(jobDetailRepository.findByIsSubmitted(0));

            List<InvoiceDetailDTO> invoiceDetailDTOList = mappedJobInvoiceDTO.getInvoiceDetailDTOS();

            for (InvoiceDetailDTO invoiceDetailDTO : invoiceDetailDTOList) {
                GenerateEdiDTO generateEdiDTO = new GenerateEdiDTO();
                generateEdiDTO.setJobId(jobId);
                generateEdiDTO.setInvoiceId(invoiceDetailDTO.getId());
                generateEdiDTO.setIsActive(1);
                generateEdiDTO.setIsEdiGenerated(0);
                generateEdiDTO.setIsFtpUpload(0);
                generateEdiDTO.setCreatedId(invoiceDetailDTO.getCreatedId());

                generateEdiRepository.save(generateEdiDTO);

                invoiceDetailDTO.setIsSubmitted(1);
                invoiceDetailRepository.save(invoiceDetailDTO);
            }
            mappedJobInvoiceEdiReturnObj.setInvoiceDetailDTOS(invoiceDetailRepository.findByIsSubmitted(0));
            mappedJobInvoiceEdiReturnObj.setGenerateEdiDTOS(getAllEDIRecord());

            LOGGER.info(" EdiProcessService service ended...");

        } catch (Exception e) {
            throw new EdiException(e.getMessage());
        }
        return mappedJobInvoiceEdiReturnObj;
    }
    */

    @Transactional(rollbackFor = Exception.class)
    public MappedAllDTO jobInvoiceSubmit(MappedAllDTO mappedJobInvoiceDTO) throws EdiException {
        LOGGER.info(" EdiProcessService service start....");
        MappedAllDTO mappedJobInvoiceEdiReturnObj = null;
        try {
            mappedJobInvoiceEdiReturnObj = new MappedAllDTO();
            List<JobInvoiceMappedDTO> jobInvoiceMappedDTOS = mappedJobInvoiceDTO.getJobInvoiceMappedDTOS();

            for (JobInvoiceMappedDTO jobInvoiceMappedDTO : jobInvoiceMappedDTOS) {
                //Job status save part
                JobDetailDTO jobDetailDTO = new JobDetailDTO();
                jobDetailDTO.setId(jobInvoiceMappedDTO.getJobId());
                jobDetailDTO.setJobNo(jobInvoiceMappedDTO.getJobNo());
                jobDetailDTO.setImporter(jobInvoiceMappedDTO.getImporter());
                jobDetailDTO.setIsSubmitted(1);
                jobDetailRepository.save(jobDetailDTO);

                //Invoice status save part
                InvoiceDetailDTO invoiceDetailDTO = new InvoiceDetailDTO();
                invoiceDetailDTO.setId(jobInvoiceMappedDTO.getInvoiceId());
                invoiceDetailDTO.setBillNo(jobInvoiceMappedDTO.getBillNo());
                invoiceDetailDTO.setDate(jobInvoiceMappedDTO.getDate());
                invoiceDetailDTO.setIsSubmitted(1);
                invoiceDetailRepository.save(invoiceDetailDTO);

                //Edi status save part
                GenerateEdiDTO generateEdiDTO = new GenerateEdiDTO();
                generateEdiDTO.setJobId(jobInvoiceMappedDTO.getJobId());
                generateEdiDTO.setInvoiceId(jobInvoiceMappedDTO.getInvoiceId());
                generateEdiDTO.setIsActive(1);
                generateEdiDTO.setIsEdiGenerated(0);
                generateEdiDTO.setIsFtpUpload(0);
                generateEdiDTO.setCreatedId(jobInvoiceMappedDTO.getCreatedId());
                generateEdiRepository.save(generateEdiDTO);
            }
            mappedJobInvoiceEdiReturnObj.setJobInvoiceMappedDTOS(fileUploadService.getAllJobInvoiceMappedDetails());
            mappedJobInvoiceEdiReturnObj.setGenerateEdiDTOS(getAllEDIRecord());

            LOGGER.info(" EdiProcessService service ended...");
        } catch (Exception e) {
            throw new EdiException(e.getMessage());
        }
        return mappedJobInvoiceEdiReturnObj;
    }

    public List<GenerateEdiDTO> getAllEDIRecord() throws EdiException {
        try {
            List<GenerateEdiDTO> generateEdiDTOList = generateEdiRepository.findByIsActiveAndIsEdiGenerated(1, 0);

            for (GenerateEdiDTO generateEdiDTO : generateEdiDTOList) {
                JobDetailDTO jobDetailDTO = jobDetailRepository.findById(generateEdiDTO.getJobId());
                if (jobDetailDTO.getJobNo() != null) {
                    generateEdiDTO.setJobNo(jobDetailDTO.getJobNo());
                    generateEdiDTO.setImporter(jobDetailDTO.getImporter());
                    generateEdiDTO.setBeNo(jobDetailDTO.getBeNo());
                }
                InvoiceDetailDTO invoiceDetailDTO = invoiceDetailRepository.findById(generateEdiDTO.getInvoiceId());
                if (invoiceDetailDTO.getBillNo() != null) {
                    generateEdiDTO.setBillNo(invoiceDetailDTO.getBillNo());
                    generateEdiDTO.setDate(invoiceDetailDTO.getDate());
                }
            }
            return generateEdiDTOList;
        } catch (Exception e) {
            throw new EdiException(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiResponse generateEDI(GenerateEdiDTO generateEdiDTO) throws EdiException {
        try {
            MappedEdiFtpDTO mappedEdiFtpDTO = new MappedEdiFtpDTO();

            String localDirectory = fileStorageProperties.getSpring_edi_temp_dir();
            File file = new File(localDirectory);
            if (!file.exists()) {
                file.mkdir();
            }
//            String fileName = fileStorageProperties.getSpring_edi_ftp_filename();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd.HHmmss");
            Date curDateTime = new Date();
//            String ftpFileName = fileName + dateFormat.format(curDateTime).toString() + ".edi";

            fileStorageProperties.setSpring_edi_ftp_filename(generateEdiDTO.getBillNo());
            String fileName = fileStorageProperties.getSpring_edi_ftp_filename();
            fileName = fileName.replaceAll("[^a-zA-Z0-9]", "");
            String ftpFileName = fileName + ".edi";

            String filePath = localDirectory + ftpFileName;
            generateSerialNo(generateEdiDTO);

            createEDI(generateEdiDTO, filePath, curDateTime);

            saveEdi(generateEdiDTO, filePath, ftpFileName);

            mappedEdiFtpDTO.setGenerateEdiDTOList(getAllEDIRecord());
            mappedEdiFtpDTO.setGenerateFtpDTOList(getAllFTPRecord());

            return new ApiResponse(HttpStatus.OK, SUCCESS, mappedEdiFtpDTO);
        } catch (Exception e) {
            throw new EdiException(e.getMessage());
        }
    }

    private void createEDI(GenerateEdiDTO generateEdiDTO, String filePath, Date curDateTime) throws EdiException {
        try {
            JobDetailDTO jobDetailDTO = jobDetailRepository.findById(generateEdiDTO.getJobId());
            InvoiceDetailDTO invoiceDetailDTO = invoiceDetailRepository.findById(generateEdiDTO.getInvoiceId());
            MiscellaneousDTO miscellaneousDTO = miscellaneousRepository.findById(1);

            FileWriter writer = new FileWriter(filePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            SimpleDateFormat ediFileGenerationYYMMDDFormat = new SimpleDateFormat("yyMMdd");
            String sysGenDateYY = ediFileGenerationYYMMDDFormat.format(curDateTime).toString();

            SimpleDateFormat ediFileGenerationYYYYMMDDFormat = new SimpleDateFormat("yyyyMMdd");
            String sysGenDateYYYY = ediFileGenerationYYYYMMDDFormat.format(curDateTime).toString();

            SimpleDateFormat ediFileGenerationHHMMFormat = new SimpleDateFormat("HHmm");
            String sysGenTime = ediFileGenerationHHMMFormat.format(curDateTime).toString();

            String invoiceDate = getInvoiceYYYYMMDDDate(invoiceDetailDTO.getDate());

            bufferedWriter.write("ISA*00*          *00*          *" + miscellaneousDTO.getTestQualifier() + "*" + miscellaneousDTO.getTestISAID() + "         *ZZ*" + miscellaneousDTO.getAs2ISATestId() + "  *" + sysGenDateYY + "*" + sysGenTime + "*U*00401*" + generateEdiDTO.getSequenceNo() + "*1*T*");
            bufferedWriter.newLine();
            bufferedWriter.write("GS*IA*" + miscellaneousDTO.getTestISAID() + "         *" + miscellaneousDTO.getGsIdTestOrProd() + "  *" + sysGenDateYYYY + "*" + sysGenTime + "*" + generateEdiDTO.getSequenceNo() + "*X*004010");
            bufferedWriter.newLine();
            bufferedWriter.write("ST*210*" + generateEdiDTO.getSequenceNo());
            bufferedWriter.newLine();
            bufferedWriter.write("B3*B*" + invoiceDetailDTO.getBillNo() + "*" + jobDetailDTO.getAwbOrblNo() + "*CC*K*" + invoiceDate + "*" + invoiceDetailDTO.getAmount() + "****SCAC*CIF");
            bufferedWriter.newLine();
            bufferedWriter.write("C3*INR");
            bufferedWriter.newLine();
            bufferedWriter.write("N9*MB*" + jobDetailDTO.getAwbOrblNo());
            bufferedWriter.newLine();
            bufferedWriter.write("N9*BM*" + jobDetailDTO.getHawbOrhblNo());
            bufferedWriter.newLine();
            bufferedWriter.write("G62*86*" + sysGenDateYYYY);
            bufferedWriter.newLine();
            bufferedWriter.write("R3*SFIT*B**M******ST*FT");
            bufferedWriter.newLine();
            bufferedWriter.write("K1*ADD BILL");
            bufferedWriter.newLine();
            bufferedWriter.write("N1*SH*Flextronics Manufacturing MEX, SA d");
            bufferedWriter.newLine();
            bufferedWriter.write("N3*Carretera Base Aerea 5850 Intl 4*Col. La Mora Zapopan");
            bufferedWriter.newLine();
            bufferedWriter.write("N4*Zapopan*JA*45136*MX");
            bufferedWriter.newLine();
            bufferedWriter.write("N1*CN*Flextronics International USA Inc");
            bufferedWriter.newLine();
            bufferedWriter.write("N3*1077 Gibraltar Dr., Building 8");
            bufferedWriter.newLine();
            bufferedWriter.write("N4*Milpitas*CA*95035*US");
            bufferedWriter.newLine();
            bufferedWriter.write("N1*BT*FLEXTRONICS LOGISTICS USA*25*G0461860");
            bufferedWriter.newLine();
            bufferedWriter.write("N3*847 Gibraltar Drive");
            bufferedWriter.newLine();
            bufferedWriter.write("N4*Milpitas*CA*93035*US");
            bufferedWriter.newLine();
            bufferedWriter.write("LX*1");
            bufferedWriter.newLine();
            bufferedWriter.write("L0*1*11544.0*KG*11544.0*G***26*PCS**K");
            bufferedWriter.newLine();
            bufferedWriter.write("L1*1***435400****400****FREIGHT");
            bufferedWriter.newLine();
            bufferedWriter.write("L1*2***15000****CSE****BORDER CROSSING");
            bufferedWriter.newLine();
            bufferedWriter.write("L1*3***96800****FUE****FSC - FUEL SURCHARGE");
            bufferedWriter.newLine();
            bufferedWriter.write("L3*11544.0*G***547200******26*K");
            bufferedWriter.newLine();
            bufferedWriter.write("SE*32*934821619");
            bufferedWriter.newLine();
            bufferedWriter.write("GE*1*934821619");
            bufferedWriter.newLine();
            bufferedWriter.write("IEA*1*934821619");

            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getInvoiceYYYYMMDDDate(String invoiceActualDate) throws ParseException {
        SimpleDateFormat actualInvoiceFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date convertedInvoiceISTDate = actualInvoiceFormat.parse(invoiceActualDate);

        SimpleDateFormat invoiceYYYYMMDDFormat = new SimpleDateFormat("yyyyMMdd");
        String invoiceDate = invoiceYYYYMMDDFormat.format(convertedInvoiceISTDate).toString();
        return invoiceDate;
    }

    private void saveEdi(GenerateEdiDTO generateEdiDTO, String filePath, String ftpFileName) throws EdiException {
        try {
            generateEdiDTO.setIsEdiGenerated(1);
            generateEdiDTO.setGeneratedDate(new Date());
            generateEdiDTO.setEdiFilePath(filePath);
            generateEdiDTO.setEdiFileName(ftpFileName);
            generateEdiDTO.setModifiedDate(new Date());

            generateEdiRepository.save(generateEdiDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GenerateEdiDTO generateSerialNo(GenerateEdiDTO generateEdiDTO) throws EdiException {
        String sequenceNo = "";
        try {
            sequenceNo = fileStorageProperties.getSequenceno();
            GenerateEdiDTO ediDTO = generateEdiRepository.findTop1ByIsActiveAndIsEdiGeneratedOrderBySequenceIdDesc(1, 1);
            int sequenceId;

            if (ediDTO == null) {
                sequenceId = 1;
            } else {
                if (ediDTO.getSequenceId() == 0) {
                    sequenceId = 1;
                } else {
                    sequenceId = ediDTO.getSequenceId() + 1;
                }
            }
            if (sequenceNo.length() == 0) {
                sequenceNo = "00000000";
            }
            sequenceNo = sequenceNo + sequenceId;
            sequenceNo = sequenceNo.substring(sequenceNo.length() - 9);

            generateEdiDTO.setSequenceId(sequenceId);
            generateEdiDTO.setSequenceNo(sequenceNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generateEdiDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiResponse ftpMove(GenerateEdiDTO generateEdiDTO) throws EdiException {
        try {
            generateEdiDTO.setIsFtpUpload(1);
            generateEdiDTO.setModifiedDate(new Date());
            GenerateEdiDTO generateEdiDTO1 = generateEdiRepository.save(generateEdiDTO);

            moveEdiFileToServer(generateEdiDTO1.getEdiFilePath(), generateEdiDTO1.getEdiFileName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ApiResponse(HttpStatus.OK, SUCCESS, getAllFTPRecord());
    }

    private String moveEdiFileToServer(String localFile, String fileName) throws IOException {
        String uploadMsg = "failure";
        FTPClient client = new FTPClient();

        String ftpIP = fileStorageProperties.getSpring_edi_ftp_ip();
        String ftpUserName = fileStorageProperties.getSpring_edi_ftp_id();
        String ftpPassword = fileStorageProperties.getSpring_edi_ftp_password();
        String ftpDataDir = fileStorageProperties.getSpring_edi_ftp_dir();

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

    public List<GenerateEdiDTO> getAllFTPRecord() throws EdiException {
        List<GenerateEdiDTO> generateFtpDTOS = null;
        try {
            generateFtpDTOS = generateEdiRepository.findByIsActiveAndIsEdiGeneratedAndIsFtpUpload(1, 1, 0);

            for (GenerateEdiDTO generateEdiDTO : generateFtpDTOS) {
                JobDetailDTO jobDetailDTO = jobDetailRepository.findById(generateEdiDTO.getJobId());
                generateEdiDTO.setJobNo(jobDetailDTO.getJobNo());

                InvoiceDetailDTO invoiceDetailDTO = invoiceDetailRepository.findById(generateEdiDTO.getInvoiceId());
                generateEdiDTO.setBillNo(invoiceDetailDTO.getBillNo());

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
                String stringDate = dateFormat.format(generateEdiDTO.getGeneratedDate());
                generateEdiDTO.setEdiGeneratedDateTime(stringDate);
            }
        } catch (Exception e) {
            throw new EdiException(e.getMessage());
        }
        return generateFtpDTOS;
    }

    public ResponseEntity<Object> ediDownload(String fileName) throws IOException {
//        String fileName = "D://tmp//edi//4SMAA082381920.edi";
        fileName = fileStorageProperties.getSpring_edi_temp_dir() + "//" + fileName;
        File file = new File(fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content Disposition", String.format("attachment;filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache,no-store,must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("text/csv; charset=utf-8"))
                .body(resource);

        return responseEntity;
    }

}
