package com.example.springtest

import com.example.springtest.repositories.LoanRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class LoanScheduler(private val loanRepository: LoanRepository) {

    @Scheduled(cron = "0 0 0 * * ?")
    fun checkOverdueLoans() {
        val overdueLoans = loanRepository.findAll().filter {
            it.returnDate == null && it.loanDate.isBefore(LocalDateTime.now().minusDays(30))
        }
        println("${overdueLoans.size} overdue loans found.")
    }
}