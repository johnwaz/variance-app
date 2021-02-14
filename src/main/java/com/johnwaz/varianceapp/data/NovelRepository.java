package com.johnwaz.varianceapp.data;

import com.johnwaz.varianceapp.models.Novel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NovelRepository extends CrudRepository<Novel, Integer> {
}
