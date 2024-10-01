package com.tripmate.api.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripStyleRepository extends JpaRepository<TripStyleEntity, Long> {

    @Query("SELECT ts FROM TripStyleEntity ts "
            + "WHERE ts.keyword1 = :keyword1 AND ts.keyword2 = :keyword2 AND ts.keyword3 = :keyword3")
    Optional<TripStyleEntity> findByKeywords(@Param("keyword1") String keyword1,
                                             @Param("keyword2") String keyword2,
                                             @Param("keyword3") String keyword3);
}
