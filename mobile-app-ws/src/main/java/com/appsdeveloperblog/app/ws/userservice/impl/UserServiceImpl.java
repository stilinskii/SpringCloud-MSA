package com.appsdeveloperblog.app.ws.userservice.impl;

import com.appsdeveloperblog.app.ws.shared.Utils;
import com.appsdeveloperblog.app.ws.userservice.UserService;
import com.appsdeveloperblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    Map<String, UserRest> users;

    Utils utils;

    public UserServiceImpl(){}

    @Autowired // controller쪽에서 service생성할때 utils도 autowired되게끔
    public UserServiceImpl(Utils utils){
        this.utils = utils;
    }

    @Override
    public UserRest createUser(UserDetailsRequestModel userDetails) {
        UserRest returnValue = new UserRest();
        returnValue.setEmail(userDetails.getEmail());
        returnValue.setFirstName(userDetails.getFirstName());
        returnValue.setLastName(userDetails.getLastName());

        String userId = utils.generatedUserId();
        returnValue.setUserId(userId);

        if(users == null) users = new HashMap<>();
        users.put(userId, returnValue);
        return returnValue;
    }
}
