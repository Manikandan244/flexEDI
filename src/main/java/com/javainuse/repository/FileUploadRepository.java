package com.javainuse.repository;

import com.javainuse.model.FileUploadDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileUploadRepository extends JpaRepository<FileUploadDTO, Integer> {

    FileUploadDTO findById(int id);

    List<FileUploadDTO> findByIsActiveAndIsUploaded(int isActive, int isUploaded);
}
