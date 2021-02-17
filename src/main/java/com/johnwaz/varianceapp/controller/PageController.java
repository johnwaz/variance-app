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

    @GetMapping(path = {"bookChapterPageAdd/{chapterId}", "bookChapterPageAdd"})
    public String displayAddPageToBookChapterForm(Model model, @PathVariable(required = false) Integer chapterId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (chapterId == null) {
            model.addAttribute("user", user);
            model.addAttribute("pages", pageRepository.findAllById(Collections.singleton(userId)));
            return "pages/index";
        } else {
            Optional<Chapter> result = chapterRepository.findById(chapterId);
            if (result.isEmpty()) {
                return "pages/index";
            } else {
                Chapter chapter = result.get();
                if (user.getId() != chapter.getUser().getId()) {
                    return "pages/index";
                }
                model.addAttribute(new Page());
                model.addAttribute("chapter", chapter);
            }
        }
        return "pages/bookChapterPageAdd";
    }

    @PostMapping("bookChapterPageAdd/{chapterId}")
    public String processAddPageToBookChapterForm(@Valid @ModelAttribute Page newPage, Errors errors,
                                                  Model model, @PathVariable int chapterId, HttpSession session) {
        if (errors.hasErrors()) {
            model.addAttribute("chapter", chapterRepository.findById(chapterId).get());
            return "pages/bookChapterPageAdd";
        }
        Chapter chapter = chapterRepository.findById(chapterId).get();
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        newPage.setUser(user);
        newPage.setChapter(chapter);
        pageRepository.save(newPage);
        return "pages/bookChapterPageView";
    }

    @GetMapping(path = {"bookChapterPageView/{pageId}", "bookChapterPageView"})
    public String displayViewBookChapterPage(Model model, @PathVariable(required = false) Integer pageId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (pageId == null) {
            model.addAttribute("user", user);
            model.addAttribute("pages", pageRepository.findAllById(Collections.singleton(userId)));
            return "pages/index";
        } else {
            Optional<Page> result = pageRepository.findById(pageId);
            if (result.isEmpty()) {
                return "pages/index";
            } else {
                Page page = result.get();
                if (user.getId() != page.getUser().getId() || page.getChapter().getBook() == null) {
                    return "pages/index";
                }
                model.addAttribute("page", page);
            }
        }
        return "pages/booKChapterPageView";
    }

    @GetMapping(path = {"bookChapterPageEdit/{pageId}", "bookChapterPageEdit"})
    public String displayEditBookChapterPageForm(Model model, @PathVariable(required = false) Integer pageId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (pageId == null) {
            model.addAttribute("user", user);
            model.addAttribute("pages", pageRepository.findAllById(Collections.singleton(userId)));
            return "pages/index";
        } else {
            Optional<Page> result = pageRepository.findById(pageId);
            if (result.isEmpty()) {
                return "pages/index";
            } else {
                Page page = result.get();
                if (user.getId() != page.getUser().getId() || page.getChapter().getBook() == null) {
                    return "pages/index";
                }
                model.addAttribute("page", page);
                model.addAttribute("uneditedPage", page);
                model.addAttribute("pageId", pageId);
            }
        }
        return "pages/bookChapterPageEdit";
    }

    @PostMapping("bookChapterPageEdit")
    public String processEditBookChapterPageForm(@Valid @ModelAttribute Page editPage, Errors errors, Model model,
                                                 int pageId, Integer pageNumber, String content) {

        if (errors.hasErrors()) {
            model.addAttribute("uneditedPage", pageRepository.findById(pageId).get());
            model.addAttribute("page", editPage);
            model.addAttribute("pageId", pageId);
            return "pages/bookChapterPageEdit";
        }
        Page page = pageRepository.findById(pageId).get();
        page.setPageNumber(pageNumber);
        page.setContent(content);
        pageRepository.save(page);
        return "redirect:bookChapterPageView/" + pageId;
    }

    @PostMapping("bookChapterPageView")
    public String processDeleteBookChapterPage(int pageId, int chapterId, RedirectAttributes redirectAttributes) {
        Optional optChapter = chapterRepository.findById(chapterId);
        redirectAttributes.addAttribute("id", optChapter.get());
        pageRepository.deleteById(pageId);
        return "redirect:/chapters/bookChapterView/{id}";
    }

    @GetMapping(path = {"storyChapterPageAdd/{chapterId}", "storyChapterPageAdd"})
    public String displayAddPageToStoryChapterForm(Model model, @PathVariable(required = false) Integer chapterId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (chapterId == null) {
            model.addAttribute("user", user);
            model.addAttribute("pages", pageRepository.findAllById(Collections.singleton(userId)));
            return "pages/index";
        } else {
            Optional<Chapter> result = chapterRepository.findById(chapterId);
            if (result.isEmpty()) {
                return "pages/index";
            } else {
                Chapter chapter = result.get();
                if (user.getId() != chapter.getUser().getId()) {
                    return "pages/index";
                }
                model.addAttribute(new Page());
                model.addAttribute("chapter", chapter);
            }
        }
        return "pages/storyChapterPageAdd";
    }

    @PostMapping("storyChapterPageAdd/{chapterId}")
    public String processAddPageToStoryChapterForm(@Valid @ModelAttribute Page newPage, Errors errors,
                                                   Model model, @PathVariable int chapterId, HttpSession session) {
        if (errors.hasErrors()) {
            model.addAttribute("chapter", chapterRepository.findById(chapterId).get());
            return "pages/storyChapterPageAdd";
        }
        Chapter chapter = chapterRepository.findById(chapterId).get();
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        newPage.setUser(user);
        newPage.setChapter(chapter);
        pageRepository.save(newPage);
        return "pages/storyChapterPageView";
    }

    @GetMapping(path = {"storyChapterPageView/{pageId}", "storyChapterPageView"})
    public String displayViewStoryChapterPage(Model model, @PathVariable(required = false) Integer pageId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (pageId == null) {
            model.addAttribute("user", user);
            model.addAttribute("pages", pageRepository.findAllById(Collections.singleton(userId)));
            return "pages/index";
        } else {
            Optional<Page> result = pageRepository.findById(pageId);
            if (result.isEmpty()) {
                return "pages/index";
            } else {
                Page page = result.get();
                if (user.getId() != page.getUser().getId() || page.getChapter().getStory() == null) {
                    return "pages/index";
                }
                model.addAttribute("page", page);
            }
        }
        return "pages/storyChapterPageView";
    }

    @GetMapping(path = {"storyChapterPageEdit/{pageId}", "storyChapterPageEdit"})
    public String displayEditStoryChapterPageForm(Model model, @PathVariable(required = false) Integer pageId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (pageId == null) {
            model.addAttribute("user", user);
            model.addAttribute("pages", pageRepository.findAllById(Collections.singleton(userId)));
            return "pages/index";
        } else {
            Optional<Page> result = pageRepository.findById(pageId);
            if (result.isEmpty()) {
                return "pages/index";
            } else {
                Page page = result.get();
                if (user.getId() != page.getUser().getId() || page.getChapter().getStory() == null) {
                    return "pages/index";
                }
                model.addAttribute("page", page);
                model.addAttribute("uneditedPage", page);
                model.addAttribute("pageId", pageId);
            }
        }
        return "pages/storyChapterPageEdit";
    }

    @PostMapping("storyChapterPageEdit")
    public String processEditStoryChapterPageForm(@Valid @ModelAttribute Page editPage, Errors errors, Model model,
                                                  int pageId, Integer pageNumber, String content) {

        if (errors.hasErrors()) {
            model.addAttribute("uneditedPage", pageRepository.findById(pageId).get());
            model.addAttribute("page", editPage);
            model.addAttribute("pageId", pageId);
            return "pages/storyChapterPageEdit";
        }
        Page page = pageRepository.findById(pageId).get();
        page.setPageNumber(pageNumber);
        page.setContent(content);
        pageRepository.save(page);
        return "redirect:storyChapterPageView/" + pageId;
    }

    @PostMapping("storyChapterPageView")
    public String processDeleteStoryChapterPage(int pageId, int chapterId, RedirectAttributes redirectAttributes) {
        Optional optChapter = chapterRepository.findById(chapterId);
        redirectAttributes.addAttribute("id", optChapter.get());
        pageRepository.deleteById(pageId);
        return "redirect:/chapters/storyChapterView/{id}";
    }
}
