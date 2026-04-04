package com.example.Todo_Project.Repository;

import com.example.Todo_Project.Models.Todo;
import com.example.Todo_Project.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TodoRepository {
    @Autowired
    private MongoTemplate mongoTemplate;
    public List<Todo> findAllTodoByUserId(String userId){
        Query query = Query.query(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query,Todo.class);
    }
    public Todo save(Todo todo){
        return mongoTemplate.save(todo);
    }
    public void deleteById(String Id){
        Query query = Query.query(Criteria.where("_id").is(Id));
        mongoTemplate.remove(query,Todo.class);
    }
}
