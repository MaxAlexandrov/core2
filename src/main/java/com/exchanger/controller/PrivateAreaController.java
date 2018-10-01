package com.exchanger.controller;

import com.exchanger.model.Message;
import com.exchanger.model.User;
import com.exchanger.repository.MessageRepository;
import com.exchanger.repository.MessageStatusRepository;
import com.exchanger.repository.MessageTypeRepository;
import com.exchanger.repository.UserRepository;
import com.exchanger.service.UserService;
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

import static java.lang.System.gc;

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
    private UserService userService;

    @GetMapping
    public String getUserRoomPage(User user){
        this.user = userRepository.findByLogin(user.getLogin());
        return "user-room";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Model model) {
        List<Map> listMessages = new ArrayList<>();
        Map<String,Object> mapMessage;
            List<Message> dbListMessage = messageRepository.findAll();
       for (Message item : dbListMessage) {
                mapMessage= new HashMap<String,Object>(7);
                mapMessage.put("id", item.getId());
                mapMessage.put("text", item.getText_message());
                mapMessage.put("emailFrom", item.getUser_from().getEmail());
                mapMessage.put("emailTo", item.getUser_to().getEmail());
                mapMessage.put("dateSend", item.getDateSend());
                mapMessage.put("dateGet", item.getDateGet());
                mapMessage.put("type", messageTypeRepository.findById(item.getMessage_type()).getType());
                mapMessage.put("status", messageStatusRepository.findById(item.getStatus()).getStatus());
                listMessages.add(mapMessage);
                gc();
            }
        model.addAttribute("all_message", listMessages);
        return "user-room";
    }

    @PostMapping("send")
    public String send(@AuthenticationPrincipal User user, @RequestParam String email, @RequestParam String textMessage, @RequestParam String typeM , Model model) {
       if(!email.isEmpty()&&!textMessage.isEmpty()&&!typeM.isEmpty()) {
           Message message = new Message();
           message.setText_message(textMessage);
           message.setMessage_type(messageTypeRepository.findByType(typeM).getId());
           message.setUser_to(userRepository.findByEmail(email));
           message.setUser_from(user);
           message.setDateSend(new Date());
           message.setStatus(messageStatusRepository.findByStatus("SEND").getId());
           messageRepository.save(message);
       }
        model.addAttribute("success", "Done");
        return "user-room";
    }
}
