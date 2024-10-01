package com.tripmate.api.entity;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanionRepository extends JpaRepository<CompanionEntity, Long> {

    List<CompanionEntity> findCompanionEntitiesByHostId(Long hostId);

    List<CompanionEntity> findCompanionEntitiesBySpotId(Long spotId);
}
