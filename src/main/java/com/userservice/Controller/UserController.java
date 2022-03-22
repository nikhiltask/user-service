package com.userservice.Controller;

import com.userservice.Model.User;
import com.userservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    public  UserService userService;

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody @Valid User user){
        return new ResponseEntity<>(userService.addUser(user),HttpStatus.ACCEPTED);
    }
    @PutMapping("/users/{userId}")
    public ResponseEntity<User> update(@Valid @RequestBody User user, @PathVariable("userId")  String userId) throws Exception {
        return new ResponseEntity<>(userService.update(user,userId),HttpStatus.ACCEPTED);
    }
}
