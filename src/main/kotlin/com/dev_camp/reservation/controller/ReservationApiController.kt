package com.dev_camp.reservation.controller

import com.dev_camp.config.annotation.LoggedInUser
import com.dev_camp.reservation.domain.Reservation
import com.dev_camp.reservation.service.ReservationService
import com.dev_camp.user.domain.User
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/reservation")
class ReservationApiController (
    private val reservationService: ReservationService
) {
    @PostMapping("/{terraceId}")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@PathVariable terraceId: Int, @LoggedInUser user: User) : Reservation {
        return reservationService.createReservation(terraceId, user)
    }
}