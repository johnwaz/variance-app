package com.johnwaz.varianceapp.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Novel extends AbstractEntity {

    @ManyToOne
    private User user;

    @NotBlank(message = "Please give the book a title")
    private String title;

    @Size(max = 250, message = "Description must be less than 250 characters")
    private String description;

    public Novel(User user, @NotBlank String title, @Size(max = 250, message = "Description must be less than 250 characters") String description) {
        this.user = user;
        this.title = title;
        this.description = description;
    }

    public Novel() {}

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
}
