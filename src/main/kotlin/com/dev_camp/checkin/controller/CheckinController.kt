package com.dev_camp.checkin.controller

import com.dev_camp.checkin.dto.CheckInDto
import com.dev_camp.checkin.service.CheckInService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/v1")
class CheckinController(
    private val checkInService: CheckInService
) {

    //입실 요청
    @RequestMapping("/checkin/")
    fun createCheckIn(@RequestBody checkInDto: CheckInDto): ResponseEntity<CheckInDto>{
        checkInService.createCheckIn(checkInDto)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}