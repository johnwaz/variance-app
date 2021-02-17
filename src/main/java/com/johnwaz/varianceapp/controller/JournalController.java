package com.johnwaz.varianceapp.controller;

import com.johnwaz.varianceapp.data.JournalRepository;
import com.johnwaz.varianceapp.data.UserRepository;
import com.johnwaz.varianceapp.models.Book;
import com.johnwaz.varianceapp.models.Journal;
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

@Controller
@RequestMapping("journals")
public class JournalController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JournalRepository journalRepository;

    private static final String userSessionKey = "user";

    @GetMapping("add")
    public String displayAddJournalForm(Model model) {
        model.addAttribute(new Journal());
        model.addAttribute("journals", journalRepository.findAll());
        return "journals/add";
    }

    @PostMapping("add")
    public String processAddJournalForm(@Valid @ModelAttribute Journal newJournal,
                                     Errors errors, HttpSession session) {
        if (errors.hasErrors()) {
            return "journals/add";
        }
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        newJournal.setUser(user);
        journalRepository.save(newJournal);
        return "redirect:";
    }
}
