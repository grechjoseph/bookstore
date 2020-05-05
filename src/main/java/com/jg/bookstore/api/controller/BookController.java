package com.jg.bookstore.api.controller;

import com.jg.bookstore.api.dto.book.ApiBook;
import com.jg.bookstore.api.dto.book.ApiBookExtended;
import com.jg.bookstore.domain.entity.Book;
import com.jg.bookstore.mapper.ModelMapper;
import com.jg.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final ModelMapper modelMapper;

    @GetMapping("/{bookId}")
    public ApiBookExtended getBookById(@PathVariable final UUID bookId) {
        return modelMapper.map(bookService.getBookById(bookId), ApiBookExtended.class);
    }

    @GetMapping
    public List<ApiBookExtended> getBooks() {
        return modelMapper.mapAsList(bookService.getBooks(null), ApiBookExtended.class);
    }

    @PutMapping("/{bookId}")
    public ApiBookExtended updateBook(@PathVariable final UUID bookId, @RequestBody final ApiBook newValues) {
        return modelMapper.map(bookService.updateBook(bookId, modelMapper.map(newValues, Book.class)), ApiBookExtended.class);
    }

    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable final UUID bookId) {
        bookService.deleteBook(bookId);
    }
}
