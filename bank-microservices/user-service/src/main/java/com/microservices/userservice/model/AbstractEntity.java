package com.microservices.userservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

/**
 * Abstract Entity
 */

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Abstract entity")
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID of the entity", example = "1")
    private Long id;

    @Schema(description = "Created date of the entity", example = "2021-07-01T10:00:00")
    private LocalDateTime createdDate;

    @Schema(description = "Updated date of the entity", example = "2021-07-01T10:00:00")
    private LocalDateTime updatedDate;

    @Schema(description = "Created by", example = "admin")
    private String createdBy;

    @Schema(description = "Updated by", example = "admin")
    private String updatedBy;

    @Schema(description = "Is active", example = "true")
    private boolean isActive;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
        isActive = true;
        createdBy = getCurrentUser();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDateTime.now();
        updatedBy = getCurrentUser();
    }

    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
