package com.johnwaz.varianceapp.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Story extends AbstractEntity {

    @ManyToOne
    private User user;

    @ManyToOne
    private Novel novel;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Chapter> chapters = new ArrayList<>();

    @NotBlank(message = "Please name the story")
    private String name;

    public Story(User user, Novel novel, @NotBlank String name) {
        this.user = user;
        this.novel = novel;
        this.name = name;
    }

    public Story() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Novel getNovel() {
        return novel;
    }

    public void setNovel(Novel novel) {
        this.novel = novel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }
}
