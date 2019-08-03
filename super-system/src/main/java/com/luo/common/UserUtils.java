package com.luo.common;

import com.luo.entity.User;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {
    private static User user;
    public static User getCurrentUser() {
//        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    };

    public void setUser(User user) {
        this.user = user;
    }
}
