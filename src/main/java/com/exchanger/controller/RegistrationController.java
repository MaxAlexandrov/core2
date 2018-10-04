package com.exchanger.controller;

import com.exchanger.model.User;
import com.exchanger.repository.RoleRepository;
import com.exchanger.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("registration")
public class RegistrationController {
    Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private RoleRepository roleReepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getRegistrationPage(){
        return "registration";
    }

    @PostMapping
    public String addUserInDB(User user, Model model){
        String showMessageUser = String.format("This LOGIN  %s \n" + "exist in System. Please change login", user.getLogin());
        if(!userService.addUserInDB(user)) {
            model.addAttribute("message", showMessageUser);
            return "registration";
        }return "redirect:/login";
    }
    @GetMapping("activate/{code}")
    public String getActivationCode(Model model, @PathVariable String code){
        boolean activated = userService.isActivated(code);
        if(activated){
            model.addAttribute("mesasage", "Activated success!");
        }
        else{model.addAttribute("mesasage", "Activated decline! Please registration again!");}
        return  "login";
    }

}
