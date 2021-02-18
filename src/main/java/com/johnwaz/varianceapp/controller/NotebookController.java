package com.johnwaz.varianceapp.controller;

import com.johnwaz.varianceapp.data.NotebookRepository;
import com.johnwaz.varianceapp.data.UserRepository;
import com.johnwaz.varianceapp.models.Notebook;
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
@RequestMapping("notebooks")
public class NotebookController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotebookRepository notebookRepository;

    private static final String userSessionKey = "user";

    @GetMapping
    public String displayAllNotebooks(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        model.addAttribute("user", user);
        model.addAttribute("notebooks", notebookRepository.findAllById(Collections.singleton(userId)));
        return "notebooks/index";
    }

    @GetMapping("add")
    public String displayAddNotebookForm(Model model) {
        model.addAttribute(new Notebook());
        model.addAttribute("notebooks", notebookRepository.findAll());
        return "notebooks/add";
    }

    @PostMapping("add")
    public String processAddNotebookForm(@Valid @ModelAttribute Notebook newNotebook,
                                      Errors errors, HttpSession session) {
        if (errors.hasErrors()) {
            return "notebooks/add";
        }
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        newNotebook.setUser(user);
        notebookRepository.save(newNotebook);
        return "redirect:";
    }

    @GetMapping(path = {"view/{notebookId}", "view"})
    public String displayViewNotebook(Model model, @PathVariable(required = false) Integer notebookId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (notebookId == null) {
            model.addAttribute("user", user);
            model.addAttribute("notebooks", notebookRepository.findAllById(Collections.singleton(userId)));
            return "notebooks/index";
        } else {
            Optional<Notebook> result = notebookRepository.findById(notebookId);
            if (result.isEmpty()){
                return "redirect:../";
            } else {
                Notebook notebook = result.get();
                if (user.getId() != notebook.getUser().getId()) {
                    return "redirect:../";
                }
                model.addAttribute("notebook", notebook);
            }
        }
        return "notebooks/view";
    }

    @GetMapping(path = {"edit/{notebookId}", "edit"})
    public String displayEditNotebookForm(Model model, @PathVariable(required = false) Integer notebookId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (notebookId == null) {
            model.addAttribute("user", user);
            model.addAttribute("notebooks", notebookRepository.findAllById(Collections.singleton(userId)));
            return "notebooks/index";
        } else {
            Optional<Notebook> result = notebookRepository.findById(notebookId);
            if (result.isEmpty()){
                return "redirect:../";
            } else {
                Notebook notebook = result.get();
                if (user.getId() != notebook.getUser().getId()) {
                    return "redirect:../";
                }
                model.addAttribute("notebook", notebook);
                model.addAttribute("uneditedNotebook", notebook);
                model.addAttribute("notebookId", notebookId);
            }
        }
        return "notebooks/edit";
    }

    @PostMapping("edit")
    public String processEditNotebookForm(@Valid @ModelAttribute Notebook editNotebook, Errors errors, Model model,
                                       int notebookId, String title, String description) {

        if (errors.hasErrors()) {
            model.addAttribute("notebook", editNotebook);
            model.addAttribute("uneditedNotebook", notebookRepository.findById(notebookId).get());
            model.addAttribute("notebookId", notebookId);
            return "notebooks/edit";
        }
        Notebook notebook = notebookRepository.findById(notebookId).get();
        notebook.setTitle(title);
        notebook.setDescription(description);
        notebookRepository.save(notebook);
        return "redirect:view/" + notebookId;
    }

    @PostMapping("view")
    public String processDeleteNotebook(int notebookId) {
        notebookRepository.deleteById(notebookId);
        return "redirect:";
    }
}
