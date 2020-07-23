package com.javainuse.controllers;

import com.javainuse.exception.EdiException;
import com.javainuse.org.response.ApiResponse;
import com.javainuse.repository.GenerateEdiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4300")
@RestController
@RequestMapping("/flex-api/report")
public class ReportController {

    @Autowired
    GenerateEdiRepository generateEdiRepository;

    public static final String SUCCESS = "success";

    @GetMapping(value = "/all")
    public ApiResponse getAllReport(@RequestParam String fromDate, @RequestParam String toDate) throws EdiException {
        List<Object> list = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        List<Object[]> reportList = generateEdiRepository.fetchJobReportByFromDateToDate();
        for (Object[] item : reportList) {
            HashMap hm = new HashMap();
            hm.put("jobNo", item[0]);
            hm.put("billNo", item[1]);
            hm.put("billDate", item[2]);

            if (item[4].equals(1)) {
                item[4] = "Yes";
                hm.put("ediFileName", item[3]);
                hm.put("sequenceNo", item[6]);

                String genDate = dateFormat.format((Date) item[5]);
                hm.put("generatedDate", genDate);

                String modDate = dateFormat.format((Date) item[8]);
                hm.put("modifiedDate", modDate);
            } else {
                item[4] = "No";
                hm.put("ediFileName", "-");
                hm.put("sequenceNo", "-");
                hm.put("generatedDate", "-");
                hm.put("modifiedDate", "-");
            }
            hm.put("isEdiGenerated", item[4]);

            if (item[7].equals(1)) {
                item[7] = "Yes";
            } else {
                item[7] = "No";
            }
            hm.put("isFtpUpload", item[7]);

            list.add(hm);
        }
        return new ApiResponse(HttpStatus.OK, SUCCESS, list);
    }
}
