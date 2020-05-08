package com.jg.bookstore.api.controller;

import com.jg.bookstore.api.model.book.ApiBook;
import com.jg.bookstore.api.model.book.ApiBookExtended;
import com.jg.bookstore.domain.entity.Book;
import com.jg.bookstore.mapper.ModelMapper;
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
@RequestMapping("/books")
@Api(value = "Manage Books.")
public class BookController {

    private final BookService bookService;
    private final ModelMapper modelMapper;

    @GetMapping("/{bookId}")
    @ApiOperation(value = "Get a Book by its ID.")
    public ApiBookExtended getBookById(@PathVariable final UUID bookId) {
        return modelMapper.map(bookService.getBookById(bookId), ApiBookExtended.class);
    }

    @GetMapping
    @ApiOperation(value = "Get Books.")
    public List<ApiBookExtended> getBooks() {
        return modelMapper.mapAsList(bookService.getBooks(null), ApiBookExtended.class);
    }

    @PutMapping("/{bookId}")
    @ApiOperation(value = "Update a Book.")
    public ApiBookExtended updateBook(@PathVariable final UUID bookId, @RequestBody @Valid final ApiBook newValues) {
        return modelMapper.map(bookService.updateBook(bookId, modelMapper.map(newValues, Book.class)), ApiBookExtended.class);
    }

    @DeleteMapping("/{bookId}")
    @ApiOperation(value = "(Soft) Delete a Book.")
    public void deleteBook(@PathVariable final UUID bookId) {
        bookService.deleteBook(bookId);
    }
}
