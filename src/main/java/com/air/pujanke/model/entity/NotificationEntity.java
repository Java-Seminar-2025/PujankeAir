package com.air.pujanke.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Notification")
public class NotificationEntity {

    @Id
    @Column(name = "notification_id", nullable = false, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(length = 256)
    private String message;

    @Column(name = "was_seen", nullable = false, insertable = false)
    private Boolean wasSeen;

    @Column(length = 20, nullable = false)
    private String context;

    @Column(name = "generation_timestamp", nullable = false, insertable = false)
    private Instant generationTimestamp;
}
