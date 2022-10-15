package com.appsdeveloperblog.photoapp.api.users.ui.controllers;

import com.appsdeveloperblog.photoapp.api.users.service.UsersService;
import com.appsdeveloperblog.photoapp.api.users.shared.UserDto;
import com.appsdeveloperblog.photoapp.api.users.ui.model.CreateUserRequestModel;
import com.appsdeveloperblog.photoapp.api.users.ui.model.CreateUserResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private Environment env;

    @Autowired
    private UsersService usersService;

    @GetMapping("/status/check")
    public String status(){
        return "Working on port "+ env.getProperty("local.server.port") + ", with token = " + env.getProperty("token.secret");
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequestModel userDetail){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = modelMapper.map(userDetail, UserDto.class);
        UserDto createUser = usersService.createUser(userDto);
        CreateUserResponse returnValue = modelMapper.map(createUser, CreateUserResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue); //201
    }
}
