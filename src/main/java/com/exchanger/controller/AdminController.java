package com.exchanger.controller;

import com.exchanger.model.Message;
import com.exchanger.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {


    @GetMapping
    public String getPage(Model model){
        return "admin-room";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Model model) {
        List<Message> messages = null;
//        if (filter != null && !filter.isEmpty()) {
//            messages = messageRepository.findAll();
//        }
        model.addAttribute("all_message", messages);
        return "admin-room";
    }
}
