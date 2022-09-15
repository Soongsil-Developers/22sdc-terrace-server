package com.dev_camp.checkin.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CheckInRepository : JpaRepository<CheckIn, Int> {
}