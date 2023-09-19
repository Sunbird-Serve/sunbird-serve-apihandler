package com.evidyaloka.gateway.models.Need;

import com.evidyaloka.gateway.models.enums.NeedTypeStatus;
import com.evidyaloka.gateway.models.enums.TaskType;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.lang.String;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class NeedType {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   private UUID id;

   private String description;
   private String userId;
   private String requirementId;
   private String taxonomyId;
   private String onboardingId;
   private String name;

   @Enumerated(EnumType.STRING)
   private NeedTypeStatus status;

   @Enumerated(EnumType.STRING)
   private TaskType taskType;

   @CreationTimestamp
   private Instant createdAt;

   @UpdateTimestamp
   private Instant updatedAt;
}


