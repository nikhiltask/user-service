package com.userservice.Service;

import com.userservice.Model.User;
import com.userservice.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public List<User> allUser(){
        return userRepository.findAll();
    }

}
