package com.bookstore.services;

import com.bookstore.domain.Book;
import com.bookstore.exceptions.NotFoundException;
import com.bookstore.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    @Autowired
    private final BookRepository bookRepository;

    public Book createBook(Book newBook) {
        return bookRepository.save(newBook);
    }

    public Book updateBook(Long id, Book newBook) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book not found with id: " + id));

        book.setTitle(newBook.getTitle());
        book.setAuthor(newBook.getAuthor());

        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void deleteBook(Long id) {
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book not found with id: " + id));
        bookRepository.delete(existingBook);
    }
}
