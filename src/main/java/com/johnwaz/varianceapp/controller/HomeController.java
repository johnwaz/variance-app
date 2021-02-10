package com.johnwaz.varianceapp.controller;

import com.johnwaz.varianceapp.data.UserRepository;
import com.johnwaz.varianceapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    private static final String userSessionKey = "user";

    @RequestMapping("")
    public String welcomeUserFromSession(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return null;
        }
        model.addAttribute("user", user.get());
        return "index";
    }
}
