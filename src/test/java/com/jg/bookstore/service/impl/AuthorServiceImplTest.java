package com.jg.bookstore.service.impl;

import com.jg.bookstore.BaseTestContext;
import com.jg.bookstore.domain.entity.Author;
import com.jg.bookstore.domain.exception.BaseException;
import com.jg.bookstore.domain.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.jg.bookstore.domain.exception.ErrorCode.AUTHOR_NOT_FOUND;
import static com.jg.bookstore.utils.TestUtils.AUTHOR;
import static com.jg.bookstore.utils.TestUtils.AUTHOR_ID;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthorServiceImplTest extends BaseTestContext {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @BeforeEach
    public void before() {
        when(authorRepository.save(any(Author.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        when(authorRepository.findByIdAndDeletedFalse(any(UUID.class))).thenReturn(Optional.of(AUTHOR));
    }

    @Test
    public void createAuthor_validModel_shouldCreateAuthor() {
        final Author result = authorService.createAuthor(AUTHOR);
        verify(authorRepository).save(AUTHOR);
        assertThat(result).isEqualTo(AUTHOR);
    }

    @Test
    public void getAuthorById_authorExist_shouldReturnAuthor() {
        assertThatCode(() -> authorService.getAuthorById(AUTHOR_ID)).doesNotThrowAnyException();
        verify(authorRepository).findByIdAndDeletedFalse(AUTHOR_ID);
    }

    @Test
    public void getAuthorById_authorDoesNotExist_shouldThrowAuthorNotFoundException() {
        when(authorRepository.findByIdAndDeletedFalse(any(UUID.class))).thenReturn(Optional.empty());
        assertThatThrownBy(() -> authorService.getAuthorById(UUID.randomUUID()))
            .isEqualTo(new BaseException(AUTHOR_NOT_FOUND));
    }

    @Test
    public void getAuthors_noAuthorsExist_shouldReturnEmptyList() {
        when(authorRepository.findByDeletedFalse()).thenReturn(List.of());
        final List<Author> result = authorService.getAuthors();
        verify(authorRepository).findByDeletedFalse();
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    public void getAuthors_oneAuthorsExist_shouldReturnListWithOne() {
        when(authorRepository.findByDeletedFalse()).thenReturn(List.of(AUTHOR));
        final List<Author> result = authorService.getAuthors();
        verify(authorRepository).findByDeletedFalse();
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void updateAuthor_authorExist_shouldUpdateAuthor() {
        final Author result = authorService.updateAuthor(AUTHOR_ID, AUTHOR);
        verify(authorRepository).findByIdAndDeletedFalse(AUTHOR_ID);
        verify(authorRepository).save(AUTHOR);
        assertThat(result).isEqualTo(AUTHOR);
    }

    @Test
    public void updateAuthor_authorDoesNotExist_shouldThrowAuthorNotFoundException() {
        when(authorRepository.findByIdAndDeletedFalse(any(UUID.class))).thenReturn(Optional.empty());
        assertThatThrownBy(() -> authorService.updateAuthor(AUTHOR_ID, AUTHOR))
                .isEqualTo(new BaseException(AUTHOR_NOT_FOUND));
        verify(authorRepository).findByIdAndDeletedFalse(AUTHOR_ID);
        verify(authorRepository, never()).save(AUTHOR);
    }

    @Test
    public void deleteAuthor_idProvided_shouldDelete() {
        assertThat(AUTHOR.isDeleted()).isFalse();
        assertThatCode(() -> authorService.deleteAuthor(AUTHOR_ID)).doesNotThrowAnyException();
        verify(authorRepository).findByIdAndDeletedFalse(AUTHOR_ID);
        verify(authorRepository).save(AUTHOR);
        assertThat(AUTHOR.isDeleted()).isTrue();
    }

    @Test
    public void deleteAuthor_bookDoesNotExist_shouldThrowBookNotFoundException() {
        when(authorRepository.findByIdAndDeletedFalse(AUTHOR_ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> authorService.deleteAuthor(AUTHOR_ID))
                .isEqualTo(new BaseException(AUTHOR_NOT_FOUND));
        verify(authorRepository).findByIdAndDeletedFalse(AUTHOR_ID);
        verify(authorRepository, never()).save(AUTHOR);
        assertThat(AUTHOR.isDeleted()).isFalse();
    }

}
