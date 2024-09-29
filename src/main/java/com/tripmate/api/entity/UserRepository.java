package com.tripmate.api.entity;

import com.tripmate.api.dto.companion.UserInfo;
import com.tripmate.api.dto.response.MypageUserInfoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // TODO : 개인화 개발 완료되면 LEFT JOIN -> JOIN 변경하기
    @Query(
        "select new com.tripmate.api.dto.response.MypageUserInfoResponse(tse.keyword1, tse.keyword2, tse.keyword3, "
            + "ue.characterType, tse.styleName, ue.nickname, ue.thumbnailImage, ue.profileImage) "
            + "from UserEntity ue "
            + "LEFT JOIN TripStyleEntity tse ON tse.id = ue.tripStyleId "
            + "WHERE ue.kakaoId = :userId"
    )
    MypageUserInfoResponse joinUserEntityAndTripStyleEntity(Long userId);

    // TODO : 개인화 개발 완료되면 LEFT JOIN -> JOIN 변경하기
    @Query(
        "select new com.tripmate.api.dto.companion.UserInfo(ue.thumbnailImage, ue.nickname, "
            + "tse.styleName, ue.characterType) "
            + "from UserEntity ue "
            + "LEFT JOIN TripStyleEntity tse ON tse.id = ue.tripStyleId "
            + "WHERE ue.kakaoId = :userId"
    )
    UserInfo joinUserEntityAndTripStyleEntityForUserInfo(Long userId);

}
