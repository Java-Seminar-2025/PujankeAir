package com.air.pujanke.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User")
public class UserEntity {

    @Id
    @Column(name = "user_id", insertable = false, updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(length = 20, unique = true, nullable = false)
    private String username;

    @Column(name = "e_mail", length = 25, unique = true, nullable = false)
    private String eMail;

    @Column(name = "password_hash", length = 60, nullable = false)
    private String passwordHash;

    @Column(name = "admin_privileges", nullable = false, insertable = false)
    private Boolean hasAdminPrivileges;

    @Column(name = "enabled", nullable = false, insertable = false)
    private Boolean isEnabled;

    @Column(name = "registration_timestamp", insertable = false)
    private Instant registrationTimestamp;

    @Column(precision = 10, scale = 2, nullable = false, insertable = false)
    private BigDecimal funds;
}
