package com.jg.bookstore.service.impl;

import com.jg.bookstore.domain.entity.Author;
import com.jg.bookstore.domain.exception.BaseException;
import com.jg.bookstore.domain.repository.AuthorRepository;
import com.jg.bookstore.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.jg.bookstore.domain.exception.ErrorCode.AUTHOR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author createAuthor(final Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author getAuthorById(final UUID authorId) {
        return authorRepository.findById(authorId).orElseThrow(() -> new BaseException(AUTHOR_NOT_FOUND));
    }

    @Override
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author updateAuthor(final UUID authorId, final Author newValues) {
        getAuthorById(authorId);
        newValues.setId(authorId);
        return authorRepository.save(newValues);
    }

    @Override
    public void deleteAuthor(final UUID authorId) {
        final Author author = getAuthorById(authorId);
        author.setDeleted(true);
        authorRepository.save(author);
    }

}
