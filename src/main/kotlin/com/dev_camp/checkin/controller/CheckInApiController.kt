package com.dev_camp.checkin.controller

import com.dev_camp.checkin.dto.CheckInResponseDto
import com.dev_camp.checkin.service.CheckInService
import com.dev_camp.config.annotation.LoggedInUser
import com.dev_camp.user.dto.UserDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus

class CheckInApiController(
    private val checkInService: CheckInService
) {

    @PostMapping("/v1/checkin/{terraceid}")
    @ResponseStatus(HttpStatus.CREATED)
    fun createCheckIn(@PathVariable terraceId: Int, @LoggedInUser userDto: UserDto): CheckInResponseDto {
        val created = checkInService.createCheckIn(terraceId, userDto.id)
        return CheckInResponseDto(created.id)
    }
}