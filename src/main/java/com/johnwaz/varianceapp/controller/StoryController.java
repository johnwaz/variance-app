package com.johnwaz.varianceapp.controller;

import com.johnwaz.varianceapp.data.NovelRepository;
import com.johnwaz.varianceapp.data.StoryRepository;
import com.johnwaz.varianceapp.data.UserRepository;
import com.johnwaz.varianceapp.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

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

    @GetMapping(path = {"add/{novelId}", "add"})
    public String displayAddStoryToNovelForm(Model model, @PathVariable(required = false) Integer novelId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (novelId == null) {
            model.addAttribute("user", user);
            model.addAttribute("stories", storyRepository.findAllById(Collections.singleton(userId)));
            return "stories/index";
        } else {
            Optional<Novel> result = novelRepository.findById(novelId);
            if (result.isEmpty()) {
                return "stories/index";
            } else {
                Novel novel = result.get();
                if (user.getId() != novel.getUser().getId()) {
                    return "stories/index";
                }
                model.addAttribute(new Story());
                model.addAttribute("novel", novel);
            }
        }
        return "stories/add";
    }

    @PostMapping("add/{novelId}")
    public String processAddStoryToNovelForm(@Valid @ModelAttribute Story newStory,
                                              Errors errors, Model model, @PathVariable int novelId,
                                              HttpSession session, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute("novel", novelRepository.findById(novelId).get());
            return "stories/add";
        }
        Optional optNovel = novelRepository.findById(novelId);
        Novel novel = novelRepository.findById(novelId).get();
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        redirectAttributes.addAttribute("id", optNovel.get());
        newStory.setUser(user);
        newStory.setNovel(novel);
        storyRepository.save(newStory);
        return "redirect:/novels/view/{id}";
    }

    @GetMapping(path = {"view/{storyId}", "view"})
    public String displayViewNovelStory(Model model, @PathVariable(required = false) Integer storyId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (storyId == null) {
            model.addAttribute("user", user);
            model.addAttribute("stories", storyRepository.findAllById(Collections.singleton(userId)));
            return "stories/index";
        } else {
            Optional<Story> result = storyRepository.findById(storyId);
            if (result.isEmpty()) {
                return "stories/index";
            } else {
                Story story = result.get();
                if (user.getId() != story.getUser().getId()) {
                    return "stories/index";
                }
                model.addAttribute("story", story);
            }
        }
        return "stories/view";
    }

    @GetMapping(path = {"edit/{storyId}", "edit"})
    public String displayEditNovelStoryForm(Model model, @PathVariable(required = false) Integer storyId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (storyId == null){
            model.addAttribute("user", user);
            model.addAttribute("stories", storyRepository.findAllById(Collections.singleton(userId)));
            return "stories/index";
        } else {
            Optional<Story> result = storyRepository.findById(storyId);
            if (result.isEmpty()) {
                return "stories/index";
            } else {
                Story story = result.get();
                if (user.getId() != story.getUser().getId()) {
                    return "stories/index";
                }
                model.addAttribute("story", story);
                model.addAttribute("uneditedStory", story);
                model.addAttribute("storyId", storyId);
            }
        }
        return "stories/edit";
    }

    @PostMapping("edit")
    public String processEditNovelStoryForm(@Valid @ModelAttribute Story editStory, Errors errors, Model model,
                                             int storyId, String name) {

        if (errors.hasErrors()) {
            model.addAttribute("uneditedStory", storyRepository.findById(storyId).get());
            model.addAttribute("story", editStory);
            model.addAttribute("storyId", storyId);
            return "stories/edit";
        }
        Story story = storyRepository.findById(storyId).get();
        story.setName(name);
        storyRepository.save(story);
        return "redirect:view/" + storyId;
    }

    @PostMapping("view")
    public String processDeleteNovelStory(int storyId, int novelId, RedirectAttributes redirectAttributes) {
        Optional optNovel = novelRepository.findById(novelId);
        redirectAttributes.addAttribute("id", optNovel.get());
        storyRepository.deleteById(storyId);
        return "redirect:/novels/view/{id}";
    }
}
