package com.dev_camp.checkin.service

import com.dev_camp.checkin.domain.CheckIn
import com.dev_camp.checkin.dto.CheckInDto

interface CheckInService {
    fun createCheckIn(checkInDto: CheckInDto): CheckIn
}