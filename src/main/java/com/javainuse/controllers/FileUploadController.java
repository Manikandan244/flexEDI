package com.javainuse.controllers;

import com.javainuse.exception.EdiException;
import com.javainuse.model.MappedAllDTO;
import com.javainuse.org.response.ApiResponse;
import com.javainuse.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/flex-api/uploadProcess")
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;

    public static final String SUCCESS = "success";

    @PostMapping(value = "/fileUpload")
    public ApiResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("type") String getType, @RequestParam("userId") int userId) throws Throwable {
        return fileUploadService.save(file, getType, userId);
    }

    @GetMapping(value = "/all")
    public ApiResponse getAllFileList() throws EdiException {
        return new ApiResponse(HttpStatus.OK, SUCCESS, fileUploadService.fetchAllRecords());
    }

    @PostMapping(value = "/update/{id}")
    public ApiResponse removeFileUpload(@PathVariable int id) throws EdiException {
        return fileUploadService.update(id);
    }

    @GetMapping(value = "/getFile/{id}")
    public ApiResponse getFile(@PathVariable int id) throws Throwable {
        return new ApiResponse(HttpStatus.OK, SUCCESS, fileUploadService.find(id));
    }

    @PostMapping(value = "/submit")
//    public ApiResponse submit(@RequestBody Map<String, Object>[] stuffs) throws EdiException {
    public ApiResponse submit(@Valid @RequestBody MappedAllDTO mappedFileDTO) throws EdiException {
        return fileUploadService.submit(mappedFileDTO);
    }

}
