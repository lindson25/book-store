package com.bookstore.controllers;

import com.bookstore.domain.Book;
import com.bookstore.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
@Tag(name = "Book Store", description = "Operações CRUD para gerenciamento de uma livraria")
public class BookController {

    @Autowired
    private final BookService bookService;

    @Operation(summary = "Criar um livro", description = "Cria um livro de acordo com os parâmetros enviados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    @PostMapping
    public ResponseEntity<Book> createBook(
            @Parameter(description = "Objeto do livro a ser criado", required = true, schema = @Schema(implementation = Book.class))
            @RequestBody Book book) {
        Book createdBook = bookService.createBook(book);
        return ResponseEntity.ok(createdBook);
    }

    @Operation(summary = "Retornar todos os livros", description = "Retorna todos os livros que estão no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livros listados com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))),
    })
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Atualizar um livro", description = "Atualiza um livro já existente através do ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "500", description = "Livro com o id não encontrado"),
            @ApiResponse(responseCode = "404", description = "ID inválido")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @Parameter(description = "ID do livro a ser atualizado", required = true)
            @PathVariable Long id,
            @Parameter(description = "Objeto do livro com as novas informações", required = true, schema = @Schema(implementation = Book.class))
            @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }

    @Operation(summary = "Deletar um livro", description = "Deleta um livro através do ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro deletado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Livro não encontrado"),
            @ApiResponse(responseCode = "404", description = "ID inválido")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(
            @Parameter(description = "ID do livro a ser deletado", required = true)
            @PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }
}
