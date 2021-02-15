package com.johnwaz.varianceapp.controller;

import com.johnwaz.varianceapp.data.BookRepository;
import com.johnwaz.varianceapp.data.ChapterRepository;
import com.johnwaz.varianceapp.data.UserRepository;
import com.johnwaz.varianceapp.models.Book;
import com.johnwaz.varianceapp.models.Chapter;
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
@RequestMapping("chapters")
public class ChapterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    private static final String userSessionKey = "user";

    @GetMapping(path = {"add/{bookId}", "add"})
    public String displayAddChapterToBookForm(Model model, @PathVariable(required = false) Integer bookId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (bookId == null) {
            model.addAttribute("user", user);
            model.addAttribute("chapters", chapterRepository.findAllById(Collections.singleton(userId)));
            return "chapters/index";
        } else {
            Optional<Book> result = bookRepository.findById(bookId);
            if (result.isEmpty()) {
                return "chapters/index";
            } else {
                Book book = result.get();
                if (user.getId() != book.getUser().getId()) {
                    return "redirect:../";
                }
                model.addAttribute(new Chapter());
                model.addAttribute("book", book);
            }
        }
        return "chapters/add";
    }

    @PostMapping("add/{bookId}")
    public String processAddChapterToBookForm(@Valid @ModelAttribute Chapter newChapter,
                                       Errors errors, Model model, @PathVariable int bookId,
                                       HttpSession session, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute("book", bookRepository.findById(bookId).get());
            return "chapters/add";
        }
        Optional optBook = bookRepository.findById(bookId);
        Book book = bookRepository.findById(bookId).get();
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        redirectAttributes.addAttribute("id", optBook.get());
        newChapter.setUser(user);
        newChapter.setBook(book);
        chapterRepository.save(newChapter);
        return "redirect:/books/view/{id}";
    }

    @GetMapping(path = {"view/{chapterId}", "view"})
    public String displayViewBookChapter(Model model, @PathVariable(required = false) Integer chapterId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (chapterId == null) {
            model.addAttribute("user", user);
            model.addAttribute("chapters", chapterRepository.findAllById(Collections.singleton(userId)));
            return "chapters/index";
        } else {
            Optional<Chapter> result = chapterRepository.findById(chapterId);
            if (result.isEmpty()) {
                return "chapters/index";
            } else {
                Chapter chapter = result.get();
                if (user.getId() != chapter.getUser().getId()) {
                    return "redirect:../";
                }
                model.addAttribute("chapter", chapter);
            }
        }
        return "chapters/view";
    }

    @GetMapping(path = {"edit/{chapterId}", "edit"})
    public String displayEditChapterForm(Model model, @PathVariable(required = false) Integer chapterId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (chapterId == null){
            model.addAttribute("user", user);
            model.addAttribute("chapters", chapterRepository.findAllById(Collections.singleton(userId)));
            return "chapters/index";
        } else {
            Optional<Chapter> result = chapterRepository.findById(chapterId);
            if (result.isEmpty()) {
                return "chapters/index";
            } else {
                Chapter chapter = result.get();
                if (user.getId() != chapter.getUser().getId()) {
                    return "redirect:../";
                }
                model.addAttribute("chapter", chapter);
                model.addAttribute("uneditedChapter", chapter);
                model.addAttribute("chapterId", chapterId);
            }
        }
        return "chapters/edit";
    }

    @PostMapping("edit")
    public String processEditChapterForm(@Valid @ModelAttribute Chapter editChapter, Errors errors, Model model,
                                         int chapterId, Integer chapterNumber, String name) {

        if (errors.hasErrors()) {
            model.addAttribute("uneditedChapter", chapterRepository.findById(chapterId).get());
            model.addAttribute("chapter", editChapter);
            model.addAttribute("chapterId", chapterId);
            return "chapters/edit";
        }
        Chapter chapter = chapterRepository.findById(chapterId).get();
        chapter.setChapterNumber(chapterNumber);
        chapter.setName(name);
        chapterRepository.save(chapter);
        return "redirect:view/" + chapterId;
    }

    @PostMapping("view")
    public String processDeleteBookChapter(int chapterId, int bookId, RedirectAttributes redirectAttributes) {
        Optional optBook = bookRepository.findById(bookId);
        redirectAttributes.addAttribute("id", optBook.get());
        chapterRepository.deleteById(chapterId);
        return "redirect:/books/view/{id}";
    }
}
