package com.dev_camp.terrace.domain.exception

import com.dev_camp.common.exception.NotAcceptableException

class TerraceUsingException: NotAcceptableException {
    constructor(): super("already using terrace")
}