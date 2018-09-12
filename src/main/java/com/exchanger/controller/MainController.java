package com.exchanger.controller;

import com.exchanger.model.User;
import com.exchanger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(path = "/home")
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/add")
    public @ResponseBody String addUsers(@RequestParam String login, @RequestParam String password, @RequestParam String email, Model model){
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        userRepository.save(user);
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("user",users);
        return "add";
    }
    @GetMapping("/all")
    public @ResponseBody Iterable<User> getAllUser(){
    return userRepository.findAll();
    }
    @PostMapping(path = "/checklogin")
    public @ResponseBody String checker(@RequestParam String login, @RequestParam String password){
        Iterable<User> iterable = userRepository.findAll();
        for (User user : iterable) {
             if(login!=null&&password!=null){
                if(user.getLogin().equals(login)&&user.getPassword().equals(password)){
                    return "Valid";
                }
             }
        }
        return "Not Valid";
    }
}