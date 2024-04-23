package com.bookstore.controllers;

import com.bookstore.domain.Book;
import com.bookstore.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookService.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Book>> getBookById(@PathVariable String id) {
        Optional<Book> book = this.bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable String id, @RequestBody Book book) {
        return ResponseEntity.ok("Livro atualizado com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable String id) {
        return ResponseEntity.ok("Livro deletado com sucesso!");
    }
}
