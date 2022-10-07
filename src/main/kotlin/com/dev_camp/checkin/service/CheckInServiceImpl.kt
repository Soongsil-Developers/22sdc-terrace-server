package com.dev_camp.checkin.service

import com.dev_camp.auth.exception.UserIdNotFoundException
import com.dev_camp.checkin.domain.CheckIn
import com.dev_camp.checkin.domain.CheckInRepository
import com.dev_camp.reservation.domain.Reservation
import com.dev_camp.reservation.domain.ReservationRepository
import com.dev_camp.reservation.exception.ReservationOutOfBoundsException
import com.dev_camp.terrace.domain.TerraceRepository
import com.dev_camp.terrace.domain.TerraceStatus
import com.dev_camp.terrace.exception.TerraceIdNotFoundException
import com.dev_camp.terrace.exception.UnavailableTerraceException
import com.dev_camp.user.domain.UserRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CheckInServiceImpl(
    private val checkInRepository: CheckInRepository,
    private val terraceRepository: TerraceRepository,
    private val userRepository: UserRepository,
    private val reservationRepository: ReservationRepository
) : CheckInService {

    @Transactional
    override fun createCheckIn(terraceId: Int, userId: String): CheckIn {

        val terrace = terraceRepository.findById(terraceId).orElseThrow{ TerraceIdNotFoundException() }
        val user= userRepository.findById(userId).orElseThrow { UserIdNotFoundException() }
        val checkIn : CheckIn

        when (terrace.status) {
            TerraceStatus.AVAILABLE -> throw UnavailableTerraceException("예약 후 사용해야 합니다.")
            TerraceStatus.BOOK -> checkIn = checkInRepository.save(CheckIn( terrace =  terrace , user = user ))
            TerraceStatus.CLOSED -> throw UnavailableTerraceException("사용 불가능한 테라스입니다.")
            TerraceStatus.USING -> throw UnavailableTerraceException("사용 중인 테라스입니다.")
        }
        terrace.status = TerraceStatus.USING

        val reservation = matchReservation(checkIn)
        reservation.isCheckedIn=true

        return checkIn
    }

    fun matchReservation(checkIn: CheckIn): Reservation {
        val reservations = reservationRepository.findByUserId(checkIn.user.id)
        if( reservations.size>1 ) {
            throw ReservationOutOfBoundsException()
        }

        return reservations[0]
    }
}