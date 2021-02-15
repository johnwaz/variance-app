package com.johnwaz.varianceapp.controller;

import com.johnwaz.varianceapp.data.NovelRepository;
import com.johnwaz.varianceapp.data.UserRepository;
import com.johnwaz.varianceapp.models.Book;
import com.johnwaz.varianceapp.models.Novel;
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
@RequestMapping("novels")
public class NovelController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NovelRepository novelRepository;

    private static final String userSessionKey = "user";

    @GetMapping
    public String displayAllNovels(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        model.addAttribute("user", user);
        model.addAttribute("novels", novelRepository.findAllById(Collections.singleton(userId)));
        return "novels/index";
    }

    @GetMapping("add")
    public String displayAddNovelForm(Model model) {
        model.addAttribute(new Novel());
        model.addAttribute("novels", novelRepository.findAll());
        return "novels/add";
    }

    @PostMapping("add")
    public String processAddNovelForm(@Valid @ModelAttribute Novel newNovel,
                                     Errors errors, HttpSession session) {
        if (errors.hasErrors()) {
            return "novels/add";
        }
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        newNovel.setUser(user);
        novelRepository.save(newNovel);
        return "redirect:";
    }

    @GetMapping(path = {"view/{novelId}", "view"})
    public String displayViewNovel(Model model, @PathVariable(required = false) Integer novelId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (novelId == null){
            model.addAttribute("user", user);
            model.addAttribute("novels", novelRepository.findAllById(Collections.singleton(userId)));
            return "novels/index";
        } else {
            Optional<Novel> result = novelRepository.findById(novelId);
            if (result.isEmpty()){
                return "redirect:../";
            } else {
                Novel novel = result.get();
                if (user.getId() != novel.getUser().getId()) {
                    return "redirect:../";
                }
                model.addAttribute("novel", novel);
            }
        }
        return "novels/view";
    }

    @GetMapping(path = {"edit/{novelId}", "edit"})
    public String displayEditNovelForm(Model model, @PathVariable(required = false) Integer novelId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (novelId == null){
            model.addAttribute("user", user);
            model.addAttribute("novels", novelRepository.findAllById(Collections.singleton(userId)));
            return "novels/index";
        } else {
            Optional<Novel> result = novelRepository.findById(novelId);
            if (result.isEmpty()){
                return "redirect:../";
            } else {
                Novel novel = result.get();
                if (user.getId() != novel.getUser().getId()) {
                    return "redirect:../";
                }
                model.addAttribute("novel", novel);
                model.addAttribute("uneditedNovel", novel);
                model.addAttribute("novelId", novelId);
            }
        }
        return "novels/edit";
    }

    @PostMapping("edit")
    public String processEditNovelForm(@Valid @ModelAttribute Novel editNovel, Errors errors, Model model,
                                      int novelId, String title, String description) {

        if (errors.hasErrors()) {
            model.addAttribute("novel", editNovel);
            model.addAttribute("uneditedNovel", novelRepository.findById(novelId).get());
            model.addAttribute("novelId", novelId);
            return "novels/edit";
        }
        Novel novel = novelRepository.findById(novelId).get();
        novel.setTitle(title);
        novel.setDescription(description);
        novelRepository.save(novel);
        return "redirect:view/" + novelId;
    }

    @PostMapping("view")
    public String processDeleteNovel(int novelId) {
        novelRepository.deleteById(novelId);
        return "redirect:";
    }
}
