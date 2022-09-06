package com.dev_camp.auth.exception

import com.dev_camp.common.exception.NotFoundException

class UserIdNotFoundException : NotFoundException {
    constructor(message: String) : super(message)
    constructor() : super("userId is invalid.")
}
