package com.javainuse.service;

import com.javainuse.controllers.LoginController;
import com.javainuse.exception.EdiException;
import com.javainuse.model.LoginDTO;
import com.javainuse.org.response.ApiResponse;
import com.javainuse.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(LoginController.class);

    public static final String SUCCESS = "success";

    public List<LoginDTO> findAll() throws EdiException {
        List<LoginDTO> loginDTOList = null;
        try {
            loginDTOList = userRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginDTOList;
    }

    public ApiResponse validateLoginDetails(LoginDTO loginDTO) throws EdiException {
        LOGGER.info(" Login Controller start....");
        LoginDTO existsLogin = null;
        String result;
        try {
            existsLogin = userRepository.findByLoginNameAndLoginPassword(loginDTO.getLoginName(), loginDTO.getLoginPassword());

        } catch (Exception e) {
            LOGGER.info(" Login Controller error....");
            throw new EdiException(e.getMessage());
        }
        LOGGER.info(" Login Controller end....");
        if (existsLogin != null) {
            result = existsLogin.getLoginName() + " logged in successfully..!!";
            LOGGER.info(loginDTO.getLoginName() + " logged in successfully....");
            return new ApiResponse(HttpStatus.OK, result, existsLogin);
        } else {
            result = "Invalid User Name and Password..!!";
            LOGGER.info("Invalid User Name and Password..!!");
            return new ApiResponse(HttpStatus.BAD_REQUEST, result, null);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiResponse save(LoginDTO loginDTO) throws EdiException {
        LOGGER.info(" Login Controller start....");
        try {
            loginDTO.setLoginName(loginDTO.getLoginName());
            loginDTO.setLoginPassword(loginDTO.getLoginPassword());

            LoginDTO existsLogin = userRepository.save(loginDTO);

            if (existsLogin != null) {
                LOGGER.info(loginDTO.getLoginName() + " saved successfully....");
                return new ApiResponse(HttpStatus.OK, SUCCESS, existsLogin);
            } else {
                LOGGER.info("loginnot saved...");
                return new ApiResponse(HttpStatus.BAD_REQUEST, SUCCESS, "");
            }
        } catch (Exception e) {
            LOGGER.info(" Login Controller error....");
            throw new EdiException(e.getMessage());
        }
    }
}
