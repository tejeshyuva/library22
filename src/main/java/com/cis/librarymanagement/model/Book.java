package com.cis.librarymanagement.model;

import com.cis.librarymanagement.entity.BookIsbn;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class Book {
    private int bookId;
    private String title;
    private String authorName;
    private int yearPublished;
    private int quantity;
    private List<BookIsbn> bookIsbnList;
}
