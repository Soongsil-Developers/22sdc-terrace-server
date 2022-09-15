package com.dev_camp.terrace.exception

import com.dev_camp.common.exception.ForbiddenException

class UnavailableTerraceException : ForbiddenException {
    constructor(message: String) : super(message)
    constructor() : super("해당 테라스는 예약할 수 없습니다.")
}