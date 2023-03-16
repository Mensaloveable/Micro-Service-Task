package com.loveable.customerservice.utils;

import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserUtil {
    public static String getAuthenticatedUserEmail() {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loggedEmail = userDetails.getUsername();
        return userDetails.getUsername();
    }
}
