package com.johnwaz.varianceapp.data;

import com.johnwaz.varianceapp.models.Journal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends CrudRepository<Journal, Integer> {
}
