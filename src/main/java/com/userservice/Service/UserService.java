package com.userservice.Service;

import com.userservice.Exception.UserNotFoundException;
import com.userservice.Model.User;
import com.userservice.Model.UserDTO;
import com.userservice.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

public UserDTO findID(String userId){
    try {
    User user=userRepository.findById(userId).get();
    UserDTO userDto=new UserDTO();userDto.setUserID(user.getUserID());userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());userDto.setMiddleName(user.getMiddleName());userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setEmail(user.getEmail());userDto.setAddress(user.getAddress());userDto.setDateOfBirth(user.getDateOfBirth());
        userDto.setEmployeeNumber(user.getEmployeeNumber());userDto.setBloodGroup(user.getBloodGroup());userDto.setGender(user.getGender());
    return  userDto;
    }
    catch (Exception e){
        throw new UserNotFoundException("User Not Found Exception");
    }
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
            return userRepository.save(user);
        }
        else{
            throw new Exception("ID doesnot Exist");
        }
    }

    public List<UserDTO> allUser(Integer page,Integer pageSize) {
        if(page==null){
            page=1;
        }
        if(pageSize==null){
            pageSize=10;
        }
        Pageable firstPage = PageRequest.of(page-1, pageSize);
        List<User> allUsers=  userRepository.findAll(firstPage).toList();
        List<UserDTO> allUsersDTO=new ArrayList<>();
        for(User user:allUsers){
            UserDTO userDTO=new UserDTO(user.getUserID(),user.getFirstName(),user.getMiddleName(),
                    user.getLastName(),user.getPhoneNumber(),user.getDateOfBirth(),user.getGender(),
                    user.getAddress(),user.getEmployeeNumber(),user.getBloodGroup(),user.getEmail());
            allUsersDTO.add(userDTO);
        }
        return  allUsersDTO;

    }

}
