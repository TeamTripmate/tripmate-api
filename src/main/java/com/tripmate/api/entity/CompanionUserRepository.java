package com.tripmate.api.entity;

import com.tripmate.api.dto.tripList.MyApplyCompanionListInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanionUserRepository extends JpaRepository<CompanionUserEntity, Long> {

    List<CompanionUserEntity> findCompanionUserEntitiesByUserId(Long userId);

    @Query(
        "select new com.tripmate.api.dto.tripList.MyApplyCompanionListInfo(ce.id, ce.title, ce.startDate, ce.openChatLink, "
            + "cue.reviewYn, cue.matchingStatus, tse.keyword1, tse.keyword2, tse.keyword3, tse.styleName, ue.characterType) "
            + "FROM CompanionUserEntity cue JOIN CompanionEntity ce ON cue.companionId = ce.id "
            + "JOIN UserEntity ue ON ue.kakaoId = ce.hostId "
            + "JOIN TripStyleEntity tse ON tse.id = ue.tripStyleId "
            + "WHERE cue.userId = :userId")
    List<MyApplyCompanionListInfo> joinCompanionUserEntityAndCompanionEntity(Long userId);

}
