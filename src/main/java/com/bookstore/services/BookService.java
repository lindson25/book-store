package com.bookstore.services;

import com.bookstore.domain.Book;
import com.bookstore.dto.BookDTO;
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

    public Book createBook(BookDTO book) {
        Book newBook = new Book(book);
        return bookRepository.save(newBook);
    }

    public Book updateBook(Long id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        book.setTitle(bookDTO.title());
        book.setAuthor(bookDTO.author());

        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void deleteBook(Long id) {
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        bookRepository.delete(existingBook);
    }
}
