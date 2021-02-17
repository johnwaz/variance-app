package com.johnwaz.varianceapp.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Journal extends AbstractEntity {

    @ManyToOne
    private User user;
}
