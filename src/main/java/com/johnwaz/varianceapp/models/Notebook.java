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

    @NotBlank(message = "Please give the notebook a name")
    private String name;

    @Size(max = 250, message = "Description must be less than 250 characters")
    private String description;

    public Notebook(User user, @NotBlank String name, @Size(max = 250, message = "Description must be less than 250 characters") String description) {
        this.user = user;
        this.name = name;
        this.description = description;
    }

    public Notebook() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
