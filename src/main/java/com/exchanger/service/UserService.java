package com.exchanger.service;

import com.exchanger.model.RoleEnum;
import com.exchanger.model.User;
import com.exchanger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login);
    }

    public boolean addUserInDB(User user) {
        User searchedUser = userRepository.findByLogin(user.getLogin());
        if (searchedUser == null) {
            user.setRole(Collections.singleton(RoleEnum.USER));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEnabled(true);
            user.setActivationCode(UUID.randomUUID().toString());
            userRepository.save(user);
            if(!StringUtils.isEmpty(user.getEmail())){
                String message = String.format("Hello %s \n" + "this you Activation code. \n"+
                        "Please go to this link for confirm you email address: http://localhost:8080/registration/activate/%s", user.getLogin(), user.getActivationCode());
                mailSender.send(user.getEmail(),"Activation code", message);
            }
        return true;
        } else return false;
    }

    public boolean isActivated(String code) {
        User user = userRepository.findByActivationCode(code);
        if(user==null){
            return false;
        }
        user.setActivationCode(null);
        userRepository.save(user);
        return true;
    }
}
