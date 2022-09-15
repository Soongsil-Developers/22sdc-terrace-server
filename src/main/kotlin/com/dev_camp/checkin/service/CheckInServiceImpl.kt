package com.dev_camp.checkin.service

import com.dev_camp.auth.exception.InvalidAccessException
import com.dev_camp.auth.exception.UserIdNotFoundException
import com.dev_camp.checkin.domain.CheckIn
import com.dev_camp.checkin.domain.CheckInRepository
import com.dev_camp.checkin.dto.CheckInDto
import com.dev_camp.common.exception.NotFoundException
import com.dev_camp.terrace.domain.Terrace
import com.dev_camp.user.domain.UserRepository
import com.dev_camp.terrace.domain.TerraceRepository
import com.dev_camp.terrace.domain.TerraceStatus
import com.dev_camp.terrace.domain.exception.TerraceIdNotFoundException
import com.dev_camp.user.domain.User
import javax.persistence.EntityNotFoundException

class CheckInServiceImpl(
    private val checkInRepository: CheckInRepository,
    private val userRepository: UserRepository,
    private val terraceRepository: TerraceRepository
): CheckInService{
    override fun createCheckIn(checkInDto: CheckInDto): CheckIn {
        var flag: Boolean=false //EntityNotFoundException의 발생 위치
        try {
            val terrace: Terrace = terraceRepository.getById(checkInDto.terraceId)
            flag=true
            val user: User = userRepository.getById(checkInDto.userId)

            when (terrace.status) { //terraceStatus에 따른 예외처리
                TerraceStatus.AVAILABLE
                    ->return checkInRepository.save(checkInDto.toEntity(user, terrace))
                TerraceStatus.BOOK
                    ->throw InvalidAccessException()
                TerraceStatus.CLOSED
                    ->throw InvalidAccessException()
                TerraceStatus.USING
                    ->throw InvalidAccessException()
            }
        }
        //전달 받은 terraceid나 userid가 db없을경우
        catch(e: EntityNotFoundException){
            if(flag)
                throw UserIdNotFoundException()
            else
                throw TerraceIdNotFoundException()
        }
    }
}