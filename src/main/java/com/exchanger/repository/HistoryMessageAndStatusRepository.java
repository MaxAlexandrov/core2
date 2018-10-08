package com.exchanger.repository;

import com.exchanger.model.HistoryChangeMessagesAndStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

    public interface HistoryMessageAndStatusRepository extends JpaRepository<HistoryChangeMessagesAndStatus, Long> {
        List<HistoryChangeMessagesAndStatus> findByDateChange(Date dateChange);

    }
