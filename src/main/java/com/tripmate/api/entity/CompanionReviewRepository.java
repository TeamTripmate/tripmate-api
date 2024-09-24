package com.tripmate.api.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanionReviewRepository extends JpaRepository<CompanionReviewEntity, Long> {

}
