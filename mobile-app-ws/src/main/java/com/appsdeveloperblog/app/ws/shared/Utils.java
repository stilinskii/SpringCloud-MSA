package com.appsdeveloperblog.app.ws.shared;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
//@Component
public class Utils {
    public String generatedUserId(){
        return UUID.randomUUID().toString();
    }
}
