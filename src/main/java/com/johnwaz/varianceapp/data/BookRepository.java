package com.johnwaz.varianceapp.data;

import com.johnwaz.varianceapp.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
}
