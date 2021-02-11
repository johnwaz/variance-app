package com.johnwaz.varianceapp.data;

import com.johnwaz.varianceapp.models.Chapter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChapterRepository extends CrudRepository<Chapter, Integer> {
}
