package com.cis.librarymanagement.controller;

import com.cis.librarymanagement.entity.LibraryBook;
import com.cis.librarymanagement.model.Book;
import com.cis.librarymanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public LibraryBook createdBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @RequestMapping(value = "/book/read", method = RequestMethod.GET)
    public String getBookById(@RequestParam int bookId) {
        return bookService.readBook(bookId).getTitle();
    }

    @RequestMapping(value = "/book/all", method = RequestMethod.GET)
    public Collection<Book> getAllBooks() {
        return bookService.readAllBooks();
    }

    @RequestMapping(value = "/book/{bookId}", method = RequestMethod.PUT)
    public Book update(@PathVariable int bookId,
                         @RequestBody Book book) {
        return bookService.updateBookData(bookId, book);
    }

    @RequestMapping(value = "/book/{bookId}", method = RequestMethod.DELETE)
    public Book delete(@PathVariable int bookId) {
        System.out.println("Book Deleted");
        return bookService.deletebook(bookId);
    }
}
