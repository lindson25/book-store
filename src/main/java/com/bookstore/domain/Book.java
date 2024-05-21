package com.bookstore.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "books")
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Schema(description = "Detalhes sobre o livro")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do livro", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Título do livro", example = "O Pequeno Príncipe")
    private String title;

    @Schema(description = "Nome do autor", example = "Antoine de Saint-Exupéry")
    @Column(nullable = false)
    private String author;
}
