package com.jg.bookstore.service.impl;

import com.jg.bookstore.domain.entity.Book;
import com.jg.bookstore.domain.exception.BaseException;
import com.jg.bookstore.domain.repository.BookRepository;
import com.jg.bookstore.service.AuthorService;
import com.jg.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.jg.bookstore.domain.exception.ErrorCode.BOOK_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorService authorService;
    private final BookRepository bookRepository;

    @Override
    public Book createBook(final UUID authorId, final Book book) {
        log.debug("Creating Book [{}]", book.getName());
        book.setAuthor(authorService.getAuthorById(authorId));
        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(final UUID bookId) {
        log.debug("Retrieving Book by ID [{}].", bookId);
        return bookRepository.findByIdAndDeletedFalse(bookId).orElseThrow(() -> new BaseException(BOOK_NOT_FOUND));
    }

    @Override
    public List<Book> getBooks(final UUID authorId) {
        log.debug("Retrieving Books.");
        return authorId == null ? bookRepository.findByDeletedFalse() :
                bookRepository.findByAuthorIdAndDeletedFalse(authorId);
    }

    @Override
    public Book updateBook(final UUID bookId, final Book newValues) {
        log.debug("Updating Book with ID [{}].", bookId);
        newValues.setAuthor(getBookById(bookId).getAuthor());
        newValues.setId(bookId);
        return bookRepository.save(newValues);
    }

    @Override
    public void deleteBook(final UUID bookId) {
        log.debug("Deleting Book with ID [{}].", bookId);
        final Book book = getBookById(bookId);
        book.setDeleted(true);
        bookRepository.save(book);
    }

}
