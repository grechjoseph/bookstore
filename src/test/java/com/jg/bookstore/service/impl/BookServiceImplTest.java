package com.jg.bookstore.service.impl;

import com.jg.bookstore.BaseTestContext;
import com.jg.bookstore.domain.entity.Book;
import com.jg.bookstore.domain.exception.BaseException;
import com.jg.bookstore.domain.repository.BookRepository;
import com.jg.bookstore.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.jg.bookstore.domain.exception.ErrorCode.BOOK_NOT_FOUND;
import static com.jg.bookstore.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookServiceImplTest extends BaseTestContext {

    @Mock
    private AuthorService mockAuthorService;

    @Mock
    private BookRepository mockBookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    public void before() {
        when(mockAuthorService.getAuthorById(AUTHOR_ID)).thenReturn(AUTHOR);
        when(mockBookRepository.findByIdAndDeletedFalse(BOOK_ID)).thenReturn(Optional.of(BOOK));
        when(mockBookRepository.findByDeletedFalse()).thenReturn(List.of(BOOK));
        when(mockBookRepository.findByAuthorIdAndDeletedFalse(AUTHOR_ID)).thenReturn(List.of(BOOK));
        when(mockBookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
    }

    @Test
    public void createBook_validBook_shouldCreateBook() {
        final Book result = bookService.createBook(AUTHOR_ID, BOOK);
        verify(mockAuthorService).getAuthorById(AUTHOR_ID);
        verify(mockBookRepository).save(BOOK);
        assertThat(result).isEqualTo(BOOK);
    }

    @Test
    public void getBookById_bookExists_shouldReturnBook() {
        final Book result = bookService.getBookById(BOOK_ID);
        verify(mockBookRepository).findByIdAndDeletedFalse(BOOK_ID);
        assertThat(result).isEqualTo(BOOK);
    }

    @Test
    public void getBookById_bookDoesNotExist_shouldThrowBookNotFoundException() {
        when(mockBookRepository.findByIdAndDeletedFalse(BOOK_ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.getBookById(BOOK_ID))
            .isEqualTo(new BaseException(BOOK_NOT_FOUND));
        verify(mockBookRepository).findByIdAndDeletedFalse(BOOK_ID);
    }

    @Test
    public void getBooks_withoutAuthorId_shouldReturnAllBooks() {
        final List<Book> result = bookService.getBooks(null);
        verify(mockBookRepository).findByDeletedFalse();
        verify(mockBookRepository, never()).findByAuthorIdAndDeletedFalse(any(UUID.class));
        assertThat(result).contains(BOOK);
    }

    @Test
    public void getBooks_withAuthorId_shouldReturnBooksByAuthorOnly() {
        final List<Book> result = bookService.getBooks(AUTHOR_ID);
        verify(mockBookRepository, never()).findByDeletedFalse();
        verify(mockBookRepository).findByAuthorIdAndDeletedFalse(any(UUID.class));
        assertThat(result).contains(BOOK);
    }

    @Test
    public void updateBook_bookExists_shouldUpdateBook() {
        final Book result = bookService.updateBook(BOOK_ID, BOOK);
        verify(mockBookRepository).findByIdAndDeletedFalse(BOOK_ID);
        verify(mockBookRepository).save(BOOK);
        assertThat(result).isEqualTo(BOOK);
    }

    @Test
    public void updateBook_bookDoesNotExist_shouldThrowBookNotFoundException() {
        when(mockBookRepository.findByIdAndDeletedFalse(BOOK_ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.updateBook(BOOK_ID, BOOK))
            .isEqualTo(new BaseException(BOOK_NOT_FOUND));
        verify(mockBookRepository).findByIdAndDeletedFalse(BOOK_ID);
        verify(mockBookRepository, never()).save(BOOK);
    }

    @Test
    public void deleteBook_bookExists_shouldDeleteBook() {
        assertThat(BOOK.isDeleted()).isFalse();
        assertThatCode(() -> bookService.deleteBook(BOOK_ID)).doesNotThrowAnyException();
        verify(mockBookRepository).findByIdAndDeletedFalse(BOOK_ID);
        verify(mockBookRepository).save(BOOK);
        assertThat(BOOK.isDeleted()).isTrue();
    }

    @Test
    public void deleteBook_bookDoesNotExist_shouldThrowBookNotFoundException() {
        when(mockBookRepository.findByIdAndDeletedFalse(BOOK_ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.deleteBook(BOOK_ID))
                .isEqualTo(new BaseException(BOOK_NOT_FOUND));
        verify(mockBookRepository).findByIdAndDeletedFalse(BOOK_ID);
        verify(mockBookRepository, never()).save(BOOK);
    }

}
