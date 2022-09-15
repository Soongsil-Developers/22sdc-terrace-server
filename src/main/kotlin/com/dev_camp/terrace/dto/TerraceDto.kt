package com.dev_camp.terrace.dto

import com.dev_camp.terrace.domain.TerraceStatus

data class TerraceDto (
    val terrace_id: Int,
    val floor: Int,
    val status: TerraceStatus
)