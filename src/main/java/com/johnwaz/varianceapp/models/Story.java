package com.johnwaz.varianceapp.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Story {

    @ManyToOne
    private User user;

    @ManyToOne
    private Novel novel;
}
