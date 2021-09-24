package com.divary.simpleredis.service;

import com.divary.simpleredis.dto.request.UserRequest;
import com.divary.simpleredis.exception.NotFoundException;
import com.divary.simpleredis.model.User;
import com.divary.simpleredis.service.redis.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SimpleService {

    private static final Logger logger = LogManager.getLogger();

    private final RedisService redisService;
    private final ObjectMapper objectMapper;

    private static final String PREFIX = "User";

    @Autowired
    public SimpleService(RedisService redisService, ObjectMapper objectMapper) {
        this.redisService = redisService;
        this.objectMapper = objectMapper;
    }

    public User save(UserRequest userRequest) {

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(userRequest.getName());
        user.setAge(userRequest.getAge());

        try {
            redisService.set(PREFIX, user.getId(), objectMapper.writeValueAsString(user), null);
        } catch (JsonProcessingException e) {
            logger.error("Error parse user to redis = {}", user);
            throw new com.divary.simpleredis.exception.JsonProcessingException();
        }

        return user;
    }

    public void addExp(String id){

        if (!redisService.addExp(PREFIX, id, 100)){
            logger.info("user \"{} \" not found in redis", id);
        }

    }

    public User findById(String id){

        User user ;
        String result = redisService.get(PREFIX, id);

        try {
            if (StringUtils.isBlank(result)) throw new NotFoundException("User not found");

            user = objectMapper.readValue(result, User.class);
        } catch (JsonProcessingException e) {
            logger.error("Error parse user from redis = {}", result);
            throw new com.divary.simpleredis.exception.JsonProcessingException();
        }

        return user;

    }

    public List<User> findAll(){

        List<User> result = new ArrayList<>();
        List<String> userString = redisService.findAll(PREFIX);

        userString.forEach(s -> {
            try {
                result.add(objectMapper.readValue(s, User.class));
            } catch (JsonProcessingException e) {
                logger.error("Error parse user from redis = {}", result);
                throw new com.divary.simpleredis.exception.JsonProcessingException();
            }
        });

        return result;

    }

    public void delete(String id){
        redisService.delete(PREFIX, id);
    }

    public void delete(){
        redisService.delete(PREFIX);
    }
}
