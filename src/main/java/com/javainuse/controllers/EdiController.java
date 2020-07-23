package com.javainuse.controllers;

import com.javainuse.model.GenerateEdiDTO;
import com.javainuse.model.InvoiceDetailDTO;
import com.javainuse.org.response.ApiResponse;
import com.javainuse.service.EdiProcessService;
import com.javainuse.exception.EdiException;
import com.javainuse.model.MappedAllDTO;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4300")
@RestController
@RequestMapping("/flex-api/ediProcess")
public class EdiController {

    @Autowired
    EdiProcessService ediProcessService;

//    private final Path rootLocation = Paths.get("F:/Java/flex/");

    @PostMapping("/savefile")
    public MappedAllDTO readDataFromExcelFile(@RequestParam("file") MultipartFile file) throws EdiException {
        String message;
        MappedAllDTO mappedAllDTO = new MappedAllDTO();
        try {
            try {
                List<InvoiceDetailDTO> invoiceDetailDTOList = new ArrayList<>();
                Workbook workbook = getWorkBook(file);
                Sheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rows = sheet.iterator();
                rows.next();
                while (rows.hasNext()) {
                    Row row = rows.next();
                    InvoiceDetailDTO invoiceDetailDTO = new InvoiceDetailDTO();
                    if (row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        String phNo = NumberToTextConverter.toText(row.getCell(0).getNumericCellValue());
                        invoiceDetailDTO.setBillNo(phNo);
                    }
                    if (row.getCell(1).getCellType() == Cell.CELL_TYPE_STRING) {
                        invoiceDetailDTO.setDate(row.getCell(1).getStringCellValue());
                    }
//                    Cell cell =row.getCell(2);
//                    invoiceDetailDTO.setAmount(cell.getNumericCellValue());

                    invoiceDetailDTOList.add(invoiceDetailDTO);
                }
//                mappedAllDTO.setInvoiceDetailDTOS(invoiceDetailDTOList);

//                Files.copy(file.getInputStream(), this.rootLocation.resolve("file_name.xlsx"));
//                InputStream inputStream = file.getInputStream();
//                ObjectMapper mapper = new ObjectMapper();
//
//                String jsonPayload = IOUtils.toString(file.getInputStream(), "UTF-8");
//                System.out.println("stream::::: "+file.getInputStream().toString());
//                System.out.println("jsonPayload:::: "+jsonPayload);

            } catch (Exception e) {
                throw new RuntimeException("FAIL!");
            }
            message = "Successfully uploaded!";
            System.out.println("Successfully uploaded!");
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

    @PostMapping(value = "/ediGenerate", consumes = "application/json")
    public ApiResponse generateEDI(@Valid @RequestBody GenerateEdiDTO generateEdiDTO) throws EdiException {
        return ediProcessService.generateEDI(generateEdiDTO);
    }

    @PostMapping(value = "/ftpUpload", consumes = "application/json")
    public ApiResponse ftpUpload(@Valid @RequestBody GenerateEdiDTO generateEdiDTO) throws EdiException {
        return ediProcessService.ftpMove(generateEdiDTO);
    }

//    @GetMapping(path = "/finalData")
//    public MappedJobInvoiceDTO finalDataProcess(@RequestParam("dataFile") FileInputStream fileInputStream) throws EdiException {
//        return ediProcessService.getInputStreamObject(fileInputStream);
//    }

    @PostMapping(value = "/jobInvoiceSubmit", consumes = "application/json")
    public ApiResponse jobInvoiceSubmit(@Valid @RequestBody MappedAllDTO mappedJobInvoiceDTO) throws Exception {
        return new ApiResponse(HttpStatus.OK, "success", ediProcessService.jobInvoiceSubmit(mappedJobInvoiceDTO));
    }

    @GetMapping(path = "/ediDownload/{fileName}")
    public ResponseEntity<Object> download(@PathVariable String fileName) throws IOException {
        return ediProcessService.ediDownload(fileName);
    }

//    @GetMapping(path = "/ediDownload/{fileName:.+}")
//    public HashMap downloadFileBase64(@PathVariable String fileName, HttpServletRequest request) {
//        return ediProcessService.ediDownload(fileName, request);
//    }

//    @GetMapping(path = "/all")
//    public ApiResponse serNo(@RequestParam String fromDate, @RequestParam String toDate) throws Exception {
//        return new ApiResponse(HttpStatus.OK, "success", ediProcessService.getAllReport());
//    }
}
