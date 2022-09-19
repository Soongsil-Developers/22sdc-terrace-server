package com.dev_camp.reservation.service

import com.dev_camp.reservation.domain.Reservation
import com.dev_camp.reservation.domain.ReservationRepository
import com.dev_camp.terrace.domain.Terrace
import com.dev_camp.terrace.domain.TerraceRepository
import com.dev_camp.terrace.domain.TerraceStatus
import com.dev_camp.terrace.exception.TerraceIdNotFoundException
import com.dev_camp.terrace.exception.UnavailableTerraceException
import com.dev_camp.user.domain.User
import org.springframework.stereotype.Service

@Service
class ReservationServiceImpl (
    private val reservationRepository: ReservationRepository,
    private val terraceRepository: TerraceRepository
) : ReservationService {
    override fun createReservation(terraceId: Int, user: User) : Reservation {
        val terrace: Terrace = terraceRepository.findById(terraceId).orElseThrow { TerraceIdNotFoundException() }

        return when (terrace.status) {
            TerraceStatus.AVAILABLE -> return reservationRepository.save(Reservation(null, terrace, user))
            TerraceStatus.BOOK -> throw UnavailableTerraceException("이미 예약된 테라스입니다.")
            TerraceStatus.CLOSED -> throw UnavailableTerraceException("사용 불가능한 테라스입니다.")
            TerraceStatus.USING -> throw UnavailableTerraceException("사용 중인 테라스입니다.")
        }
    }
}