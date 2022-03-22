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

    public User findID(String id){
        return userRepository.findById(id).get();
    }
    public String userDeleteById(String Id){
        userRepository.deleteById(Id);
        return "User Deleted Successfully";
    }
    public  User addUser(User user){
        return userRepository.save(user);
    }
    public User update(User user,String userId) throws Exception {
        if(userRepository.findById(userId).isPresent()){
            return this.userRepository.save(user);
        }
        else{
            throw new Exception("ID doesnot Exist");
        }
    }

}
