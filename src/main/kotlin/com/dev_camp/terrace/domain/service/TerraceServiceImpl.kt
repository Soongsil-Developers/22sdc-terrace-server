package com.dev_camp.terrace.service

import com.dev_camp.terrace.domain.domain.Terrace
import com.dev_camp.terrace.domain.domain.TerraceRepository
import com.dev_camp.terrace.dto.TerraceDto
import org.springframework.stereotype.Service

@Service
class TerraceServiceImpl (
    private val terraceRepository: TerraceRepository
) : TerraceService {
    override fun getAllTerrace() : List<TerraceDto> {
        val allTerrace: List<Terrace> = terraceRepository.findAll()
        return allTerrace.map { terrace -> terrace.toDto() }
    }
}