package com.LocalBusan.LocalBusan.signup;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/users")
    public String users(){
        return "users";
    }
}
