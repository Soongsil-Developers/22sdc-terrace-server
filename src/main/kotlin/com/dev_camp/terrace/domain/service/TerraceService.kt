package com.dev_camp.terrace.service

import com.dev_camp.terrace.dto.TerraceDto

interface TerraceService {
    fun getAllTerrace() : List<TerraceDto>

    //terraceStatus를 Using으로 변경
    //fun checkInTerrace(): Int
}