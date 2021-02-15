package com.johnwaz.varianceapp.data;

import com.johnwaz.varianceapp.models.Story;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends CrudRepository<Story, Integer> {
}
