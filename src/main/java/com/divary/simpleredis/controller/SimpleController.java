package com.divary.simpleredis.controller;

import com.divary.simpleredis.dto.request.UserRequest;
import com.divary.simpleredis.dto.response.Response;
import com.divary.simpleredis.model.User;
import com.divary.simpleredis.service.SimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class SimpleController {

    private final SimpleService simpleService;

    @Autowired
    public SimpleController(SimpleService simpleService) {
        this.simpleService = simpleService;
    }

    @PostMapping()
    public ResponseEntity<Response<User>> save(@RequestBody UserRequest userRequest) {

        User result = simpleService.save(userRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>("User Created", result, HttpStatus.CREATED));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<User>> findById(@PathVariable String id){

        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("User found", simpleService.findById(id), HttpStatus.OK));
    }

    @GetMapping()
    public ResponseEntity<Response<List<User>>> findAll(){

        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("List of User", simpleService.findAll(), HttpStatus.OK));
    }

    @PutMapping("/exp/{id}")
    public ResponseEntity<Response<User>> addExp(@PathVariable String id){

        simpleService.addExp(id);

        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("Add expiration success", null, HttpStatus.OK));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Object>> delete(@PathVariable String id){

        simpleService.delete(id);

        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("Delete success", null, HttpStatus.OK));
    }

    @DeleteMapping()
    public ResponseEntity<Response<Object>> delete(){

        simpleService.delete();

        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("Delete success", null, HttpStatus.OK));
    }

}
