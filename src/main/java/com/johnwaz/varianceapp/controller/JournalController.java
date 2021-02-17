package com.johnwaz.varianceapp.controller;

import com.johnwaz.varianceapp.data.JournalRepository;
import com.johnwaz.varianceapp.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("journals")
public class JournalController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JournalRepository journalRepository;

    private static final String userSessionKey = "user";
}
