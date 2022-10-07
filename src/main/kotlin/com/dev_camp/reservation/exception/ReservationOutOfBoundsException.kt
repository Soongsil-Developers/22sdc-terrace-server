package com.dev_camp.reservation.exception

import com.dev_camp.common.exception.NotAcceptableException

class ReservationOutOfBoundsException: NotAcceptableException {

    constructor(message: String) : super(message)

    constructor() : super("예약은 동시에 2개 이상 할 수 없습니다.")
}