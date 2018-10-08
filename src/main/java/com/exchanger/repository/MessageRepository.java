package com.exchanger.repository;

import com.exchanger.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByUserTo(Integer userTo);
      }
