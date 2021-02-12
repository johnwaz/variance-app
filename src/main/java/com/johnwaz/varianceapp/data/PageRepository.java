package com.johnwaz.varianceapp.data;

import com.johnwaz.varianceapp.models.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends CrudRepository<Page, Integer> {
}
