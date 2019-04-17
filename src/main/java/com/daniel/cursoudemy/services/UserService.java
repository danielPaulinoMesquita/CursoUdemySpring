package com.daniel.cursoudemy.services;

import com.daniel.cursoudemy.security.UserSS;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {
    public static UserSS authenticated(){
        try {
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch(Exception ex){
            return null;
        }
    }
}
