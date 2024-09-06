package com.bookstore.service;

import com.bookstore.domain.book.Book;
import com.bookstore.domain.book.BookDTO;
import com.bookstore.exceptions.BookNotFoundException;
import com.bookstore.repositories.BookRepository;
import com.bookstore.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateBook() {
        BookDTO bookDTO = new BookDTO("Title", "Author");
        Book book = new Book();
        book.setTitle(bookDTO.title());
        book.setAuthor(bookDTO.author());

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDTO createdBookDTO = bookService.createBook(bookDTO);

        assertEquals(bookDTO.title(), createdBookDTO.title());
        assertEquals(bookDTO.author(), createdBookDTO.author());
        verify(bookRepository, times(2)).save(any(Book.class));
    }

    @Test
    public void testFindBookById() {
        Long id = 1L;
        Book book = new Book();
        book.setTitle("Title");
        book.setAuthor("Author");

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        BookDTO foundBookDTO = bookService.findBookById(id);

        assertEquals(book.getTitle(), foundBookDTO.title());
        assertEquals(book.getAuthor(), foundBookDTO.author());
    }

    @Test
    public void testFindBookById_NotFound() {
        Long id = 1L;

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.findBookById(id));
    }

    @Test
    public void testFindAllBooks() {
        Book book1 = new Book();
        book1.setTitle("Title1");
        book1.setAuthor("Author1");

        Book book2 = new Book();
        book2.setTitle("Title2");
        book2.setAuthor("Author2");

        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        List<BookDTO> booksDTO = bookService.findAllBooks();

        assertEquals(2, booksDTO.size());
        assertEquals(book1.getTitle(), booksDTO.get(0).title());
        assertEquals(book1.getAuthor(), booksDTO.get(0).author());
        assertEquals(book2.getTitle(), booksDTO.get(1).title());
        assertEquals(book2.getAuthor(), booksDTO.get(1).author());
    }

    @Test
    public void testUpdateBook() {
        Long id = 1L;
        BookDTO bookDTO = new BookDTO("UpdatedTitle", "UpdatedAuthor");
        Book book = new Book();
        book.setTitle("Title");
        book.setAuthor("Author");

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDTO updatedBookDTO = bookService.updateBook(id, bookDTO);

        assertEquals(bookDTO.title(), updatedBookDTO.title());
        assertEquals(bookDTO.author(), updatedBookDTO.author());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testUpdateBook_NotFound() {
        Long id = 1L;
        BookDTO bookDTO = new BookDTO("UpdatedTitle", "UpdatedAuthor");

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(id, bookDTO));
    }

    @Test
    public void testDeleteBookById() {
        Long id = 1L;

        when(bookRepository.existsById(id)).thenReturn(true);

        bookService.deleteBookById(id);

        verify(bookRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteBookById_NotFound() {
        Long id = 1L;

        when(bookRepository.existsById(id)).thenReturn(false);

        assertThrows(BookNotFoundException.class, () -> bookService.deleteBookById(id));
    }
}
