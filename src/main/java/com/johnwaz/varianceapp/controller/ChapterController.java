package com.johnwaz.varianceapp.controller;

import com.johnwaz.varianceapp.data.BookRepository;
import com.johnwaz.varianceapp.data.ChapterRepository;
import com.johnwaz.varianceapp.data.StoryRepository;
import com.johnwaz.varianceapp.data.UserRepository;
import com.johnwaz.varianceapp.models.Book;
import com.johnwaz.varianceapp.models.Chapter;
import com.johnwaz.varianceapp.models.Story;
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

    @Autowired
    private StoryRepository storyRepository;

    private static final String userSessionKey = "user";

    @GetMapping(path = {"bookChapterAdd/{bookId}", "bookChapterAdd"})
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
                    return "chapters/index";
                }
                model.addAttribute(new Chapter());
                model.addAttribute("book", book);
            }
        }
        return "chapters/bookChapterAdd";
    }

    @PostMapping("bookChapterAdd/{bookId}")
    public String processAddChapterToBookForm(@Valid @ModelAttribute Chapter newChapter,
                                       Errors errors, Model model, @PathVariable int bookId,
                                       HttpSession session, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute("book", bookRepository.findById(bookId).get());
            return "chapters/bookChapterAdd";
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

    @GetMapping(path = {"bookChapterView/{chapterId}", "bookChapterView"})
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
                if (user.getId() != chapter.getUser().getId() || chapter.getBook() == null) {
                    return "chapters/index";
                }
                model.addAttribute("chapter", chapter);
            }
        }
        return "chapters/bookChapterView";
    }

    @GetMapping(path = {"bookChapterEdit/{chapterId}", "bookChapterEdit"})
    public String displayEditBookChapterForm(Model model, @PathVariable(required = false) Integer chapterId, HttpSession session) {
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
                if (user.getId() != chapter.getUser().getId() || chapter.getBook() == null) {
                    return "chapters/index";
                }
                model.addAttribute("chapter", chapter);
                model.addAttribute("uneditedChapter", chapter);
                model.addAttribute("chapterId", chapterId);
            }
        }
        return "chapters/bookChapterEdit";
    }

    @PostMapping("bookChapterEdit")
    public String processEditBookChapterForm(@Valid @ModelAttribute Chapter editChapter, Errors errors, Model model,
                                         int chapterId, Integer chapterNumber, String name) {

        if (errors.hasErrors()) {
            model.addAttribute("uneditedChapter", chapterRepository.findById(chapterId).get());
            model.addAttribute("chapter", editChapter);
            model.addAttribute("chapterId", chapterId);
            return "chapters/bookChapterEdit";
        }
        Chapter chapter = chapterRepository.findById(chapterId).get();
        chapter.setChapterNumber(chapterNumber);
        chapter.setName(name);
        chapterRepository.save(chapter);
        return "redirect:bookChapterView/" + chapterId;
    }

    @PostMapping("bookChapterView")
    public String processDeleteBookChapter(int chapterId, int bookId, RedirectAttributes redirectAttributes) {
        Optional optBook = bookRepository.findById(bookId);
        redirectAttributes.addAttribute("id", optBook.get());
        chapterRepository.deleteById(chapterId);
        return "redirect:/books/view/{id}";
    }

    @GetMapping(path = {"storyChapterAdd/{storyId}", "storyChapterAdd"})
    public String displayAddChapterToStoryForm(Model model, @PathVariable(required = false) Integer storyId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (storyId == null) {
            model.addAttribute("user", user);
            model.addAttribute("chapters", chapterRepository.findAllById(Collections.singleton(userId)));
            return "chapters/index";
        } else {
            Optional<Story> result = storyRepository.findById(storyId);
            if (result.isEmpty()) {
                return "chapters/index";
            } else {
                Story story = result.get();
                if (user.getId() != story.getUser().getId()) {
                    return "chapters/index";
                }
                model.addAttribute(new Chapter());
                model.addAttribute("story", story);
            }
        }
        return "chapters/storyChapterAdd";
    }

    @PostMapping("storyChapterAdd/{storyId}")
    public String processAddChapterToStoryForm(@Valid @ModelAttribute Chapter newChapter,
                                               Errors errors, Model model, @PathVariable int storyId,
                                               HttpSession session, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute("story", storyRepository.findById(storyId).get());
            return "chapters/storyChapterAdd";
        }
        Optional optStory = storyRepository.findById(storyId);
        Story story = storyRepository.findById(storyId).get();
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        redirectAttributes.addAttribute("id", optStory.get());
        newChapter.setUser(user);
        newChapter.setStory(story);
        chapterRepository.save(newChapter);
        return "redirect:/stories/view/{id}";
    }

    @GetMapping(path = {"storyChapterView/{chapterId}", "storyChapterView"})
    public String displayViewStoryChapter(Model model, @PathVariable(required = false) Integer chapterId, HttpSession session) {
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
                if (user.getId() != chapter.getUser().getId() || chapter.getStory() == null) {
                    return "chapters/index";
                }
                model.addAttribute("chapter", chapter);
            }
        }
        return "chapters/storyChapterView";
    }

    @GetMapping(path = {"storyChapterEdit/{chapterId}", "storyChapterEdit"})
    public String displayEditStoryChapterForm(Model model, @PathVariable(required = false) Integer chapterId, HttpSession session) {
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
                if (user.getId() != chapter.getUser().getId() || chapter.getStory() == null) {
                    return "chapters/index";
                }
                model.addAttribute("chapter", chapter);
                model.addAttribute("uneditedChapter", chapter);
                model.addAttribute("chapterId", chapterId);
            }
        }
        return "chapters/storyChapterEdit";
    }

    @PostMapping("storyChapterEdit")
    public String processEditStoryChapterForm(@Valid @ModelAttribute Chapter editChapter, Errors errors, Model model,
                                             int chapterId, Integer chapterNumber, String name) {

        if (errors.hasErrors()) {
            model.addAttribute("uneditedChapter", chapterRepository.findById(chapterId).get());
            model.addAttribute("chapter", editChapter);
            model.addAttribute("chapterId", chapterId);
            return "chapters/storyChapterEdit";
        }
        Chapter chapter = chapterRepository.findById(chapterId).get();
        chapter.setChapterNumber(chapterNumber);
        chapter.setName(name);
        chapterRepository.save(chapter);
        return "redirect:storyChapterView/" + chapterId;
    }
}
