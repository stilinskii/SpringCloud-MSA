package com.appsdeveloperblog.app.ws.ui.controller;

import com.appsdeveloperblog.app.ws.userservice.UserService;
import com.appsdeveloperblog.app.ws.exception.UserServiceException;
import com.appsdeveloperblog.app.ws.ui.model.request.UpdateUserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/users") //http:/localhost:8090/users/
public class UserController {

    @Autowired
    UserService userService;


    Map<String, UserRest> users;

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

        if(true) throw new UserServiceException("A user service exception is thrown.");

        if(users.containsKey(userId)){
            return new ResponseEntity<>(users.get(userId), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(consumes = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails){
        UserRest returnValue = userService.createUser(userDetails);

        return new ResponseEntity<>(returnValue, HttpStatus.OK);
    }

    @PutMapping(path = "/{userId}",consumes = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE },
            produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE })
    public UserRest updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserDetailsRequestModel userDetails) {

        UserRest storedUserDetails = null;
        if (users.containsKey(userId)) {
            storedUserDetails = users.get(userId);
            storedUserDetails.setFirstName(userDetails.getFirstName());
            storedUserDetails.setLastName(userDetails.getLastName());

        }

        return storedUserDetails;
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId){
        users.remove(userId);
        return ResponseEntity.noContent().build();
    }
}
