package com.dev_camp.terrace.domain.exception

import com.dev_camp.common.exception.NotAcceptableException

class TerraceReservationException: NotAcceptableException {
    constructor(): super("already reserved terrace")
}