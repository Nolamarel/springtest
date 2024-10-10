package com.example.springtest.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Loan(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @ManyToOne val book: Book,
    @ManyToOne val user: User,
    val loanDate: LocalDateTime = LocalDateTime.now(),
    var returnDate: LocalDateTime? = null
)
