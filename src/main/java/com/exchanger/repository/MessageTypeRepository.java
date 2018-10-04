package com.exchanger.repository;

import com.exchanger.model.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageTypeRepository extends JpaRepository<MessageType,Long> {
    MessageType findByType(String type);
    MessageType findById(Integer type);
}
