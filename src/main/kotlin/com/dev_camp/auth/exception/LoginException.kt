package com.dev_camp.auth.exception

import com.dev_camp.common.exception.NotFoundException

class LoginException : NotFoundException {
    constructor() : super("학번 또는 비밀번호가 잘못되었습니다.")
}
