package com.cis.librarymanagement.service;

import com.cis.librarymanagement.entity.*;
import com.cis.librarymanagement.model.Book;

import com.cis.librarymanagement.model.Member;
import com.cis.librarymanagement.repository.BookIsbnRepository;
import com.cis.librarymanagement.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookIsbnRepository bookIsbnRepository;
    @Autowired
    private BookRepository bookRepository;
    public LibraryBook createBook(Book book) {
        int bookId = Math.abs(new Random().nextInt());
        LibraryBook libraryBook = new LibraryBook();
        libraryBook.setBookId(bookId);
        libraryBook.setTitle(book.getTitle());
        libraryBook.setAuthorName(book.getAuthorName());
        libraryBook.setQuantity(book.getQuantity());
        libraryBook.setYearPublished(book.getYearPublished());

        if(book.getBookIsbnList() != null) {
            List<BookIsbn> bookIsbnList = book.getBookIsbnList()
                    .stream().map(cd -> {
                        BookIsbn bookIsbn = new BookIsbn();
                        bookIsbn.setIsbn(cd.getIsbn());
                        bookIsbn.setLibraryBook(cd.getLibraryBook());
                        bookIsbn.setCheckoutList(cd.getCheckoutList());
                        return bookIsbn;
                    }).toList();
            bookIsbnRepository.saveAll(bookIsbnList);
            libraryBook.setBookIsbnList(bookIsbnList);
        }
        return bookRepository.save(libraryBook);
    }

    public Book readBook(Integer bookId) {

        Optional<LibraryBook> bookOptional =
                bookRepository.findById(bookId);
        LibraryBook libraryBook =
                bookOptional.orElse(new LibraryBook());

        Book book = new Book();
        book.setBookId(libraryBook.getBookId());
        book.setTitle(libraryBook.getTitle());
        book.setAuthorName(libraryBook.getAuthorName());
        book.setYearPublished(libraryBook.getYearPublished());
        book.setQuantity(libraryBook.getQuantity());


        List<BookIsbn> bookIsbnList =
                libraryBook.getBookIsbnList().stream().map(c -> {
                    BookIsbn bookIsbn = new BookIsbn();
                    bookIsbn.setIsbn(c.getIsbn());
                    bookIsbn.setCheckoutList(c.getCheckoutList());
                    bookIsbn.setLibraryBook(c.getLibraryBook());
                    return bookIsbn;
                }).collect(Collectors.toList());

        book.setBookIsbnList(bookIsbnList);

        return book;
    }

    public Book updateBookData(int bookId, Book updateBook) {
        Optional<LibraryBook> existingBookOptional = bookRepository.findById(bookId);
        if(existingBookOptional.isPresent()) {
            LibraryBook existingBook = existingBookOptional.get();
            if (!updateBook.getTitle().isEmpty()) {
                existingBook.setTitle(updateBook.getTitle());
            } else if (!updateBook.getAuthorName().isEmpty()) {
                existingBook.setAuthorName(updateBook.getAuthorName());
            } else if (updateBook.getYearPublished() != 0) {
                existingBook.setYearPublished(updateBook.getYearPublished());
            } else if (updateBook.getQuantity() != 0) {
                existingBook.setQuantity(updateBook.getQuantity());
            }
            LibraryBook savedLibraryBook = bookRepository.save(existingBook);
            System.out.println("updated successfully");
            return convertToBook(savedLibraryBook);
        }
        return null;
    }

    private Book convertToBook(LibraryBook libraryBook) {
        return new Book();
    }

    public Book deletebook(int bookId) {
        if(bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId);
        }
        return null;
    }

    public List<Book> readAllBooks() {
        List<LibraryBook> libraryBookList =
                bookRepository.findAll();

        return libraryBookList.stream()
                .map(this::convertModel)
                .toList();
    }

    private Book convertModel(LibraryBook libraryBook) {
        Book book = new Book();
        book.setBookId(libraryBook.getBookId());
        book.setTitle(libraryBook.getTitle());
        book.setAuthorName(libraryBook.getAuthorName());
        book.setYearPublished(libraryBook.getYearPublished());
        book.setQuantity(libraryBook.getQuantity());


        List<BookIsbn> bookIsbnList =
                libraryBook.getBookIsbnList().stream().map(c -> {
                    BookIsbn cdo = new BookIsbn();
                    cdo.setIsbn(c.getIsbn());
                    cdo.setCheckoutList(c.getCheckoutList());
                    cdo.setLibraryBook(c.getLibraryBook());
                    return  cdo;
                }).collect(Collectors.toList());

        book.setBookIsbnList(bookIsbnList);

        return book;
    }

}
