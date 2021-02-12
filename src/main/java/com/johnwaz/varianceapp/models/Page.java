package com.johnwaz.varianceapp.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Page extends AbstractEntity {

    @ManyToOne
    private User user;

    @ManyToOne
    private Chapter chapter;

    private Integer pageNumber;

    public Page(User user, Chapter chapter, Integer pageNumber) {
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
