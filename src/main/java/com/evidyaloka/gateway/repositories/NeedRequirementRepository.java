package com.evidyaloka.gateway.repositories;

import com.evidyaloka.gateway.models.Need.NeedRequirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NeedRequirementRepository extends JpaRepository<NeedRequirement, UUID> {
}
