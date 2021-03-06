package com.johnwaz.varianceapp.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Notebook extends AbstractEntity {

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "notebook", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Subject> subjects = new ArrayList<>();

    @NotBlank(message = "(Please title the notebook)")
    @Size(max = 40, message = "(Title must be equal to or less than 40 characters)")
    private String title;

    @Size(max = 100, message = "(Description must be equal to or less than 100 characters)")
    private String description;

    public Notebook(User user, @NotBlank @Size(max = 40, message = "(Title must be equal to or less than 40 characters)") String title,
                    @Size(max = 100, message = "(Description must be equal to or less than 100 characters)") String description) {
        this.user = user;
        this.title = title;
        this.description = description;
    }

    public Notebook() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }
}
