package com.evidyaloka.gateway.repositories;

import com.evidyaloka.gateway.models.Need.Nomination;
import com.evidyaloka.gateway.models.enums.NominationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NominationRepository extends JpaRepository<Nomination, UUID> {

    List<Nomination> findAllByNeedId(String needId);

    List<Nomination> findAllByNeedIdAndNominationStatus(String needId, NominationStatus nominationStatus);

    List<Nomination> findAllByNominatedUserId(String nominatedUserId);
}
