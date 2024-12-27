package com.hospitly.service;

import com.hospitly.dto.Response;
import com.hospitly.dto.UserDTO;
import com.hospitly.entity.Role;
import com.hospitly.entity.User;
import com.hospitly.repo.UserRepository;
import com.hospitly.utils.JWTUtils;
import com.hospitly.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean isValidEnumValue(Role enumValue) {
        try{
            Role.valueOf(enumValue.name());
            return true;
        }
        catch (IllegalArgumentException e){
            return false;
        }
    }

    public Response register(User user,String confirmPassword) {

        Response response = new Response();

        try{

            if(userRepository.existsByEmail(user.getEmail())){

                response.setMessage("Email already exists");
                response.setStatusCode(404);
                return response;

            }
            else{

            if(user.getPassword().equals(confirmPassword)){

                if(isValidEnumValue(user.getRole())){

                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    user.setEmail(user.getEmail().toLowerCase());

                    userRepository.save(user);
                    response.setMessage("Registration Successful");
                    response.setStatusCode(200);
                    return response;

                }
                else{
                    response.setMessage("Specify UserRole");
                    response.setStatusCode(400);
                    return response;
                }

            }
            else{

                response.setStatusCode(400);
                response.setMessage("Check Both Passwords");
                return response;

            }
            }

        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }

    }

    public Response login(String email,String password){

        Response response = new Response();

        try{

            if(email.isBlank() || password.isBlank()){
                response.setStatusCode(400);
                response.setMessage("Please enter your email and password");
                return response;
            }
            else{

                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email.toLowerCase(),password));

                User u = userRepository.findByEmail(email.toLowerCase()).orElseThrow(()->new UsernameNotFoundException("User Not Found"));

                if(u!=null){

                    UserDTO userDTO = Utils.mapUserEntityToUserDTO(u);
                    var token = jwtUtils.generatedToken(u);

                    response.setToken(token);
                    response.setStatusCode(200);
                    response.setMessage("Login Successful");
                    response.setUser(userDTO);


                }
                else{
                    response.setStatusCode(400);
                    response.setMessage("User Not Found");
                }

                return response;

            }

        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }

    }

}
