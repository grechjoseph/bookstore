package com.jg.bookstore.service.impl;

import com.jg.bookstore.domain.entity.Author;
import com.jg.bookstore.domain.exception.BaseException;
import com.jg.bookstore.domain.repository.AuthorRepository;
import com.jg.bookstore.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.jg.bookstore.domain.exception.ErrorCode.AUTHOR_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author createAuthor(final Author author) {
        log.debug("Creating Author.");
        return authorRepository.save(author);
    }

    @Override
    public Author getAuthorById(final UUID authorId) {
        log.debug("Retrieving Author by ID [{}].", authorId);
        return authorRepository.findByIdAndDeletedFalse(authorId).orElseThrow(() -> new BaseException(AUTHOR_NOT_FOUND));
    }

    @Override
    public List<Author> getAuthors() {
        log.debug("Retrieving Authors.");
        return authorRepository.findByDeletedFalse();
    }

    @Override
    public Author updateAuthor(final UUID authorId, final Author newValues) {
        log.debug("Updating Author with ID [{}].", authorId);
        getAuthorById(authorId);
        newValues.setId(authorId);
        return authorRepository.save(newValues);
    }

    @Override
    public void deleteAuthor(final UUID authorId) {
        log.debug("Deleting Author with ID [{}].", authorId);
        final Author author = getAuthorById(authorId);
        author.setDeleted(true);
        authorRepository.save(author);
    }

}
