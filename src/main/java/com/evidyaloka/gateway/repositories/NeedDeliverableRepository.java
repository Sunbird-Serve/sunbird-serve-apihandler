package com.evidyaloka.gateway.repositories;

import com.evidyaloka.gateway.models.Need.NeedDeliverable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NeedDeliverableRepository extends JpaRepository<NeedDeliverable, UUID> {

    List<NeedDeliverable> findByNeedPlanId(String needPlanId);

}
