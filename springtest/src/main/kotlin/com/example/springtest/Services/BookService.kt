package com.example.springtest.Services

import com.example.springtest.entities.Book
import com.example.springtest.repositories.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(private var bookRepository: BookRepository) {

    fun getAllBooks(): List<Book> = bookRepository.findAll()

    fun getBookById(id: Long): Book = bookRepository.findById(id).orElseThrow{
        Exception("Book not found")
    }

    fun getBooksByAvailability(isAvailable: Boolean):List<Book> = bookRepository.findByIsAvailable(isAvailable)

    fun createBook(book: Book): Book = bookRepository.save(book)

    fun updateBook(id: Long, updatedBook: Book): Book {
        val existingBook = bookRepository.findById(id).orElseThrow { Exception("Book not found") }
        existingBook.title = updatedBook.title
        existingBook.author = updatedBook.author
        existingBook.genre = updatedBook.genre
        existingBook.publicationDate = updatedBook.publicationDate
        existingBook.isAvailable = updatedBook.isAvailable
        return bookRepository.save(existingBook)
    }

    fun deleteBook(id: Long) = bookRepository.deleteById(id)

    fun countBooksByGenre(genre: String): Long {
        return bookRepository.countByGenre(genre)
    }

    fun getBooksByAuthor(authorName: String): List<Book> {
        return bookRepository.findByAuthor(authorName)
    }

    fun saveBookToRepository(book: Book): Book {
        return bookRepository.save(book)
    }


}