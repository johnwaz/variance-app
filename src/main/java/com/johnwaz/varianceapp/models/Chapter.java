package com.johnwaz.varianceapp.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Chapter extends AbstractEntity {

    @ManyToOne
    private User user;

    @ManyToOne
    private Book book;

    @NotBlank(message = "Please name the chapter")
    private String name;

    public Chapter(User user, Book book, @NotBlank String name) {
        this.user = user;
        this.book = book;
        this.name = name;
    }

    public Chapter() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
