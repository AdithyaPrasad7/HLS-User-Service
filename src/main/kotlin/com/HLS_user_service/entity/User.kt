package com.HLS_user_service.entity

import com.HLS_user_service.dto.response.UserResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = arrayOf("email"))])
data class User(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "first_name")
    var firstName: String,

    @Column(name = "last_name")
    var lastName: String,

    @Column(name = "email", nullable = false)
    var email: String,

    @Column("phone")
    var phone: String,

    @Column("password_hashed")
    var passwordHashed: String,

    @Column("password_salt")
    var passwordSalt: String
): AuditEntity() {
    fun toResponse(): UserResponse {
        return UserResponse(email, firstName, lastName, phone)
    }
}