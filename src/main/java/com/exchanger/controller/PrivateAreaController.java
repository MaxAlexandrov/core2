package com.exchanger.controller;

import com.exchanger.model.Message;
import com.exchanger.model.User;
import com.exchanger.repository.MessageRepository;
import com.exchanger.repository.MessageStatusRepository;
import com.exchanger.repository.MessageTypeRepository;
import com.exchanger.repository.UserRepository;
import com.exchanger.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('USER')")
public class PrivateAreaController {

    private User user;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageTypeRepository messageTypeRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageStatusRepository messageStatusRepository;

    @Autowired
    private MessageService messageService;

    @GetMapping
    public String getUserRoomPage(@AuthenticationPrincipal User user){
        messageService.changeStatusSendToGet(user);
        return "user-room";
    }

    @PostMapping("/filter")
    public String filter(@AuthenticationPrincipal User user,@RequestParam String filter, Model model) {
        List<Map<String,Object>> listMessages = new ArrayList<>();
        Map<String,Object> mapMessage;
        if(!filter.isEmpty()&& filter!=null){
            List<Message> dbListMessage = messageRepository.findAll();
            for (Message item : dbListMessage) {
                if(item.getUserFrom() == user.getId()){
                    mapMessage = new HashMap<String, Object>(7);
                    mapMessage.put("id", item.getId());
                    mapMessage.put("text",item.getText_message() );
                    mapMessage.put("emailFrom", item.getUserFrom());
                    mapMessage.put("emailTo", item.getUserTo());
                    mapMessage.put("dateSend", item.getDateSend());
                    mapMessage.put("dateGet", item.getDateGet());
                    mapMessage.put("type", messageTypeRepository.findById(item.getMessage_type()).getType());
                    mapMessage.put("status", messageStatusRepository.findById(item.getStatus()).getStatus());
                    listMessages.add(mapMessage);
                }
            }
        } else {
                List<Message> dbListMessage =messageRepository.findAll();
                for (Message item : dbListMessage) {
                    mapMessage= new HashMap<String,Object>(7);
                    mapMessage.put("id", item.getId());
                    mapMessage.put("text", item.getText_message());
                    mapMessage.put("emailFrom", item.getUserFrom());
                    mapMessage.put("emailTo", item.getUserTo());
                    mapMessage.put("dateSend", item.getDateSend());
                    mapMessage.put("dateGet", item.getDateGet());
                    mapMessage.put("type", messageTypeRepository.findById(item.getMessage_type()).getType());
                    mapMessage.put("status", messageStatusRepository.findById(item.getStatus()).getStatus());
                    listMessages.add(mapMessage);
                }
        }
        model.addAttribute("all_message", listMessages);
        return "user-room";
    }

    @PostMapping("/send")
    public String send(@AuthenticationPrincipal User user, @RequestParam String email, @RequestParam String textMessage, @RequestParam String typeMessage , Model model) {
        String showMessageUser;
        if(!email.isEmpty()&&!textMessage.isEmpty()&&!typeMessage.isEmpty()) {
            if (messageService.addMessageInDB(user, email, textMessage, typeMessage)) {
                showMessageUser = String.format("This MESSAGE send for email %s \n" + "sent.", email);
                model.addAttribute("success", showMessageUser);
            return "user-room";
            }
        }
        showMessageUser = String.format("Sorry, but you need enter message");
        model.addAttribute("success", showMessageUser);
        return "user-room";
    }

    @PostMapping("/searchEmail")
    public boolean serchEmail(@AuthenticationPrincipal User user,@RequestParam String email, Model model) {
        if (!email.isEmpty() && email != null) {
            User emailUser = userRepository.findByEmail(email);
            if (emailUser != null) {
                return true;
            } else return false;
        }
        return false;
    }

}
