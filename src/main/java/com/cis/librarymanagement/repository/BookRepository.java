package com.cis.librarymanagement.repository;

import com.cis.librarymanagement.entity.LibraryBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<LibraryBook, Integer> {
}
