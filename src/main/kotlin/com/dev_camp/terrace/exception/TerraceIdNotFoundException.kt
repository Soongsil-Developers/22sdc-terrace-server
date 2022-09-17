package com.dev_camp.terrace.exception

import com.dev_camp.common.exception.NotFoundException

class TerraceIdNotFoundException : NotFoundException {
    constructor() : super("terraceId is invalid.")
}