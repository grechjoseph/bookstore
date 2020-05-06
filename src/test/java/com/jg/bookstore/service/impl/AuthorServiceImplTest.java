package com.jg.bookstore.service.impl;

import com.jg.bookstore.domain.entity.Author;
import com.jg.bookstore.domain.exception.BaseException;
import com.jg.bookstore.domain.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.jg.bookstore.domain.exception.ErrorCode.AUTHOR_NOT_FOUND;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthorServiceImplTest {

    private static Author AUTHOR = new Author();
    private static UUID ID = AUTHOR.getId();
    private static String FIRST_NAME = "First Name";
    private static String LAST_NAME = "Last Name";

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @BeforeEach
    public void beforeEach() {
        when(authorRepository.save(any(Author.class))).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                return invocation.getArguments()[0];
            }
        });
        when(authorRepository.findByIdAndDeletedFalse(any(UUID.class))).thenReturn(Optional.of(AUTHOR));

        AUTHOR.setFirstName(FIRST_NAME);
        AUTHOR.setLastName(LAST_NAME);
    }

    @Test
    public void createAuthor_validModel_shouldCreateAuthor() {
        final Author result = authorService.createAuthor(AUTHOR);
        verify(authorRepository).save(AUTHOR);
        assertThat(result).isEqualTo(AUTHOR);
    }

    @Test
    public void getAuthorById_authorExist_shouldReturnAuthor() {
        assertThatCode(() -> authorService.getAuthorById(ID)).doesNotThrowAnyException();
        verify(authorRepository).findByIdAndDeletedFalse(ID);
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
        final Author result = authorService.updateAuthor(ID, AUTHOR);
        verify(authorRepository).findByIdAndDeletedFalse(ID);
        verify(authorRepository).save(AUTHOR);
        assertThat(result).isEqualTo(AUTHOR);
    }

    @Test
    public void updateAuthor_authorDoesNotExist_shouldThrowAuthorNotFoundException() {
        when(authorRepository.findByIdAndDeletedFalse(any(UUID.class))).thenReturn(Optional.empty());
        assertThatThrownBy(() -> authorService.updateAuthor(ID, AUTHOR))
                .isEqualTo(new BaseException(AUTHOR_NOT_FOUND));
        verify(authorRepository).findByIdAndDeletedFalse(ID);
        verify(authorRepository, never()).save(AUTHOR);
    }

    @Test
    public void deleteAuthor_idProvided_shouldDelete() {
        assertThat(AUTHOR.isDeleted()).isFalse();
        assertThatCode(() -> authorService.deleteAuthor(ID)).doesNotThrowAnyException();
        verify(authorRepository).findByIdAndDeletedFalse(ID);
        verify(authorRepository).save(AUTHOR);
        assertThat(AUTHOR.isDeleted()).isTrue();
    }

}
