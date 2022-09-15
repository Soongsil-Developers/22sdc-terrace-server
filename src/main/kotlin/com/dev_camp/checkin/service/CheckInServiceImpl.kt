package com.dev_camp.checkin.service

import com.dev_camp.auth.exception.InvalidAccessException
import com.dev_camp.auth.exception.UserIdNotFoundException
import com.dev_camp.checkin.domain.CheckIn
import com.dev_camp.checkin.domain.CheckInRepository
import com.dev_camp.checkin.dto.CheckInDto
import com.dev_camp.terrace.domain.domain.Terrace
import com.dev_camp.user.domain.UserRepository
import com.dev_camp.terrace.domain.domain.TerraceRepository
import com.dev_camp.terrace.domain.domain.TerraceStatus
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

            //terraceStatus에 따른 예외처리
            if (terrace.status== TerraceStatus.AVAILABLE) {
                /*
                terraceStatus Using 으로 변경
                 */
                return checkInRepository.save(checkInDto.toEntity(user, terrace))
            }
            else if(terrace.status== TerraceStatus.BOOK){
                /*
                terraceid를 가진 reservation 레코드의 userid와 비교
                    exception
                    or
                    terraceStatus Using 으로 변경
                 */
                return checkInRepository.save(checkInDto.toEntity(user, terrace))
            }
            else throw InvalidAccessException()
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