package com.johnwaz.varianceapp.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginFormDTO {

    @NotNull
    @NotBlank(message = "Please enter a username")
    @Size(min = 4, max = 20, message = "Invalid username. Must be between 4 and 20 characters")
    private String username;

    @NotNull
    @NotBlank(message = "Please enter a password")
    @Size(min = 6, max = 30, message = "Invalid password. Must be between 6 and 30 characters")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
