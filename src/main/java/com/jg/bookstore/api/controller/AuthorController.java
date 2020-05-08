package com.jg.bookstore.api.controller;

import com.jg.bookstore.api.model.author.ApiAuthor;
import com.jg.bookstore.api.model.author.ApiAuthorExtended;
import com.jg.bookstore.api.model.book.ApiBook;
import com.jg.bookstore.api.model.book.ApiBookExtended;
import com.jg.bookstore.domain.entity.Author;
import com.jg.bookstore.domain.entity.Book;
import com.jg.bookstore.mapper.ModelMapper;
import com.jg.bookstore.service.AuthorService;
import com.jg.bookstore.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
@Api(value = "Manage Authors.")
public class AuthorController {

    private final AuthorService authorService;
    private final BookService bookService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ApiOperation(value = "Create an Author.")
    public ApiAuthorExtended createAuthor(@RequestBody @Valid final ApiAuthor author) {
        return modelMapper.map(authorService.createAuthor(modelMapper.map(author, Author.class)), ApiAuthorExtended.class);
    }

    @GetMapping("/{authorId}")
    @ApiOperation(value = "Get an Author by ID.")
    public ApiAuthorExtended getAuthorById(@PathVariable final UUID authorId) {
        return modelMapper.map(authorService.getAuthorById(authorId), ApiAuthorExtended.class);
    }

    @GetMapping
    @ApiOperation(value = "Get Authors.")
    public List<ApiAuthorExtended> getAuthors() {
        return modelMapper.mapAsList(authorService.getAuthors(), ApiAuthorExtended.class);
    }

    @PutMapping("/{authorId}")
    @ApiOperation(value = "Update an Author.")
    public ApiAuthorExtended updateAuthor(@PathVariable final UUID authorId, @RequestBody @Valid final ApiAuthor newValues) {
        return modelMapper.map(authorService.updateAuthor(authorId, modelMapper.map(newValues, Author.class)), ApiAuthorExtended.class);
    }

    @DeleteMapping("/{authorId}")
    @ApiOperation(value = "(Soft) Delete an Author.")
    public void deleteAuthor(@PathVariable final UUID authorId) {
        authorService.deleteAuthor(authorId);
    }


    @GetMapping("/{authorId}/books")
    @ApiOperation(value = "Get an Author's Books.")
    public List<ApiBookExtended> getAuthorBooks(@PathVariable final UUID authorId) {
        return modelMapper.mapAsList(bookService.getBooks(authorId), ApiBookExtended.class);
    }

    @PostMapping("/{authorId}/books")
    @ApiOperation(value = "Add a Book to an Author's collection.")
    public ApiBookExtended createAuthorBook(@PathVariable final UUID authorId, @RequestBody @Valid final ApiBook book) {
        return modelMapper.map(bookService.createBook(authorId, modelMapper.map(book, Book.class)), ApiBookExtended.class);
    }
}
