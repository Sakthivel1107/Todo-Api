package com.example.Todo_Project.Repository;


import com.example.Todo_Project.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public Optional<User> findByEmail(String email){
        Query query = new Query(Criteria.where("email").is(email));
        return Optional.ofNullable(mongoTemplate.findOne(query, User.class));
    }
    public User save(User user){
        return mongoTemplate.save(user);
    }
    public Optional<User> findById(String id) throws Exception{
        return Optional.ofNullable(mongoTemplate.findById(id, User.class));
    }
    public List<User> findAll(){
        return mongoTemplate.findAll(User.class);
    }
}

