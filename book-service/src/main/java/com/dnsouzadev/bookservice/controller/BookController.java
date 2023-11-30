package com.dnsouzadev.bookservice.controller;

import java.util.Date;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnsouzadev.bookservice.model.Book;

@RestController
@RequestMapping("/book-service")
public class BookController {

    private Environment environment;

    public BookController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping(value = "/{id}/{currency}")
    public Book findBook(@PathVariable("id") Long id, @PathVariable("currency") String currency) {

        var port = environment.getProperty("local.server.port");

        return new Book(id, "Nigel Poulton" , new Date(), Double.valueOf(50), "Docker Deep Dive" ,currency, port);
    }

}
