package com.dev_camp.reservation.controller

import com.dev_camp.config.annotation.LoggedInUser
import com.dev_camp.reservation.domain.Reservation
import com.dev_camp.reservation.service.AsyncService
import com.dev_camp.reservation.service.ReservationService
import com.dev_camp.user.dto.UserDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/reservation")
class ReservationApiController (
    private val reservationService: ReservationService,
    private val asyncService: AsyncService
) {
    @PostMapping("/{terraceId}")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@PathVariable terraceId: Int, @LoggedInUser userDto: UserDto) : Reservation {
        val created = reservationService.createReservation(terraceId, userDto.id)
        asyncService.waitCheckIn(created.id!!)
        return created
    }
}