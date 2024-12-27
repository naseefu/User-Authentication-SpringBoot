package com.hospitly.utils;

import com.hospitly.dto.UserDTO;
import com.hospitly.entity.User;

public class Utils {

    public static UserDTO mapUserEntityToUserDTO(User user) {

        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setAddress(user.getAddress());
        userDTO.setCity(user.getCity());
        userDTO.setState(user.getState());
        userDTO.setCountry(user.getCountry());
        userDTO.setProfilePicture(user.getProfilePicture());
        userDTO.setBirthday(user.getBirthday());
        return userDTO;

    }

}
