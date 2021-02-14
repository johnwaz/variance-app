package com.johnwaz.varianceapp.controller;

import com.johnwaz.varianceapp.data.NovelRepository;
import com.johnwaz.varianceapp.data.UserRepository;
import com.johnwaz.varianceapp.models.Book;
import com.johnwaz.varianceapp.models.Novel;
import com.johnwaz.varianceapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;

@Controller
@RequestMapping("novels")
public class NovelController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NovelRepository novelRepository;

    private static final String userSessionKey = "user";

    @GetMapping
    public String displayAllNovels(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        model.addAttribute("user", user);
        model.addAttribute("novels", novelRepository.findAllById(Collections.singleton(userId)));
        return "novels/index";
    }

    @GetMapping("add")
    public String displayAddNovelForm(Model model) {
        model.addAttribute(new Novel());
        model.addAttribute("novels", novelRepository.findAll());
        return "novels/add";
    }

    @PostMapping("add")
    public String processAddNovelForm(@Valid @ModelAttribute Novel newNovel,
                                     Errors errors, HttpSession session) {
        if (errors.hasErrors()) {
            return "novels/add";
        }
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        newNovel.setUser(user);
        novelRepository.save(newNovel);
        return "redirect:";
    }
}
