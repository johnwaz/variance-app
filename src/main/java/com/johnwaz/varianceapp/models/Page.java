package com.johnwaz.varianceapp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Page extends AbstractEntity {

    @ManyToOne
    private User user;

    @ManyToOne
    private Chapter chapter;

    @NotNull(message = "Please give a page number")
    @Min(value = 1, message = "Please give a page number")
    private Integer pageNumber;

    @NotBlank(message = "Please add some content to the page")
    @Size(max = 3000, message = "Content must be less than 3,000 characters")
    @Column(columnDefinition = "text")
    private String content;

    public Page(User user, Chapter chapter, @NotNull @Min(value = 1) Integer pageNumber,
                @NotBlank @Size(max = 3000, message = "Content must be less than 3,000 characters") String content) {
        this.user = user;
        this.chapter = chapter;
        this.pageNumber = pageNumber;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
