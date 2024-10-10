package com.example.springtest

import com.example.springtest.Services.BookService
import com.example.springtest.entities.Book
import com.example.springtest.repositories.BookRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import kotlin.test.Test


@SpringBootTest
class UnitTests {

    @Mock
    lateinit var bookRepository: BookRepository

    @InjectMocks
    lateinit var bookService: BookService

    //проверка метода getBookById на возвращение книги по id
    @Test
    fun `test getBookById should return book when found`() {
        val book = Book(id = 1, title = "Test Book", author = "Test Author", genre = "Fiction", publicationDate = "2023")
        `when`(bookRepository.findById(1)).thenReturn(Optional.of(book))

        val foundBook = bookService.getBookById(1)
        assertNotNull(foundBook)
        assertEquals("Test Book", foundBook.title)
    }

    //проверка метода getBookById на выброс исключения при отсутствии нужной книги
    @Test
    fun `test getBookById should throw exception when not found`() {
        `when`(bookRepository.findById(1)).thenReturn(Optional.empty())

        val exception = assertThrows<Exception> { bookService.getBookById(1) }
        assertEquals("Book not found", exception.message)
    }

    //проверка метода createBook на сохранение книги в реп и возвращении обратно
    @Test
    fun `test createBook should save and return book`() {
        val book = Book(title = "Test Book", author = "Test Author", genre = "Fiction", publicationDate = "2023")
        `when`(bookRepository.save(book)).thenReturn(book)

        val createdBook = bookService.createBook(book)
        assertNotNull(createdBook)
        assertEquals("Test Book", createdBook.title)
    }
}