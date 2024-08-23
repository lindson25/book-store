package com.bookstore.exceptions;

import lombok.Getter;

import java.io.Serial;

@Getter
public class BookNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BookNotFoundException() {
        super();
    }
}
