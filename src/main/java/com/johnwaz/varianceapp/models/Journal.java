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
public class Journal extends AbstractEntity {

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "journal", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Page> pages = new ArrayList<>();

    @NotBlank(message = "(Please name the journal)")
    @Size(max = 40, message = "(Name must be equal to or less than 40 characters)")
    private String name;

    @Size(max = 100, message = "(Description must be equal to or less than 100 characters)")
    private String description;

    public Journal(User user, @NotBlank @Size(max = 40, message = "(Name must be equal to or less than 40 characters)") String name,
                   @Size(max = 100, message = "(Description must be equal to or less than 100 characters)") String description) {
        this.user = user;
        this.name = name;
        this.description = description;
    }

    public Journal() {}

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

    public List<Page> getPages() {
        return pages;
    }
}
