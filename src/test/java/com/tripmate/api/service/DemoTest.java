package com.tripmate.api.service;

import com.tripmate.api.domain.CompanionStatus;
import com.tripmate.api.domain.MatchingStatus;
import com.tripmate.api.domain.user.Gender;
import com.tripmate.api.domain.user.TripmateCharacterType;
import com.tripmate.api.entity.CompanionEntity;
import com.tripmate.api.entity.CompanionRepository;
import com.tripmate.api.entity.CompanionReviewEntity;
import com.tripmate.api.entity.CompanionReviewRepository;
import com.tripmate.api.entity.CompanionUserEntity;
import com.tripmate.api.entity.CompanionUserRepository;
import com.tripmate.api.entity.TripStyleEntity;
import com.tripmate.api.entity.TripStyleRepository;
import com.tripmate.api.entity.UserEntity;
import com.tripmate.api.entity.UserRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = "spring.profiles.active=local")
@Transactional
class DemoTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TripStyleRepository tripStyleRepository;
    @Autowired
    private CompanionRepository companionRepository;
    @Autowired
    private CompanionReviewRepository companionReviewRepository;
    @Autowired
    private CompanionUserRepository companionUserRepository;

    @Autowired
    private CompanionReviewService companionReviewService;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void init() {
        TripStyleEntity tripStyle = TripStyleEntity.builder()
            .styleName("자유여행")  // 스타일 이름
            .keyword1("모험")  // 키워드 1
            .keyword2("즉흥")  // 키워드 2
            .keyword3("여유")  // 키워드 3
            .build();
        TripStyleEntity tse = tripStyleRepository.save(tripStyle);

        for (int i = 0; i < 10000; i++) {
            UserEntity user1 = UserEntity.builder()
                .kakaoId((long) i)  // 카카오 ID
                .nickname("여행자" + i)  // 닉네임
                .profileImage("https://example.com/profile.jpg")  // 프로필 이미지
                .thumbnailImage("https://example.com/thumbnail.jpg")  // 썸네일 이미지
                .gender(Gender.MALE)  // 성별
                .birthDate("901012")  // 출생년도
                .tripStyleId(tse.getId())  // 여행 스타일 ID
                .characterType(TripmateCharacterType.DOLPHIN)  // 캐릭터 유형
                .mbti("ENFP")
                .deleted(false)  // 삭제 여부
                .build();
            userRepository.save(user1);
        }

        for (int j = 0; j < 20; j++) {
            CompanionEntity companion = CompanionEntity.builder()
                .spotId(123L)  // 여행지 ID
                .title("재즈바 가실분")  // 제목
                .description("재즈바 가실 분 구합니다.")  // 내용
                .startDate(LocalDateTime.of(2024, 10, 10, 14, 0))  // 동행 시작일
                .companionType("01")  // 동행 유형
                .companionStatus(CompanionStatus.RECRUITING.name())  // 동행 모집 상태
                .openChatLink("https://open.kakao.com/o/exampleLink")  // 오픈채팅 링크
                .hostId(1L)  // 호스트 ID
                .sameAgeYn(true)  // 같은 나이 여부
                .sameGenderYn(false)  // 같은 성별 여부
                .build();
            CompanionEntity ce = companionRepository.save(companion);

            for (int i = 0; i < 200; i++) {
                CompanionUserEntity companionUser = CompanionUserEntity.builder()
                    .userId((long) i)  // 회원 ID
                    .companionId(ce.getId())  // 동행 ID
                    .matchingStatus(MatchingStatus.REQUEST.name())  // 매칭 상태
                    .reviewYn(false)  // 후기작성여부
                    .build();
                companionUserRepository.save(companionUser);
            }

            for (int i = 0; i < 200; i++) {
                CompanionReviewEntity cre1 = CompanionReviewEntity.builder()
                    .reviewerId((long) i)
                    .revieweeId(1L)
                    .companionId(ce.getId())
                    .feedback("N1")
                    .isPositive(false)
                    .build();
                companionReviewRepository.save(cre1);
            }
        }

        for (int k = 5; k < 25; k++) {

            for (int j = 0; j < 10; j++) {
                CompanionEntity companion = CompanionEntity.builder()
                    .spotId(12345L)  // 여행지 ID
                    .title("재즈바 가실분" + k)  // 제목
                    .description("재즈바 가실 분 구합니다.")  // 내용
                    .startDate(LocalDateTime.of(2024, 10, 10, 14, 0))  // 동행 시작일
                    .companionType("01")  // 동행 유형
                    .companionStatus(CompanionStatus.RECRUITING.name())  // 동행 모집 상태
                    .openChatLink("https://open.kakao.com/o/exampleLink")  // 오픈채팅 링크
                    .hostId((long) k)  // 호스트 ID
                    .sameAgeYn(true)  // 같은 나이 여부
                    .sameGenderYn(false)  // 같은 성별 여부
                    .build();
                CompanionEntity ce = companionRepository.save(companion);

                for (int i = 0; i < 200; i++) {
                    CompanionUserEntity companionUser = CompanionUserEntity.builder()
                        .userId((long) i)  // 회원 ID
                        .companionId(ce.getId())  // 동행 ID
                        .matchingStatus(MatchingStatus.REQUEST.name())  // 매칭 상태
                        .reviewYn(false)  // 후기작성여부
                        .build();
                    companionUserRepository.save(companionUser);
                }

                for (int i = 0; i < 200; i++) {
                    CompanionReviewEntity cre1 = CompanionReviewEntity.builder()
                        .reviewerId((long) i)
                        .revieweeId(1L)
                        .companionId(ce.getId())
                        .feedback("N1")
                        .isPositive(false)
                        .build();
                    companionReviewRepository.save(cre1);
                }
            }
        }

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void getReviewInfosTest() {
        Instant start = Instant.now();  // 시작 시간 기록

        companionReviewService.getReviewInfos(1L);

        Instant end = Instant.now();  // 종료 시간 기록
        System.out.println("Elapsed Time: " + (end.toEpochMilli() - start.toEpochMilli()) + " ms");
    }
}
