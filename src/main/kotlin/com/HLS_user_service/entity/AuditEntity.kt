package com.HLS_user_service.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AuditEntity {

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    var createdBy: String? = null

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    var createdDate: LocalDateTime? = null

    @LastModifiedDate
    @Column(name = "last_modified_date")
    var lastModifiedDate: LocalDateTime? = null

    @LastModifiedBy
    @Column(name = "last_modified_by")
    var lastModifiedBy: String? = null
}