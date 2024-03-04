package com.cis.librarymanagement.repository;

import com.cis.librarymanagement.entity.BookIsbn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookIsbnRepository extends JpaRepository<BookIsbn, Integer> {
}
