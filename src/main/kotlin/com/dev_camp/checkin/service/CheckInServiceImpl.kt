package com.dev_camp.checkin.service

import com.dev_camp.checkin.domain.CheckIn
import com.dev_camp.checkin.domain.CheckInRepository
import com.dev_camp.checkin.dto.CheckInDto
import com.dev_camp.terrace.domain.Terrace
import com.dev_camp.user.domain.UserRepository
import com.dev_camp.terrace.domain.TerraceRepository
import com.dev_camp.user.domain.User

class CheckInServiceImpl(
    private val checkInRepository: CheckInRepository,
    private val userRepository: UserRepository,
    private val terraceRepository: TerraceRepository
): CheckInService{
    override fun createCheckIn(checkInDto: CheckInDto): CheckIn {
        val terrace: Terrace=terraceRepository.getById(checkInDto.terraceId)
        val user: User=userRepository.getById(checkInDto.userId)

        return checkInRepository.save(checkInDto.toEntity(user, terrace))
    }
}