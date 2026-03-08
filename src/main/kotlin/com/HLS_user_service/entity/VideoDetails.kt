package com.HLS_user_service.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "video_details")
data class VideoDetails(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "path")
    var path: String,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metaData", columnDefinition = "jsonb", nullable = false)
    var metaData: Map<String, Any> = emptyMap(),

    @Column(name = "is_valid")
    var isValid: Boolean = false
): AuditEntity()
