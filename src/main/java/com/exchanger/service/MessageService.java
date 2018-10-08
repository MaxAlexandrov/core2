package com.exchanger.service;

import com.exchanger.model.HistoryChangeMessagesAndStatus;
import com.exchanger.model.Message;
import com.exchanger.model.User;
import com.exchanger.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Date;
import java.util.List;

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
    @Autowired
    HistoryMessageAndStatusRepository historyMessageAndStatusRepository;

    public boolean changeStatusSendToGet(User user) {
        List<Message> listmessages = messageRepository.findByUserTo(user.getId());
        for (Message listmessage : listmessages) {
            if(listmessage.getStatus().equals(messageStatusRepository.findByStatus("SEND").getId())){
                Date dateChange = new Date();
                listmessage.setStatus(messageStatusRepository.findByStatus("GET").getId());
                listmessage.setDateGet(dateChange);
                addHistoryStatusChanged(listmessage.getId(),listmessage.getStatus(),dateChange);
                sendNotification(userRepository.findById(listmessage.getUserFrom()),user,listmessage.getMessage_type(),listmessage.getStatus()," message WAS GETTING ");
                messageRepository.save(listmessage);
            }
        }
        return true;
    }

    public boolean addMessageInDB(User user, String email, String textMessage, String typeMessage) {
        Message message = new Message();
        User userTo = userRepository.findByEmail(email);
        if(userTo!=null) {
            Date dateChange = new Date();
            message.setText_message(textMessage);
            message.setMessage_type(messageTypeRepository.findByType(typeMessage).getId());
            message.setUserTo(userTo.getId());
            message.setUserFrom(user.getId());
            message.setDateSend(dateChange);
            message.setStatus(messageStatusRepository.findByStatus("SEND").getId());
            messageRepository.save(message);
            addHistoryStatusChanged(message.getId(),message.getStatus(),dateChange);
            if (!StringUtils.isEmpty(userTo.getEmail())&& typeMessage.equals("order")) {
                sendNotification(userTo,user,message.getMessage_type(),message.getStatus(),"You GET a new message in ExchangerMail");
            }
            return true;
        }else
            return false;
    }

    public boolean sendNotification(User userTo, User userFrom, Integer messageType, Integer messageStatus, String message){
        String messageForSender = String.format("Hello %s \n" + " %s\n  from user:  %s\n and from email: %s\n " +
                    "Please go to this link for look detail: http://localhost:8080/user", userTo.getLogin(),message, userFrom.getLogin(), userTo.getEmail());
            mailSender.send(userTo.getEmail(),message, messageForSender);
        return true;
    }

    public boolean addHistoryStatusChanged(Long message_id, Integer status_id, Date dateChange){
        historyMessageAndStatusRepository.save( new HistoryChangeMessagesAndStatus(message_id,status_id,dateChange));
        return true;
    }

}
