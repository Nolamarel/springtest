package com.example.springtest.entities

import jakarta.persistence.*

@Entity
data class Book(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var title: String,
    var author: String,
    var genre: String,
    var publicationDate: String,
    var isAvailable: Boolean = true
)