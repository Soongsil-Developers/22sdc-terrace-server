package com.dev_camp.terrace.domain.exception

import com.dev_camp.common.exception.NotAcceptableException

class TerraceClosedException: NotAcceptableException {
    constructor(): super("closed terrace")
}