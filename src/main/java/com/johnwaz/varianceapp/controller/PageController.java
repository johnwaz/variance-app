package com.johnwaz.varianceapp.controller;

import com.johnwaz.varianceapp.data.ChapterRepository;
import com.johnwaz.varianceapp.data.PageRepository;
import com.johnwaz.varianceapp.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("pages")
public class PageController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private PageRepository pageRepository;
}
