package com.dnsouzadev.bookservice.controller;

import java.util.HashMap;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.dnsouzadev.bookservice.model.Book;
import com.dnsouzadev.bookservice.repository.BookRepository;
import com.dnsouzadev.bookservice.response.Cambio;

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

        HashMap<String, String> params = new HashMap<>();
        params.put("amount", book.getPrice().toString());
        params.put("from", "USD");
        params.put("to", currency);

        var response = new RestTemplate()
            .getForEntity("http://localhost:8000/cambio-service/{amount}/{from}/{to}",
                Cambio.class, params);

        var cambio = response.getBody();

        var port = environment.getProperty("local.server.port");
        book.setEnvironment(port);
        book.setPrice(cambio.getConvertedValue());

        return book;
    }

}
