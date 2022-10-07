package com.dev_camp.checkin.service

import com.dev_camp.checkin.domain.CheckIn

interface CheckInService {

    fun createCheckIn(terraceId: Int, userId: String): CheckIn
}