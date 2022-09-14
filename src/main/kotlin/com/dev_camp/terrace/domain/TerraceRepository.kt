package com.dev_camp.terrace.domain

import com.dev_camp.reservation.domain.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TerraceRepository : JpaRepository<Terrace, Int> {
}