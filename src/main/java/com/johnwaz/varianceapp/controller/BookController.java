package com.johnwaz.varianceapp.controller;

import com.johnwaz.varianceapp.data.BookRepository;
import com.johnwaz.varianceapp.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("books")
public class BookController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;
}
