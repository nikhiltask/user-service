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

    public String userDeleteById(String Id){
        userRepository.deleteById(Id);
        return "User Deleted Successfully";
    }

}
