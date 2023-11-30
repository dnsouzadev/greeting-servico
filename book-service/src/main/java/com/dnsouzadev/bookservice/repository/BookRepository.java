package com.dnsouzadev.bookservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnsouzadev.bookservice.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
