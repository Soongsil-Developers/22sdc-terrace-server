package com.dev_camp.reservation.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReservationRepository : JpaRepository<Reservation, Int> {
    fun findAllByOrderByCreatedAtDesc(): List<Reservation>
}