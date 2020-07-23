package com.javainuse.repository;

import com.javainuse.model.MiscellaneousDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MiscellaneousRepository extends JpaRepository<MiscellaneousDTO, Integer> {

    MiscellaneousDTO findById(int id);
}
