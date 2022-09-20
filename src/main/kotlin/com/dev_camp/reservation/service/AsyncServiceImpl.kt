package com.dev_camp.reservation.service

import com.dev_camp.reservation.domain.ReservationRepository
import com.dev_camp.terrace.domain.Terrace
import com.dev_camp.terrace.domain.TerraceRepository
import com.dev_camp.terrace.domain.TerraceStatus
import com.dev_camp.terrace.exception.TerraceIdNotFoundException
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class AsyncServiceImpl(
    private val terraceRepository: TerraceRepository,
    private val reservationRepository: ReservationRepository
) : AsyncService {
    @Async("reservationTaskExecutor")
    @Transactional
    override fun waitCheckIn(reservationId: Int) {
        val minutes = 10

        for (i in 1..minutes) {
            Thread.sleep(1 * 60 * 1000)

            val reservation = reservationRepository.findById(reservationId).orElse(null)

            if (reservation == null) //예약 취소된 경우
                return

            if (reservation.isCheckedIn) // 체크인 된 경우
                return
        }

        val reservation = reservationRepository.findById(reservationId).orElseThrow { TerraceIdNotFoundException() }
        val terrace  = terraceRepository.findById(reservation.terrace.id).orElseThrow { TerraceIdNotFoundException() }
        reservationRepository.delete(reservation)
        terrace.status = TerraceStatus.AVAILABLE
        terraceRepository.save(terrace)
    }
}