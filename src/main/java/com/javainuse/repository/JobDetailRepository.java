package com.javainuse.repository;

import com.javainuse.model.JobDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobDetailRepository extends JpaRepository<JobDetailDTO, Integer> {

    JobDetailDTO findById(int id);

    List<JobDetailDTO> findByIsSubmitted(int isSubmitted);
}
