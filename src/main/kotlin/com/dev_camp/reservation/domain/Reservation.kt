package com.dev_camp.reservation.domain

import com.dev_camp.domain.common.CreatedAtEntity
import com.dev_camp.terrace.domain.Terrace
import javax.persistence.*

@Entity
@Table(name = "reservation")
class Reservation(
    @field:Id
    @field:Column(nullable = false, name = "reservation_id")
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "terrace_id")
    val terrace: Terrace,
) : CreatedAtEntity()