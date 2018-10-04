package com.exchanger.repository;

import com.exchanger.model.Message;
import com.exchanger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
