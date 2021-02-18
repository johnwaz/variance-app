package com.johnwaz.varianceapp.data;

import com.johnwaz.varianceapp.models.Notebook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotebookRepository extends CrudRepository<Notebook, Integer> {
}
