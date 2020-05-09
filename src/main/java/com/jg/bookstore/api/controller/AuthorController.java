package com.jg.bookstore.api.controller;

import com.jg.bookstore.api.model.ApiAuthor;
import com.jg.bookstore.api.model.ApiBook;
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
    public ApiAuthor createAuthor(@RequestBody @Valid final ApiAuthor author) {
        return modelMapper.map(authorService.createAuthor(modelMapper.map(author, Author.class)), ApiAuthor.class);
    }

    @GetMapping("/{authorId}")
    @ApiOperation(value = "Get an Author by ID.")
    public ApiAuthor getAuthorById(@PathVariable final UUID authorId) {
        return modelMapper.map(authorService.getAuthorById(authorId), ApiAuthor.class);
    }

    @GetMapping
    @ApiOperation(value = "Get Authors.")
    public List<ApiAuthor> getAuthors() {
        return modelMapper.mapAsList(authorService.getAuthors(), ApiAuthor.class);
    }

    @PutMapping("/{authorId}")
    @ApiOperation(value = "Update an Author.")
    public ApiAuthor updateAuthor(@PathVariable final UUID authorId, @RequestBody @Valid final ApiAuthor newValues) {
        return modelMapper.map(authorService.updateAuthor(authorId, modelMapper.map(newValues, Author.class)), ApiAuthor.class);
    }

    @DeleteMapping("/{authorId}")
    @ApiOperation(value = "(Soft) Delete an Author.")
    public void deleteAuthor(@PathVariable final UUID authorId) {
        authorService.deleteAuthor(authorId);
    }


    @GetMapping("/{authorId}/books")
    @ApiOperation(value = "Get an Author's Books.")
    public List<ApiBook> getAuthorBooks(@PathVariable final UUID authorId) {
        return modelMapper.mapAsList(bookService.getBooks(authorId), ApiBook.class);
    }

    @PostMapping("/{authorId}/books")
    @ApiOperation(value = "Add a Book to an Author's collection.")
    public ApiBook createAuthorBook(@PathVariable final UUID authorId, @RequestBody @Valid final ApiBook book) {
        return modelMapper.map(bookService.createBook(authorId, modelMapper.map(book, Book.class)), ApiBook.class);
    }
}
