package com.evidyaloka.gateway.repositories;

import com.evidyaloka.gateway.models.Need.Need;
import com.evidyaloka.gateway.models.enums.NeedStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NeedRepository extends JpaRepository<Need, UUID> {

    Page<Need> findAllByUserIdAndStatus(String userId, NeedStatus status, Pageable pageable);
    Page<Need> findAllByNeedTypeIdAndStatus(String needTypeId, NeedStatus status, Pageable pageable);
}
