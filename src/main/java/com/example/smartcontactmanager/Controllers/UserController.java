package com.example.smartcontactmanager.Controllers;

import java.security.Principal;
import java.util.List;

import com.example.smartcontactmanager.Dao.ContactRepository;
import com.example.smartcontactmanager.Entities.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.smartcontactmanager.Dao.UserRepository;
import com.example.smartcontactmanager.Entities.User;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void addCommonData(Model model, Principal principal){
        String userEmail = principal.getName();

        User user = userRepository.getUserByUserEmail(userEmail);

        model.addAttribute("user", user);
    }
    @RequestMapping("/index")
    public String dashboard(Model model, Principal principal){
        model.addAttribute("title", "User Dashboard");
        return "Normal/user_dashboard";
    }
    @GetMapping("/add-contact")
    public String openAddContactForm(Model model){
        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contact());
        return "Normal/add_contact_form";
    }


    @GetMapping("/show-contacts/{page}")
    public String showContacts(@PathVariable("page") Integer page, Model model, Principal principal){
        // we have to send contact list
        String userEmail = principal.getName();
        User user = this.userRepository.getUserByUserEmail(userEmail);
//        List<Contact> contacts = user.getContacts();

        Pageable pageable = PageRequest.of(page, 3);

        Page<Contact> contacts = this.contactRepository.findContactByUser(user.getId(), pageable);

        model.addAttribute("title", "Show Contacts");
        model.addAttribute("contacts", contacts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", contacts.getTotalPages());

        return "Normal/show_contact";
    }
}
