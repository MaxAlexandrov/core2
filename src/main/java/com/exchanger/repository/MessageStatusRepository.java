package com.exchanger.repository;

import com.exchanger.model.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageStatusRepository extends JpaRepository<MessageStatus,Long> {
    MessageStatus findByStatus(String status);
    MessageStatus findById(Integer id);
}
