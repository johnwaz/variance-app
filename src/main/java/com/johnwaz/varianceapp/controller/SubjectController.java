package com.johnwaz.varianceapp.controller;

import com.johnwaz.varianceapp.data.NotebookRepository;
import com.johnwaz.varianceapp.data.SubjectRepository;
import com.johnwaz.varianceapp.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("subjects")
public class SubjectController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotebookRepository notebookRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    private static final String userSessionKey = "user";
}
