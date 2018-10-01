package com.exchanger.controller;

import com.exchanger.model.RoleEnum;
import com.exchanger.model.User;
import com.exchanger.repository.RoleRepository;
import com.exchanger.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping("registration")
public class RegistrationController {
    Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleReepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String getRegistrationPage(){
        return "registration";
    }

    @PostMapping
    public String addUserInDB(User user, Model model){
        List<User> userFromDB = userRepository.findAll();
        if(userFromDB!=null) {
            for (User userUnit : userFromDB) {
                if(userUnit.getLogin().equals(user.getLogin())) {
                    model.addAttribute("message", "This LOGIN exist in System. Please change login");
                    return "registration";
                }else if(userUnit.getEmail().equals(user.getEmail())){
                    model.addAttribute("message", "This EMAIL exist in System. Please change email");
                    return "registration";
                }
            }
        }
        user.setRole(Collections.singleton(RoleEnum.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
        return "redirect:/login";
    }
}
