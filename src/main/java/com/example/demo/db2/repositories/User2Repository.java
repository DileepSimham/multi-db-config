package com.example.demo.db2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.db1.entities.User1;
import com.example.demo.db2.entities.User2;

public interface User2Repository extends JpaRepository<User2, Integer> {

}
