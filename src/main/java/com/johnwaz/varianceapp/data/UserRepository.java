package com.johnwaz.varianceapp.data;

import com.johnwaz.varianceapp.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);
}
