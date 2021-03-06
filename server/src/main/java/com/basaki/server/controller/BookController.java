package com.basaki.server.controller;

import com.basaki.server.data.entity.Book;
import com.basaki.server.model.BookRequest;
import com.basaki.server.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code BookController} exposes book service.
 * <p>
 *
 * @author Indra Basak
 * @since 11/20/17
 */
@RestController
@Slf4j
@Api(value = "Book Service", produces = "application/json", tags = {"1"})
public class BookController {

    private final BookService service;

    @Autowired
    public BookController(BookService service) {
        this.service = service;
    }

    @ApiOperation(value = "Creates a book.", response = Book.class)
    @PostMapping(value = "/books")
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody BookRequest request) {
        return service.create(request);
    }

    @ApiOperation(value = "Retrieves a book.", notes = "Requires book identifier",
            response = Book.class)
    @GetMapping(value = "/books/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Book read(@PathVariable("id") UUID id) {
        return service.read(id);
    }
}
