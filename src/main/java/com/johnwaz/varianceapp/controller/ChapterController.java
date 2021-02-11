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
    public String displayAddChapterForm(Model model, @PathVariable(required = false) Integer bookId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (bookId == null) {
            model.addAttribute("user", user);
            model.addAttribute("chapters", chapterRepository.findAllById(Collections.singleton(userId)));
            return "redirect:../";
        } else {
            Optional<Book> result = bookRepository.findById(bookId);
            if (result.isEmpty()) {
                return "redirect:../";
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
    public String processAddChapterForm(@Valid @ModelAttribute Chapter newChapter,
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
}
