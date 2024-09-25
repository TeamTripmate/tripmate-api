package com.tripmate.api.service;

import com.tripmate.api.domain.CompanionStatus;
import com.tripmate.api.domain.MatchingStatus;
import com.tripmate.api.domain.user.TripmateCharacterType;
import com.tripmate.api.entity.CompanionEntity;
import com.tripmate.api.entity.CompanionRepository;
import com.tripmate.api.entity.CompanionReviewRepository;
import com.tripmate.api.entity.CompanionUserEntity;
import com.tripmate.api.entity.CompanionUserRepository;
import com.tripmate.api.entity.TripStyleEntity;
import com.tripmate.api.entity.TripStyleRepository;
import com.tripmate.api.entity.UserEntity;
import com.tripmate.api.entity.UserRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DemoService {

    private final CompanionRepository companionRepository;
    private final CompanionUserRepository companionUserRepository;
    private final UserRepository userRepository;
    private final CompanionReviewRepository companionReviewRepository;
    private final TripStyleRepository tripStyleRepository;


    public Map<String, Long> doEntitySettings() {

        TripStyleEntity tripStyle = TripStyleEntity.builder()
            .styleName("자유여행")  // 스타일 이름
            .keyword1("모험")  // 키워드 1
            .keyword2("즉흥")  // 키워드 2
            .keyword3("여유")  // 키워드 3
            .build();
        TripStyleEntity tse = tripStyleRepository.save(tripStyle);

        UserEntity user1 = UserEntity.builder()
            .kakaoId(123456789L)  // 카카오 ID
            .nickname("여행자123")  // 닉네임
            .profileImage("https://example.com/profile.jpg")  // 프로필 이미지
            .thumbnailImage("https://example.com/thumbnail.jpg")  // 썸네일 이미지
            .gender("남성")  // 성별
            .birthYear("1990")  // 출생년도
            .tripStyleId(tse.getId())  // 여행 스타일 ID
            .characterType(TripmateCharacterType.DOLPHIN.name())  // 캐릭터 유형
            .deleted(false)  // 삭제 여부
            .build();
        UserEntity ue1 = userRepository.save(user1);

        UserEntity user2 = UserEntity.builder()
            .kakaoId(987654321L)  // 카카오 ID
            .nickname("산책러")  // 닉네임
            .profileImage("https://example.com/walker_profile.jpg")  // 프로필 이미지
            .thumbnailImage("https://example.com/walker_thumbnail.jpg")  // 썸네일 이미지
            .gender("여성")  // 성별
            .birthYear("1985")  // 출생년도
            .tripStyleId(2L)  // 여행 스타일 ID
            .characterType(TripmateCharacterType.PANDA.name())  // 캐릭터 유형
            .deleted(true)  // 삭제 여부 (탈퇴된 사용자로 가정)
            .build();
        UserEntity ue2 = userRepository.save(user2);

        CompanionEntity companion = CompanionEntity.builder()
            .spotId(123L)  // 여행지 ID
            .title("재즈바 가실분")  // 제목
            .description("재즈바 가실 분 구합니다.")  // 내용
            .startDate(LocalDateTime.of(2024, 10, 10, 14, 0))  // 동행 시작일
            .companionType("01")  // 동행 유형
            .companionStatus(CompanionStatus.RECRUITING.name())  // 동행 모집 상태
            .openChatLink("https://open.kakao.com/o/exampleLink")  // 오픈채팅 링크
            .hostId(ue1.getKakaoId())  // 호스트 ID
            .sameAgeYn(true)  // 같은 나이 여부
            .sameGenderYn(false)  // 같은 성별 여부
            .build();
        CompanionEntity ce = companionRepository.save(companion);

        CompanionUserEntity companionUser = CompanionUserEntity.builder()
            .userId(ue2.getKakaoId())  // 회원 ID
            .companionId(ce.getId())  // 동행 ID
            .matchingStatus(MatchingStatus.REQUEST.name())  // 매칭 상태
            .reviewYn(false)  // 후기작성여부
            .build();
        CompanionUserEntity cue = companionUserRepository.save(companionUser);

        HashMap<String, Long> hashMap = new HashMap<>();
        hashMap.put("tripStyleId", tse.getId());
        hashMap.put("userId1", ue1.getKakaoId());
        hashMap.put("userId2", ue2.getKakaoId());
        hashMap.put("companionId", ce.getId());
        hashMap.put("companionUserId", cue.getId());

        return hashMap;
    }

}
