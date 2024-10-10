package com.example.springtest

import com.example.springtest.entities.Book
import com.example.springtest.repositories.BookRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataInitializer(private val bookRepository: BookRepository) : CommandLineRunner {
    override fun run(vararg args: String?) {
        bookRepository.save(Book(title = "1984", author = "George Orwell", genre = "Dystopia", publicationDate = "1949"))
        bookRepository.save(Book(title = "The Hobbit", author = "J.R.R. Tolkien", genre = "Fantasy", publicationDate = "1937"))
    }
}