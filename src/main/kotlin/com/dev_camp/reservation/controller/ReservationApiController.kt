package com.dev_camp.reservation.controller

import com.dev_camp.reservation.dto.ReservationDto
import com.dev_camp.reservation.service.ReservationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/reservation")
class ReservationApiController (
    private val reservationService: ReservationService
) {
    @PostMapping
    fun create(@RequestBody requestDto: ReservationDto) : ResponseEntity<ReservationDto> {
        reservationService.create(requestDto)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}