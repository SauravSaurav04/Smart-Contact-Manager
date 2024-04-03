package com.example.smartcontactmanager.Controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.smartcontactmanager.Dao.UserRepository;
import com.example.smartcontactmanager.Entities.User;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @RequestMapping("/index")
    public String dashboard(Model model, Principal principal){

        String userName = principal.getName();
        System.out.println(userName);

        User user = userRepository.getUserByUserName(userName);
        System.out.println(user);

        model.addAttribute("user", user);

        return "Normal/user_dashboard";
    }
}
