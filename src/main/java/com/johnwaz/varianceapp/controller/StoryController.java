package com.johnwaz.varianceapp.controller;

import com.johnwaz.varianceapp.data.NovelRepository;
import com.johnwaz.varianceapp.data.StoryRepository;
import com.johnwaz.varianceapp.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("stories")
public class StoryController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NovelRepository novelRepository;

    @Autowired
    private StoryRepository storyRepository;

    private static final String userSessionKey = "user";
}
