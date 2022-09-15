package com.dev_camp.terrace.domain.domain

import com.dev_camp.terrace.dto.TerraceDto
import javax.persistence.*

@Entity
@Table(name = "terrace")
class Terrace(
    @field:Id
    @field:Column(nullable = false, name = "terrace_id")
    val id: Int = 0,

    @field:Column(nullable = false, name = "floor")
    val floor: Int = 0,

    @field:Column(nullable = false, name = "status")
    @field:Enumerated(value = EnumType.STRING)
    val status: TerraceStatus = TerraceStatus.AVAILABLE
){
    fun toDto(): TerraceDto {
        return TerraceDto(id, floor, status)
    }
}