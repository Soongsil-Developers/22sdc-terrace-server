package com.dev_camp.terrace.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TerraceRepository : JpaRepository<Terrace, Int> {
    override fun findAll(): List<Terrace>
}