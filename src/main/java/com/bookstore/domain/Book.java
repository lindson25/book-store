package com.bookstore.domain;

import com.bookstore.dto.BookDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "books")
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    public Book(BookDTO book) {
        this.title = book.title();
        this.author = book.author();
    }
}
