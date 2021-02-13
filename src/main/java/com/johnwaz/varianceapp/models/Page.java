package com.johnwaz.varianceapp.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Page extends AbstractEntity {

    @ManyToOne
    private User user;

    @ManyToOne
    private Chapter chapter;

    @NotNull(message = "Please give the page a number")
    @Min(value = 1, message = "Please give the page a number")
    private Integer pageNumber;

    public Page(User user, Chapter chapter, @NotNull @Min(value = 1) Integer pageNumber) {
        this.user = user;
        this.chapter = chapter;
        this.pageNumber = pageNumber;
    }

    public Page() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
