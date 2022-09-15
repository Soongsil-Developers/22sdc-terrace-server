package com.dev_camp.reservation.domain

import com.dev_camp.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface ReservationRepository : JpaRepository<Reservation, Int> {
    fun findAllByOrderByCreatedAtDesc(): List<Reservation>
}