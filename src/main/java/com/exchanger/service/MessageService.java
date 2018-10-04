package com.exchanger.service;

import com.exchanger.model.Message;
import com.exchanger.model.User;
import com.exchanger.repository.MessageRepository;
import com.exchanger.repository.MessageStatusRepository;
import com.exchanger.repository.MessageTypeRepository;
import com.exchanger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

//TODO Если этот класс не наполнен то этот сервис необходимо удалить за ненадобностью
//TODO был добавлен для переноса всех методов по обслуживанию БД из к
@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageTypeRepository messageTypeRepository;
    @Autowired
    private MessageStatusRepository messageStatusRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private MailSender mailSender;

    public boolean SendNotify(String userTo) {


        return true;
    }

    public boolean addMessageInDB(User user, String email, String textMessage, String typeMessage) {
        Message message = new Message();
        User userTo = userRepository.findByEmail(email);
        if(userTo!=null) {
            message.setText_message(textMessage);
            message.setMessage_type(messageTypeRepository.findByType(typeMessage).getId());
            message.setUser_to(userTo.getId());
            message.setUser_from(user.getId());
            message.setDateSend(new Date());
            message.setStatus(messageStatusRepository.findByStatus("SEND").getId());
            messageRepository.save(message);
            if (!StringUtils.isEmpty(userTo.getEmail())&& typeMessage.equals("order")) {
                String messageForSender = String.format("Hello %s \n" + " you get message from user: %s\n and from this %s\n email" +
                        "Please go to this link for look detail: http://localhost:8080/user", userTo.getLogin(), user.getLogin(), user.getEmail());
                mailSender.send(userTo.getEmail(), "You GET a new message ExchangerMail", messageForSender);
            }
            return true;
        }else
            return false;
    }
}
