package com.johnwaz.varianceapp.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Novel extends AbstractEntity {

    @ManyToOne
    private User user;
}
