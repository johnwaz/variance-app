package com.johnwaz.varianceapp.controller;

import com.johnwaz.varianceapp.data.BookRepository;
import com.johnwaz.varianceapp.data.UserRepository;
import com.johnwaz.varianceapp.models.Book;
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
@RequestMapping("books")
public class BookController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    private static final String userSessionKey = "user";

    @GetMapping
    public String displayAllBooks(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        model.addAttribute("user", user);
        model.addAttribute("books", bookRepository.findAllById(Collections.singleton(userId)));
        return "books/index";
    }

    @GetMapping("add")
    public String displayAddBookForm(Model model) {
        model.addAttribute(new Book());
        model.addAttribute("books", bookRepository.findAll());
        return "books/add";
    }

    @PostMapping("add")
    public String processAddBookForm(@Valid @ModelAttribute Book newBook,
                                     Errors errors, HttpSession session) {
        if (errors.hasErrors()) {
            return "books/add";
        }
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        newBook.setUser(user);
        bookRepository.save(newBook);
        return "redirect:../";
    }

    @GetMapping(path = {"view/{bookId}", "view"})
    public String displayViewBook(Model model, @PathVariable(required = false) Integer bookId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (bookId == null) {
            model.addAttribute("user", user);
            model.addAttribute("books", bookRepository.findAllById(Collections.singleton(userId)));
            return "books/index";
        } else {
            Optional<Book> result = bookRepository.findById(bookId);
            if (result.isEmpty()){
                return "redirect:../";
            } else {
                Book book = result.get();
                if (user.getId() != book.getUser().getId()) {
                    return "redirect:../";
                }
                model.addAttribute("book", book);
            }
        }
        return "books/view";
    }

    @GetMapping(path = {"edit/{bookId}", "edit"})
    public String displayEditBookForm(Model model, @PathVariable(required = false) Integer bookId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (bookId == null) {
            model.addAttribute("user", user);
            model.addAttribute("books", bookRepository.findAllById(Collections.singleton(userId)));
            return "books/index";
        } else {
            Optional<Book> result = bookRepository.findById(bookId);
            if (result.isEmpty()){
                return "redirect:../";
            } else {
                Book book = result.get();
                if (user.getId() != book.getUser().getId()) {
                    return "redirect:../";
                }
                model.addAttribute("book", book);
                model.addAttribute("uneditedBook", book);
                model.addAttribute("bookId", bookId);
            }
        }
        return "books/edit";
    }

    @PostMapping("edit")
    public String processEditBookForm(@Valid @ModelAttribute Book editBook, Errors errors, Model model,
                                      int bookId, String title, String description) {

        if (errors.hasErrors()) {
            model.addAttribute("book", editBook);
            model.addAttribute("uneditedBook", bookRepository.findById(bookId).get());
            model.addAttribute("bookId", bookId);
            return "books/edit";
        }
        Book book = bookRepository.findById(bookId).get();
        book.setTitle(title);
        book.setDescription(description);
        bookRepository.save(book);
        return "redirect:view/" + bookId;
    }

    @PostMapping("view")
    public String processDeleteBook(int bookId) {
        bookRepository.deleteById(bookId);
        return "redirect:../";
    }
}
