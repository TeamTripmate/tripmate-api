package com.tripmate.api.entity;

import com.tripmate.api.dto.companion.UserInfo;
import com.tripmate.api.dto.response.MypageUserInfoResponse;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(
        "select new com.tripmate.api.dto.response.MypageUserInfoResponse(tse.keyword1, tse.keyword2, tse.keyword3, "
            + "ue.characterType, tse.styleName, ue.nickname, ue.thumbnailImage, ue.profileImage) "
            + "from UserEntity ue "
            + "JOIN TripStyleEntity tse ON tse.id = ue.tripStyleId "
            + "WHERE ue.kakaoId = :userId"
    )
    MypageUserInfoResponse joinUserEntityAndTripStyleEntity(Long userId);

    @Query(
        "select new com.tripmate.api.dto.companion.UserInfo(ue.thumbnailImage, ue.nickname, "
            + "tse.styleName, ue.characterType) "
            + "from UserEntity ue "
            + "JOIN TripStyleEntity tse ON tse.id = ue.tripStyleId "
            + "WHERE ue.kakaoId = :userId"
    )
    Optional<UserInfo> joinUserEntityAndTripStyleEntityForUserInfo(Long userId);

}
