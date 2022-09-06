package com.dev_camp.user.domain

import com.dev_camp.domain.common.CreatedAtEntity
import com.dev_camp.user.dto.UserDto
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
class User(
    @field:Id
    @field:Column(nullable = false, name = "user_id", length = 8)
    var id: String ,

    @Column(nullable = false, length = 10)
    var name: String,
)