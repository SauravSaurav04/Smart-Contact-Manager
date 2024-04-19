package com.example.smartcontactmanager.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.smartcontactmanager.Dao.UserRepository;
import com.example.smartcontactmanager.Entities.User;
import com.example.smartcontactmanager.Helper.Message;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;
    // @GetMapping("/test")
    // @ResponseBody
    // public String test(){
    //     User user = new User();
    //     user.setName("Saurav");
    //     user.setEmail("saurav@gmail.com");
    //     userRepository.save(user);
    //     return "Working fine";
    // }

    @RequestMapping("/")
    public String home(Model model){
        model.addAttribute("title", "Home - SCM");
        return "home";
    }
    @RequestMapping("/about")
    public String about(Model model){
        model.addAttribute("title", "About - SCM");
        return "about";
    }
    @RequestMapping("/signup")
    public String signup(Model model){
        model.addAttribute("title", "Register - SCM");
        model.addAttribute("user", new User());
        return "signup";
    }
    @RequestMapping(value = "/do_register", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") User user, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model, HttpSession session){
        
        try {
            if(!agreement){
                throw new Exception("You have not agreed the terms and conditions");
            }
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImageUrl("default.png");
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            this.userRepository.save(user);
    
            model.addAttribute("title", "Success - SCM");
            model.addAttribute("user", user);

            session.setAttribute("message", new Message("Successfully Registered !!", "alert-success"));

            return "success";
        } catch (Exception e) {
            model.addAttribute("title", "Error - SCM");
            model.addAttribute("user", user);
            session.setAttribute("message", new Message(e.getMessage(), "alert-danger"));
            return "error";
        }
    }

    @GetMapping("/signin")
    public String customLogin(Model model){
        model.addAttribute("title", "Login - SCM");
        return "login";
    }

}
