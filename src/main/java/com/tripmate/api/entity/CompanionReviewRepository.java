package com.tripmate.api.entity;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanionReviewRepository extends JpaRepository<CompanionReviewEntity, Long> {

    @Query(
        "select distinct cre.reviewerId from CompanionReviewEntity cre where cre.revieweeId = :hostId"
    )
    List<Long> findDistinctReviewerIdByRevieweeId(Long hostId);

    List<CompanionReviewEntity> findCompanionReviewEntitiesByReviewerIdAndRevieweeId(Long reviewerId, Long revieweeId);

    List<CompanionReviewEntity> findCompanionReviewEntitiesByCompanionId(Long companionId);

}
