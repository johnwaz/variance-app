package com.johnwaz.varianceapp.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Chapter extends AbstractEntity {

    @ManyToOne
    private User user;

    @ManyToOne
    private Book book;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Page> pages = new ArrayList<>();

    @NotNull(message = "Please give a chapter number")
    @Min(value = 1, message = "Please give a chapter number")
    private Integer chapterNumber;

    @Size(max = 250, message = "Name must be less than 50 characters")
    private String name;

    public Chapter(User user, Book book, @NotNull @Min(value = 1) Integer chapterNumber,
                   @Size(max = 250, message = "Name must be less than 50 characters") String name) {
        this.user = user;
        this.book = book;
        this.chapterNumber = chapterNumber;
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

    public Integer getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(Integer chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Page> getPages() {
        return pages;
    }
}
