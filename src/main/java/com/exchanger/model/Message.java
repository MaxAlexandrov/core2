package com.exchanger.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Please, fill the message")
    @Length(max =2048 , message = "message to long, only 2048 char do you have..")
    private String text_message;
    //@ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "id", insertable = false, updatable=false)
    private Integer user_to;
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "id", insertable = false, updatable=false)
    private Integer user_from;
    private Integer message_type;
    private Date dateSend;
    private Date dateGet;
    private Integer status;

    public Message() {
    }

    public Message(String text_message, Integer user_to, Integer user_from, Integer message_type, Date dateSend, Date dateGet, Integer status) {
        this.text_message = text_message;
        this.user_to = user_to;
        this.user_from = user_from;
        this.message_type = message_type;
        this.dateSend = dateSend;
        this.dateGet = dateGet;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText_message() {
        return text_message;
    }

    public void setText_message(String text_message) {
        this.text_message = text_message;
    }

    public Integer getUser_to() {
        return user_to;
    }

    public void setUser_to(Integer user_to) {
        this.user_to = user_to;
    }

    public Integer getUser_from() {
        return user_from;
    }

    public void setUser_from(Integer user_from) {
        this.user_from = user_from;
    }

    public Integer getMessage_type() {
        return message_type;
    }

    public void setMessage_type(Integer message_type) {
        this.message_type = message_type;
    }

    public Date getDateSend() {
        return dateSend;
    }

    public void setDateSend(Date dateSend) {
        this.dateSend = dateSend;
    }

    public Date getDateGet() {
        return dateGet;
    }

    public void setDateGet(Date dateGet) {
        this.dateGet = dateGet;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text_message='" + text_message + '\'' +
                ", user_to=" + user_to +
                ", user_from=" + user_from +
                ", message_type=" + message_type +
                ", dateSend=" + dateSend +
                ", dateGet=" + dateGet +
                ", status=" + status +
                '}';
    }
}
