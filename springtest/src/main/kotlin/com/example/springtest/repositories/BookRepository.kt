package com.example.springtest.repositories

import com.example.springtest.entities.Book
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Long>{
    fun findByIsAvailable(isAvailable: Boolean): List<Book>

    fun findByAuthor(author: String): List<Book>

    fun countByGenre(genre: String): Long
}