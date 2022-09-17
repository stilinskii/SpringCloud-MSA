package com.appsdeveloperblog.app.ws.ui.controller;

import com.appsdeveloperblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users") //http:/localhost:8090/users/
public class UserController {

    @GetMapping
    public String getUsers(@RequestParam(required = false, defaultValue = "1") int page,
                           @RequestParam(defaultValue = "2") int limit,
                           @RequestParam(required = false, defaultValue = "desc") String sort){
//        if(sort == null){
//            sort = "desc";
//        }
        return "get users was called with page = " +page+ " and limit = " + limit + " and sort = "+ sort;
    }



    @GetMapping(value = "/{userId}",
            produces = {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<UserRest> getUser(@PathVariable String userId){

        UserRest returnValue = new UserRest();
        returnValue.setEmail("test@test.com");
        returnValue.setFirstName("Jenn");
        returnValue.setLastName("Im");
//        returnValue.setUserId();
        return new ResponseEntity<>(returnValue, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(consumes = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<UserRest> createUser(@RequestBody UserDetailsRequestModel userDetails){
        UserRest returnValue = new UserRest();
        returnValue.setEmail(userDetails.getEmail());
        returnValue.setFirstName(userDetails.getFirstName());
        returnValue.setLastName(userDetails.getLastName());
        return new ResponseEntity<>(returnValue, HttpStatus.OK);
    }

    @PutMapping
    public String updateUser(){
        return "update user was called";
    }

    @DeleteMapping
    public String deleteUser(){
        return "delete user was called";
    }
}
