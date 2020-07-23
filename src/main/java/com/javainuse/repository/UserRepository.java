package com.javainuse.repository;

import com.javainuse.model.LoginDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<LoginDTO, Integer> {

    LoginDTO findByLoginNameAndLoginPassword(String userName, String password);
}
