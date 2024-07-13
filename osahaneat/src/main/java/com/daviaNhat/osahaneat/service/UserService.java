package com.daviaNhat.osahaneat.service;

import com.daviaNhat.osahaneat.dto.UserDTO;
import com.daviaNhat.osahaneat.entity.Users;
import com.daviaNhat.osahaneat.repository.UserRepository;
import com.daviaNhat.osahaneat.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserServiceImp {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUser() {
        List<Users> listUser = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();

        for (Users users : listUser) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(users.getId());
            userDTO.setUserName(users.getUserName());
            userDTO.setFullname(users.getFullname());
            userDTO.setPassword(users.getPassword());

            userDTOList.add(userDTO);
        }
        return userDTOList;
    }
}
