package com.daviaNhat.osahaneat.service.imp;

import com.daviaNhat.osahaneat.dto.UserDTO;
import com.daviaNhat.osahaneat.payload.request.SignUpRequest;

import java.util.List;

public interface LoginServiceImp {
    List<UserDTO> getAllUser();
    boolean checkLogin(String username, String password);
    boolean addUser(SignUpRequest signUpRequest);
}
