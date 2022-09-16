package com.dev_camp.terrace.controller

import com.dev_camp.terrace.dto.TerraceDto
import com.dev_camp.terrace.service.TerraceService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/terrace")
class TerraceApiController (
    private val terraceService: TerraceService
) {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun terraceInfo() : List<TerraceDto> {
        return terraceService.getAllTerrace()
    }
}