package com.dev_camp.reservation.service

import com.dev_camp.reservation.domain.Reservation
import com.dev_camp.reservation.domain.ReservationRepository
import com.dev_camp.terrace.domain.Terrace
import com.dev_camp.terrace.domain.TerraceRepository
import com.dev_camp.terrace.domain.TerraceStatus
import com.dev_camp.terrace.exception.TerraceIdNotFoundException
import com.dev_camp.terrace.exception.UnavailableTerraceException
import com.dev_camp.user.domain.User
import com.dev_camp.user.domain.UserRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ReservationServiceImpl (
    private val reservationRepository: ReservationRepository,
    private val terraceRepository: TerraceRepository,
    private val userRepository: UserRepository
) : ReservationService {
    @Transactional
    override fun createReservation(terraceId: Int, userId: String) : Reservation {
        val terrace: Terrace = terraceRepository.findById(terraceId).orElseThrow { TerraceIdNotFoundException() }
        val user: User = userRepository.findById(userId).orElseThrow { TerraceIdNotFoundException() }
        val reservation: Reservation

        when (terrace.status) {
            TerraceStatus.AVAILABLE -> reservation = reservationRepository.save(Reservation(null, terrace, user, false))
            TerraceStatus.BOOK -> throw UnavailableTerraceException("이미 예약된 테라스입니다.")
            TerraceStatus.CLOSED -> throw UnavailableTerraceException("사용 불가능한 테라스입니다.")
            TerraceStatus.USING -> throw UnavailableTerraceException("사용 중인 테라스입니다.")
        }

        terrace.status = TerraceStatus.BOOK
        terraceRepository.save(terrace)

        return reservation
    }
}