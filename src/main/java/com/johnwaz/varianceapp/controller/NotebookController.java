package com.johnwaz.varianceapp.controller;

import com.johnwaz.varianceapp.data.NotebookRepository;
import com.johnwaz.varianceapp.data.UserRepository;
import com.johnwaz.varianceapp.models.Notebook;
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
@RequestMapping("notebooks")
public class NotebookController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotebookRepository notebookRepository;

    private static final String userSessionKey = "user";

    @GetMapping
    public String displayAllNotebooks(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        model.addAttribute("user", user);
        model.addAttribute("notebooks", notebookRepository.findAllById(Collections.singleton(userId)));
        return "notebooks/index";
    }

    @GetMapping("add")
    public String displayAddNotebookForm(Model model) {
        model.addAttribute(new Notebook());
        model.addAttribute("notebooks", notebookRepository.findAll());
        return "notebooks/add";
    }

    @PostMapping("add")
    public String processAddNotebookForm(@Valid @ModelAttribute Notebook newNotebook,
                                      Errors errors, HttpSession session) {
        if (errors.hasErrors()) {
            return "notebooks/add";
        }
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        newNotebook.setUser(user);
        notebookRepository.save(newNotebook);
        return "redirect:";
    }
}
