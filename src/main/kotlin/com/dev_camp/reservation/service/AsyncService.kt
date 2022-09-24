package com.dev_camp.reservation.service

interface AsyncService {
    fun waitCheckIn(reservationId: Int)
}