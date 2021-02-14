package com.johnwaz.varianceapp.controller;

import com.johnwaz.varianceapp.data.ChapterRepository;
import com.johnwaz.varianceapp.data.PageRepository;
import com.johnwaz.varianceapp.data.UserRepository;
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
    public String displayAddPageToChapterForm(Model model, @PathVariable(required = false) Integer chapterId, Integer bookId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (chapterId == null) {
            model.addAttribute("user", user);
            return "pages/index";
        } else {
            Optional<Chapter> result = chapterRepository.findById(chapterId);
            if (result.isEmpty()) {
                return "pages/index";
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
            return "pages/add";
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
            return "pages/index";
        } else {
            Optional<Page> result = pageRepository.findById(pageId);
            if (result.isEmpty()) {
                return "pages/index";
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

    @GetMapping(path = {"edit/{pageId}", "edit"})
    public String displayEditPageForm(Model model, @PathVariable(required = false) Integer pageId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (pageId == null){
            model.addAttribute("user", user);
            return "pages/index";
        } else {
            Optional<Page> result = pageRepository.findById(pageId);
            if (result.isEmpty()) {
                return "pages/index";
            } else {
                Page page = result.get();
                if (user.getId() != page.getUser().getId()) {
                    return "redirect:../";
                }
                model.addAttribute("page", page);
                model.addAttribute("uneditedPage", page);
                model.addAttribute("pageId", pageId);
            }
        }
        return "pages/edit";
    }

    @PostMapping("edit")
    public String processEditPageForm(@Valid @ModelAttribute Page editPage, Errors errors, Model model,
                                      int pageId, Integer pageNumber, String content) {

        if (errors.hasErrors()) {
            model.addAttribute("uneditedPage", pageRepository.findById(pageId).get());
            model.addAttribute("page", editPage);
            model.addAttribute("pageId", pageId);
            return "pages/edit";
        }
        Page page = pageRepository.findById(pageId).get();
        page.setPageNumber(pageNumber);
        page.setContent(content);
        pageRepository.save(page);
        return "redirect:view/" + pageId;
    }

    @PostMapping("view")
    public String processDeleteChapterPage(int pageId, int chapterId, RedirectAttributes redirectAttributes) {
        Optional optChapter = chapterRepository.findById(chapterId);
        redirectAttributes.addAttribute("id", optChapter.get());
        pageRepository.deleteById(pageId);
        return "redirect:/chapters/view/{id}";
    }
}
