package com.dnsouzadev.bookservice.controller;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnsouzadev.bookservice.model.Book;
import com.dnsouzadev.bookservice.repository.BookRepository;

@RestController
@RequestMapping("/book-service")
public class BookController {

    private Environment environment;
    private BookRepository repository;

    public BookController(Environment environment, BookRepository repository) {
        this.environment = environment;
        this.repository = repository;
    }

    @GetMapping(value = "/{id}/{currency}")
    public Book findBook(@PathVariable("id") Long id, @PathVariable("currency") String currency) {

        var book = repository.getReferenceById(id);
        if (book == null) throw new RuntimeException("Book not found");

        var port = environment.getProperty("local.server.port");
        book.setEnvironment(port);

        return book;
    }

}
