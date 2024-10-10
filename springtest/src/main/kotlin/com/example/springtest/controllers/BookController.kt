package com.example.springtest.controllers

import com.example.springtest.Services.BookService
import com.example.springtest.entities.Book
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/books")
class BookController(private val bookService: BookService) {

    @GetMapping
    fun getAllBooks() = bookService.getAllBooks()

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: Long) = bookService.getBookById(id)

    @GetMapping("/available/{isAvailable}")
    fun getBookByAvailability(@PathVariable isAvailable: Boolean) = bookService.getBooksByAvailability(isAvailable)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBook(@RequestBody book: Book) = bookService.createBook(book)

    @PutMapping("/{id}")
    fun updateBook(@PathVariable id: Long, @RequestBody updatedBook: Book) = bookService.updateBook(id, updatedBook)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBook(@PathVariable id: Long) = bookService.deleteBook(id)


    @GetMapping("/count/genre/{genre}")
    fun countBooksByGenre(@PathVariable genre: String) = bookService.countBooksByGenre(genre)

    @GetMapping("/author/{authorName}")
    fun getBooksByAuthor(@PathVariable authorName: String) = bookService.getBooksByAuthor(authorName)

}