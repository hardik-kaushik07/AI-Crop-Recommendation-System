package com.hardik.farmapp.Controller;

import com.hardik.farmapp.Entity.Users;
import com.hardik.farmapp.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    public Users register(@RequestBody Users user){
        return usersService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user){
        return usersService.verify(user);
    }
}
