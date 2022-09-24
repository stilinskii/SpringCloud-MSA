package com.appsdeveloperblog.photoapp.api.users.ui.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String userId;
}
