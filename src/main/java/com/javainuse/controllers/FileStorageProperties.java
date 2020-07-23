package com.javainuse.controllers;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:apiConfiguration.properties")
@ConfigurationProperties
@Configuration
public class FileStorageProperties {

    private String directory;
    private String sequenceno;

    private String spring_edi_temp_dir;
    private String spring_edi_ftp_ip;
    private String spring_edi_ftp_id;
    private String spring_edi_ftp_password;
    private String spring_edi_ftp_filename;
    private String spring_edi_ftp_dir;

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getSequenceno() {
        return sequenceno;
    }

    public void setSequenceno(String sequenceno) {
        this.sequenceno = sequenceno;
    }

    public String getSpring_edi_temp_dir() {
        return spring_edi_temp_dir;
    }

    public void setSpring_edi_temp_dir(String spring_edi_temp_dir) {
        this.spring_edi_temp_dir = spring_edi_temp_dir;
    }

    public String getSpring_edi_ftp_ip() {
        return spring_edi_ftp_ip;
    }

    public void setSpring_edi_ftp_ip(String spring_edi_ftp_ip) {
        this.spring_edi_ftp_ip = spring_edi_ftp_ip;
    }

    public String getSpring_edi_ftp_id() {
        return spring_edi_ftp_id;
    }

    public void setSpring_edi_ftp_id(String spring_edi_ftp_id) {
        this.spring_edi_ftp_id = spring_edi_ftp_id;
    }

    public String getSpring_edi_ftp_password() {
        return spring_edi_ftp_password;
    }

    public void setSpring_edi_ftp_password(String spring_edi_ftp_password) {
        this.spring_edi_ftp_password = spring_edi_ftp_password;
    }

    public String getSpring_edi_ftp_filename() {
        return spring_edi_ftp_filename;
    }

    public void setSpring_edi_ftp_filename(String spring_edi_ftp_filename) {
        this.spring_edi_ftp_filename = spring_edi_ftp_filename;
    }

    public String getSpring_edi_ftp_dir() {
        return spring_edi_ftp_dir;
    }

    public void setSpring_edi_ftp_dir(String spring_edi_ftp_dir) {
        this.spring_edi_ftp_dir = spring_edi_ftp_dir;
    }
}
