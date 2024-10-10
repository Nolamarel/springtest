package com.example.springtest.repositories

import com.example.springtest.entities.Loan
import org.springframework.data.jpa.repository.JpaRepository

interface LoanRepository : JpaRepository<Loan, Long>