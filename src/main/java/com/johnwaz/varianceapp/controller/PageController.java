package com.johnwaz.varianceapp.controller;

import com.johnwaz.varianceapp.data.ChapterRepository;
import com.johnwaz.varianceapp.data.PageRepository;
import com.johnwaz.varianceapp.data.UserRepository;
import com.johnwaz.varianceapp.models.Book;
import com.johnwaz.varianceapp.models.Chapter;
import com.johnwaz.varianceapp.models.Page;
import com.johnwaz.varianceapp.models.User;
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
@RequestMapping("pages")
public class PageController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private PageRepository pageRepository;

    private static final String userSessionKey = "user";

    @GetMapping(path = {"add/{chapterId}", "add"})
    public String displayAddPageToChapterForm(Model model, @PathVariable(required = false) Integer chapterId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (chapterId == null) {
            model.addAttribute("user", user);
            model.addAttribute("pages", pageRepository.findAllById(Collections.singleton(userId)));
            return "redirect:../";
        } else {
            Optional<Chapter> result = chapterRepository.findById(chapterId);
            if (result.isEmpty()) {
                return "redirect:../";
            } else {
                Chapter chapter = result.get();
                if (user.getId() != chapter.getUser().getId()) {
                    return "redirect:../";
                }
                model.addAttribute(new Page());
                model.addAttribute("chapter", chapter);
            }
        }
        return "pages/add";
    }

    @PostMapping("add/{chapterId}")
    public String processAddPageToChapterForm(@Valid @ModelAttribute Page newPage,
                                              Errors errors, Model model, @PathVariable int chapterId,
                                              HttpSession session, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute("chapter", chapterRepository.findById(chapterId).get());
            return "chapters/add";
        }
        Optional optChapter = chapterRepository.findById(chapterId);
        Chapter chapter = chapterRepository.findById(chapterId).get();
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        redirectAttributes.addAttribute("id", optChapter.get());
        newPage.setUser(user);
        newPage.setChapter(chapter);
        pageRepository.save(newPage);
        return "redirect:/chapters/view/{id}";
    }

    @GetMapping(path = {"view/{pageId}", "view"})
    public String displayViewPage(Model model, @PathVariable(required = false) Integer pageId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (pageId == null) {
            model.addAttribute("user", user);
            model.addAttribute("pages", pageRepository.findAllById(Collections.singleton(userId)));
            return "redirect:../";
        } else {
            Optional<Page> result = pageRepository.findById(pageId);
            if (result.isEmpty()) {
                return "redirect:../";
            } else {
                Page page = result.get();
                if (user.getId() != page.getUser().getId()) {
                    return "redirect:../";
                }
                model.addAttribute("page", page);
            }
        }
        return "pages/view";
    }
}
