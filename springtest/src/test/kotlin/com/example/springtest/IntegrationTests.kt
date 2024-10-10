package com.example.springtest

import com.example.springtest.Services.BookService
import com.example.springtest.entities.Book
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*


@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTests {

	@Autowired
	lateinit var bookService: BookService

	@Autowired
	lateinit var mockMvc: MockMvc

	@Autowired
	lateinit var objectMapper: ObjectMapper

	//при создании книги(POST) статус 201
	@Test
	@WithMockUser(username = "admin", roles = ["ADMIN"])
	fun `test createBook returns 201 and the created book`() {
		val book = Book(title = "Test Book", author = "Test Author", genre = "Fiction", publicationDate = "2023")

		mockMvc.perform(
			post("/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(book))
		)
			.andExpect(status().isCreated)
			.andExpect(jsonPath("$.title").value("Test Book"))
			.andExpect(jsonPath("$.author").value("Test Author"))
			.andExpect(jsonPath("$.genre").value("Fiction"))
			.andExpect(jsonPath("$.publicationDate").value("2023"))
	}
	//при получении книги по id(GET) статус 200 и данные корректны
	@Test
	@WithMockUser(username = "admin", roles = ["ADMIN"])
	fun `test getBookById returns 200 and the correct book`() {
		// Создаем тестовую книгу
		val book = Book(title = "1984", author = "George Orwell", genre = "Dystopia", publicationDate = "1949")
		val savedBook = bookService.saveBookToRepository(book)

		mockMvc.perform(
			get("/books/{id}", savedBook.id)
				.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk)
			.andExpect(jsonPath("$.title").value("1984"))
			.andExpect(jsonPath("$.author").value("George Orwell"))
	}
	//при обновлении книги(PUT) статус 200 и обновленные данные корректны
	@Test
	@WithMockUser(username = "admin", roles = ["ADMIN"])
	fun `test updateBook returns 200 and the updated book`() {
		val book = Book(title = "The Hobbit", author = "J.R.R. Tolkien", genre = "Fantasy", publicationDate = "1937")
		val savedBook = bookService.saveBookToRepository(book)

		val updatedBook = Book(title = "The Lord of the Rings", author = "J.R.R. Tolkien", genre = "Fantasy", publicationDate = "1954")

		mockMvc.perform(
			put("/books/{id}", savedBook.id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatedBook))
		)
			.andExpect(status().isOk)
			.andExpect(jsonPath("$.title").value("The Lord of the Rings"))
			.andExpect(jsonPath("$.publicationDate").value("1954"))
	}

	//при удалении книги(DELETE) статус 204
	@Test
	@WithMockUser(username = "admin", roles = ["ADMIN"])
	fun `test deleteBook returns 204`() {
		// Создаем и сохраняем тестовую книгу
		val book = Book(title = "Animal Farm", author = "George Orwell", genre = "Dystopia", publicationDate = "1945")
		val savedBook = bookService.saveBookToRepository(book)

		mockMvc.perform(
			delete("/books/{id}", savedBook.id)
				.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isNoContent)
	}

	//неавторизованные пользователи получают статус 401(unauthorized) при попытке доступа к ресурсу
	@Test
	fun `test access denied for unauthenticated users`() {
		mockMvc.perform(get("/admin/stats"))
			.andExpect(status().isUnauthorized)
	}

	//авторизованные пользователи получают статус 200, им доступен ресурс
	@Test
	@WithMockUser(username = "admin", roles = ["ADMIN"])
	fun `test access granted for admin`() {
		mockMvc.perform(get("/admin/stats"))
			.andExpect(status().isOk)
	}


}
