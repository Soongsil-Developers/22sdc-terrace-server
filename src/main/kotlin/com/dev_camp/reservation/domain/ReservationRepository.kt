package com.dev_camp.reservation.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ReservationRepository : JpaRepository<Reservation, Int> {
    fun findAllByOrderByCreatedAtDesc(): List<Reservation>
    @Query(value="select b from reservation b where reservation.user.user_id=?1")
    fun findByUserId(user_id: String): List<Reservation>
}