package com.exchanger.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "history_message_status")
public class HistoryChangeMessagesAndStatus {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer id;
        private Long message_id;
        private Integer status_id;
        private Date dateChange;

    public HistoryChangeMessagesAndStatus() {
    }

    public HistoryChangeMessagesAndStatus(Long message_id, Integer status_id) {
        this.message_id = message_id;
        this.status_id = status_id;
        this.dateChange = new Date();
    }

    public HistoryChangeMessagesAndStatus(Long message_id, Integer status_id, Date dateChange) {
        this.message_id = message_id;
        this.status_id = status_id;
        this.dateChange = dateChange;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getMessage_id() {
        return message_id;
    }

    public void setMessage_id(Long message_id) {
        this.message_id = message_id;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public Date getDateChange() {
        return dateChange;
    }

    public void setDateChange(Date dateChange) {
        this.dateChange = dateChange;
    }

    @Override
    public String toString() {
        return "HistoryChangeMessagesAndStatus{" +
                "id=" + id +
                ", message_id=" + message_id +
                ", status_id=" + status_id +
                ", dateChange=" + dateChange +
                '}';
    }
}
