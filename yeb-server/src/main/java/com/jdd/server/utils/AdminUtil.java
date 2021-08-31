package com.jdd.server.utils;

import com.jdd.server.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

public class AdminUtil {

    public static Admin getCurrentAdmin(){
        return ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

}
