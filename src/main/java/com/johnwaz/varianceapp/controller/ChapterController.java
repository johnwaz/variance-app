package com.johnwaz.varianceapp.controller;

import com.johnwaz.varianceapp.data.BookRepository;
import com.johnwaz.varianceapp.data.ChapterRepository;
import com.johnwaz.varianceapp.data.UserRepository;
import com.johnwaz.varianceapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@Controller
@RequestMapping("chapters")
public class ChapterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    private static final String userSessionKey = "user";
}
