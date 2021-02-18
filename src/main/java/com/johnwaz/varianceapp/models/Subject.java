package com.johnwaz.varianceapp.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Subject extends AbstractEntity {

    @ManyToOne
    private User user;
}
