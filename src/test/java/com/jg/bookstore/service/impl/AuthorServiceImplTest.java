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

import java.util.Optional;
import java.util.UUID;

import static com.jg.bookstore.domain.exception.ErrorCode.AUTHOR_NOT_FOUND;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthorServiceImplTest {

    private static UUID ID = UUID.randomUUID();
    private static String FIRST_NAME = "First";
    private static String LAST_NAME = "Last";

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Mock
    private Author author;

    @BeforeEach
    public void beforeEach() {
        when(authorRepository.save(any(Author.class))).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                return invocation.getArguments()[0];
            }
        });
        when(authorRepository.findByIdAndDeletedFalse(any(UUID.class))).thenReturn(Optional.of(author));

        when(author.getFirstName()).thenReturn(FIRST_NAME);
        when(author.getLastName()).thenReturn(LAST_NAME);
        when(author.getId()).thenReturn(ID);
    }

    @Test
    public void createAuthor_validModel_shouldCreateAuthor() {
        final Author result = authorService.createAuthor(this.author);
        verify(authorRepository).save(author);
        assertThat(result).isEqualTo(author);
    }

    @Test
    public void getAuthorById_authorExist_shouldReturnAuthor() {
        assertThatCode(() -> authorService.getAuthorById(UUID.randomUUID())).doesNotThrowAnyException();
    }

    @Test
    public void getAuthorById_authorDoesNotExist_shouldThrowAuthorNotFoundException() {
        when(authorRepository.findByIdAndDeletedFalse(any(UUID.class))).thenReturn(Optional.empty());
        assertThatThrownBy(() -> authorService.getAuthorById(UUID.randomUUID()))
            .isEqualTo(new BaseException(AUTHOR_NOT_FOUND));
    }

    /*
    @Override
    public Author getAuthorById(final UUID authorId) {
        return null;
    }

    @Override
    public List<Author> getAuthors() {
        return null;
    }

    @Override
    public Author updateAuthor(final UUID authorId, final Author newValues) {
        return null;
    }

    @Override
    public void deleteAuthor(final UUID authorId) {

    }
     */
}
