package com.dev_camp.checkin

import com.dev_camp.domain.common.CreatedAtEntity
import com.dev_camp.terrace.domain.Terrace
import com.dev_camp.user.domain.User
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "check_in")
class CheckIn(
    @field:Id
    @field:Column(nullable = false, name = "check_in_id")
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "user_id")
    val user: User,

    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "terrace_id")
    val terrace: Terrace,

    @field:Column(nullable = false, name = "time_block_start_at")
    val timeBlockStartAt: LocalDateTime = LocalDateTime.now()
) : CreatedAtEntity()