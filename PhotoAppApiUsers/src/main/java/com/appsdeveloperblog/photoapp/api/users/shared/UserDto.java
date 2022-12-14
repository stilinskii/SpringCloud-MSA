package com.appsdeveloperblog.photoapp.api.users.shared;

import com.appsdeveloperblog.photoapp.api.users.ui.model.AlbumResponseModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class UserDto implements Serializable {

    private static final long serialVersionUID = -953297098295050686L;
    private String firstName;
    private String lastName;
    private String email;
    private String userId;
    private String password;
    private String encryptedPassword;
    private List<AlbumResponseModel> albums;
}
