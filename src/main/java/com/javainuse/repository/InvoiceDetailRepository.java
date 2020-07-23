package com.javainuse.repository;

import com.javainuse.model.InvoiceDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetailDTO, Integer> {

    InvoiceDetailDTO findById(int id);

    List<InvoiceDetailDTO> findByIsSubmitted(int isSubmitted);
}
