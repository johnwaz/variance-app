package com.johnwaz.varianceapp.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Book extends AbstractEntity {

    @ManyToOne
    private User user;

    @NotBlank(message = "Please name the book")
    private String name;

    @Size(max = 250, message = "Description must be less than 250 characters")
    private String description;

    public Book(User user, @NotBlank String name, @Size(max = 250, message = "Description must be less than 250 characters") String description) {
        this.user = user;
        this.name = name;
        this.description = description;
    }

    public Book() {}

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
}
