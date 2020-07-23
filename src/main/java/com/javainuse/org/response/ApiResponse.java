package com.javainuse.org.response;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ApiResponse implements Serializable {

    private String timestamp;
    private int status;
    private String message;
    private Object result;

    public ApiResponse() {
    }

    public ApiResponse(HttpStatus status, String message, Object result) {
        this.timestamp = getLocalDateTime();
        this.status = status.value();
        this.message = message;
        this.result = result;
    }

    public ApiResponse(HttpStatus httpStatus, String record_Saved_Sucessfully) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ApiResponse [statusCode=" + status + ", message=" + message + "]";
    }

    private String getLocalDateTime() {
        LocalDateTime datetime1 = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatDateTime = datetime1.format(format);
        System.out.println(formatDateTime);
        return formatDateTime;
    }
}
