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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("journals")
public class JournalController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JournalRepository journalRepository;

    private static final String userSessionKey = "user";

    @GetMapping
    public String displayAllJournals(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        model.addAttribute("user", user);
        model.addAttribute("journals", journalRepository.findAllById(Collections.singleton(userId)));
        return "journals/index";
    }

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

    @GetMapping(path = {"view/{journalId}", "view"})
    public String displayViewJournal(Model model, @PathVariable(required = false) Integer journalId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (journalId == null){
            model.addAttribute("user", user);
            model.addAttribute("journals", journalRepository.findAllById(Collections.singleton(userId)));
            return "journals/index";
        } else {
            Optional<Journal> result = journalRepository.findById(journalId);
            if (result.isEmpty()){
                return "redirect:../";
            } else {
                Journal journal = result.get();
                if (user.getId() != journal.getUser().getId()) {
                    return "redirect:../";
                }
                model.addAttribute("journal", journal);
            }
        }
        return "journals/view";
    }
}
