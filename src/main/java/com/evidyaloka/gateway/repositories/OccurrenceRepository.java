package com.evidyaloka.gateway.repositories;

import com.evidyaloka.gateway.models.Need.Occurrence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OccurrenceRepository extends JpaRepository<Occurrence, UUID> { }
