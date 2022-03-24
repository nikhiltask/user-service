package com.userservice.Repository;

import com.userservice.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

@org.springframework.stereotype.Repository
public interface UserRepository extends MongoRepository<User,String > {
    public User findEmail(String email);
}
