package com.johnwaz.varianceapp.controller;

import com.johnwaz.varianceapp.data.NotebookRepository;
import com.johnwaz.varianceapp.data.SubjectRepository;
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
@RequestMapping("subjects")
public class SubjectController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotebookRepository notebookRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    private static final String userSessionKey = "user";

    @GetMapping(path = {"add/{notebookId}", "add"})
    public String displayAddSubjectToNotebookForm(Model model, @PathVariable(required = false) Integer notebookId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (notebookId == null) {
            model.addAttribute("user", user);
            model.addAttribute("subjects", subjectRepository.findAllById(Collections.singleton(userId)));
            return "subjects/index";
        } else {
            Optional<Notebook> result = notebookRepository.findById(notebookId);
            if (result.isEmpty()) {
                return "subjects/index";
            } else {
                Notebook notebook = result.get();
                if (user.getId() != notebook.getUser().getId()) {
                    return "subjects/index";
                }
                model.addAttribute(new Subject());
                model.addAttribute("notebook", notebook);
            }
        }
        return "subjects/add";
    }

    @PostMapping("add/{notebookId}")
    public String processAddSubjectToNotebookForm(@Valid @ModelAttribute Subject newSubject,
                                             Errors errors, Model model, @PathVariable int notebookId,
                                             HttpSession session, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute("notebook", notebookRepository.findById(notebookId).get());
            return "subjects/add";
        }
        Optional optNotebook = notebookRepository.findById(notebookId);
        Notebook notebook = notebookRepository.findById(notebookId).get();
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        redirectAttributes.addAttribute("id", optNotebook.get());
        newSubject.setUser(user);
        newSubject.setNotebook(notebook);
        subjectRepository.save(newSubject);
        return "redirect:/notebooks/view/{id}";
    }
}