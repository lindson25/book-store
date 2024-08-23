package com.bookstore.services;

import com.bookstore.domain.book.Book;
import com.bookstore.domain.book.BookDTO;
import com.bookstore.exceptions.BookNotFoundException;
import com.bookstore.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public BookDTO toDTO(Book entity) {
        return new BookDTO(entity.getTitle(), entity.getAuthor());
    }

    public BookDTO createBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setAuthor(bookDTO.author());
        book.setTitle(bookDTO.title());
        bookRepository.save(book);
        return toDTO(book);
    }

    public BookDTO findBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        return toDTO(book);
    }

    public List<BookDTO> findAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        book.setTitle(bookDTO.title());
        book.setAuthor(bookDTO.author());
        bookRepository.save(book);
        return toDTO(book);
    }

    public void deleteBookById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException();
        }
        bookRepository.deleteById(id);
    }
}
