package com.evidyaloka.gateway.repositories;

import com.evidyaloka.gateway.models.Need.NeedType;
import com.evidyaloka.gateway.models.enums.NeedTypeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NeedTypeRepository extends JpaRepository<NeedType, UUID> {

    Page<NeedType> findAllByStatus(NeedTypeStatus status, Pageable pageable);
}
