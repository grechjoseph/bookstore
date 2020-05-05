package com.jg.bookstore.api.controller;

import com.jg.bookstore.api.dto.author.ApiAuthor;
import com.jg.bookstore.api.dto.author.ApiAuthorExtended;
import com.jg.bookstore.api.dto.book.ApiBook;
import com.jg.bookstore.api.dto.book.ApiBookExtended;
import com.jg.bookstore.domain.entity.Author;
import com.jg.bookstore.domain.entity.Book;
import com.jg.bookstore.mapper.ModelMapper;
import com.jg.bookstore.service.AuthorService;
import com.jg.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final BookService bookService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ApiAuthorExtended createAuthor(@RequestBody final ApiAuthor author) {
        return modelMapper.map(authorService.createAuthor(modelMapper.map(author, Author.class)), ApiAuthorExtended.class);
    }

    @GetMapping("/{authorId}")
    public ApiAuthorExtended getAuthorById(@PathVariable final UUID authorId) {
        return modelMapper.map(authorService.getAuthorById(authorId), ApiAuthorExtended.class);
    }

    @GetMapping
    public List<ApiAuthorExtended> getAuthors() {
        return modelMapper.mapAsList(authorService.getAuthors(), ApiAuthorExtended.class);
    }

    @PutMapping("/{authorId}")
    public ApiAuthorExtended updateAuthor(@PathVariable final UUID authorId, @RequestBody final ApiAuthor newValues) {
        return modelMapper.map(authorService.updateAuthor(authorId, modelMapper.map(newValues, Author.class)), ApiAuthorExtended.class);
    }

    @DeleteMapping("/{authorId}")
    public void deleteAuthor(@PathVariable final UUID authorId) {
        authorService.deleteAuthor(authorId);
    }


    @GetMapping("/{authorId}/books")
    public List<ApiBookExtended> getAuthorBooks(@PathVariable final UUID authorId) {
        return modelMapper.mapAsList(bookService.getBooks(authorId), ApiBookExtended.class);
    }

    @PostMapping("/{authorId}/books")
    public ApiBookExtended createAuthorBook(@PathVariable final UUID authorId, @RequestBody final ApiBook book) {
        return modelMapper.map(bookService.createBook(authorId, modelMapper.map(book, Book.class)), ApiBookExtended.class);
    }
}
