package com.dev_camp.terrace.service

import com.dev_camp.terrace.dto.TerraceDto

interface TerraceService {
    fun getAllTerrace() : List<TerraceDto>
}