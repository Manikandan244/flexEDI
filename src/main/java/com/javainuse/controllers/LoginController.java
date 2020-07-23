package com.javainuse.controllers;

import com.javainuse.exception.EdiException;
import com.javainuse.org.response.ApiResponse;
import com.javainuse.repository.UserRepository;
import com.javainuse.service.EdiProcessService;
import com.javainuse.service.LoginService;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.javainuse.model.LoginDTO;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin( origins = "http://localhost:4300")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/flex-api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/userDetails")
    public String adminLogin(@RequestParam("loginName") String userName, @RequestParam("loginPassword") String password) throws EdiException {
        String result;
        try {
            if (userName != null && userName.equals("admin")) {
                if (password != null && password.equals("admin")) {
                    result = "Login Success..!!";
                } else {
                    result = "Invalid Password..!!";
                }
            } else {
                result = "Invalid User Name..!!";
            }
        } catch (Exception e) {
            throw new EdiException(e.getMessage());
        }
        return result;
    }

    @GetMapping(value = "/getAll")
    public ApiResponse getAllUserDetails() throws EdiException {
        return new ApiResponse(HttpStatus.OK, "success", loginService.findAll());
    }

    @PostMapping(value = "/validateLogin", consumes = "application/json")
//    public ApiResponse validateLogin(@RequestParam("loginName") String userName, @RequestParam("loginPassword") String password) throws EdiException {
    public ApiResponse validateLogin(@Valid @RequestBody LoginDTO loginDTO) throws EdiException {
        return loginService.validateLoginDetails(loginDTO);
    }

    @PostMapping(value = "/save", consumes = "application/json")
//    public ApiResponse saveLogin(@RequestParam("loginName") String userName, @RequestParam("loginPassword") String password) throws EdiException {
    public ApiResponse saveLogin(@Valid @RequestBody LoginDTO loginDTO) throws EdiException {
        return loginService.save(loginDTO);
    }
}
