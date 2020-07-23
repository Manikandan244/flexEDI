package com.javainuse.repository;

import com.javainuse.model.GenerateEdiDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenerateEdiRepository extends JpaRepository<GenerateEdiDTO, Integer> {

    GenerateEdiDTO findById(int id);

    List<GenerateEdiDTO> findByIsActiveAndIsEdiGenerated(int isActive, int isEdiGenerated);

    List<GenerateEdiDTO> findByIsActiveAndIsEdiGeneratedAndIsFtpUpload(int isActive, int isEdiGenerated, int isFtpUpload);

    GenerateEdiDTO findTop1ByIsActiveAndIsEdiGeneratedOrderBySequenceIdDesc(int isActive, int isEdiGenerated);

    List<Object[]> fetchJobReportByFromDateToDate();
}
