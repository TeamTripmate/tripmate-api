package com.tripmate.api.service;

import com.tripmate.api.domain.CompanionStatus;
import com.tripmate.api.domain.MatchingStatus;
import com.tripmate.api.dto.companion.HostInfo;
import com.tripmate.api.dto.companion.ReviewInfo;
import com.tripmate.api.dto.companion.UserInfo;
import com.tripmate.api.dto.request.CollectCompanionRequest;
import com.tripmate.api.dto.request.CompanionReviewRequest;
import com.tripmate.api.dto.response.CollectCompanionResponse;
import com.tripmate.api.dto.response.CompanionInfoResponse;
import com.tripmate.api.entity.CompanionEntity;
import com.tripmate.api.entity.CompanionRepository;
import com.tripmate.api.entity.CompanionReviewEntity;
import com.tripmate.api.entity.CompanionReviewRepository;
import com.tripmate.api.entity.CompanionUserEntity;
import com.tripmate.api.entity.CompanionUserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CompanionService {

    private final ModelMapper modelMapper;
    private final CompanionRepository companionRepository;
    private final CompanionReviewRepository companionReviewRepository;
    private final CompanionUserRepository companionUserRepository;

    public CompanionInfoResponse getCompanionInfo(Long companionId) {
        Optional<CompanionEntity> companion = companionRepository.findById(companionId);

        if (companion.isEmpty()) {
            return null;
        }

        // TODO : 테스트 데이터 제거 후 비지니스 로직 생성 필요
        HostInfo hostInfoTest = new HostInfo(
            "https://example.com/profile-image.jpg",
            "JohnDoe",
            "아기 펭귄",
            "PENGUIN",
            Arrays.asList("자유여행", "모험", "즉흥"),
            85
        );

        UserInfo userInfo1 = new UserInfo(
            "https://example.com/user-profile.jpg",
            "JaneDoe",
            "아기 펭귄",
            "PENGUIN"
        );

        // ReviewInfo 객체 생성 (리뷰 정보)
        ReviewInfo reviewInfo = new ReviewInfo(
            userInfo1,  // 리뷰 작성자 정보
            "2024-09-24T14:42:23",  // 리뷰 작성 날짜
            Arrays.asList("P1", "P2"),  // 좋았어요 리스트
            Arrays.asList("N1", "N2")  // 아쉬워요 리스트
        );

        UserInfo userInfo2 = new UserInfo(
            "https://example.com/user-profile.jpg",
            "JaneDoe",
            "아기 펭귄",
            "PENGUIN"
        );

        // ReviewInfo 객체 생성 (리뷰 정보)
        ReviewInfo reviewInfo2 = new ReviewInfo(
            userInfo2,  // 리뷰 작성자 정보
            "2024-09-24T14:42:23",  // 리뷰 작성 날짜
            Arrays.asList("P3", "P4"),  // 좋았어요 리스트
            Arrays.asList("N3", "N4")  // 아쉬워요 리스트
        );

        ArrayList<ReviewInfo> reviewInfos = new ArrayList<>();
        reviewInfos.add(reviewInfo);
        reviewInfos.add(reviewInfo2);

        return CompanionInfoResponse.toResponse(companion.get(), hostInfoTest, reviewInfos,
            Arrays.asList("P1", "N1", "P2"), "남자", "20대");
//        return CompanionInfoResponse.toResponse(companion.get(), null, null, null, null, null);
    }

    public CollectCompanionResponse saveCompanionInfo(CollectCompanionRequest collectCompanionRequest) {

        // 동행 엔티티 생성
        CompanionEntity companionEntity = CompanionEntity.builder()
            .spotId(collectCompanionRequest.spotId())
            .title(collectCompanionRequest.title())
            .description(collectCompanionRequest.description())
            .startDate(collectCompanionRequest.date())
            .companionType(collectCompanionRequest.type())
            .companionStatus(CompanionStatus.RECRUITING.name())
            .openChatLink(collectCompanionRequest.openChatLink())
            .hostId(collectCompanionRequest.creatorId())
            .sameGenderYn(collectCompanionRequest.sameGenderYn())
            .sameAgeYn(collectCompanionRequest.sameAgeYn())
            .build();

//        modelMapper.typeMap(
//                CollectCompanionRequest.class, CompanionEntity.class)
//            .addMappings(mapper -> {
//                mapper.map(CollectCompanionRequest::date, CompanionEntity::setStartDate);
//                mapper.map(CollectCompanionRequest::type, CompanionEntity::setCompanionType);
//                mapper.map(CollectCompanionRequest::creatorId, CompanionEntity::setHostId);
//            });
//        CompanionEntity companionEntity = modelMapper.map(collectCompanionRequest, CompanionEntity.class);

        CompanionEntity ce = companionRepository.save(companionEntity);
        CollectCompanionResponse ccr = CollectCompanionResponse.builder()
            .companionId(ce.getId()).build();
        return ccr;
    }

    /**
     * 동행 리뷰 저장 메서드
     *
     * @param userId
     * @param companionReviewRequest
     * TODO: 상대에 대한 리뷰 생성임!!!!! - userId 말고 host 유저 id 가져와서 해당 유저 id로 저장해야함..
     */
    public void saveCompanionReview(Long userId, CompanionReviewRequest companionReviewRequest) {

        Long companionId = companionReviewRequest.companionId();
        List<String> likeList = companionReviewRequest.likeList();
        List<String> badList = companionReviewRequest.badList();

        for (String like : likeList) {
            CompanionReviewEntity cre = CompanionReviewEntity.builder()
                .companionId(companionId)
                .userId(userId)
                .feedback(like)
                .isPositive(true).build();
            companionReviewRepository.save(cre);
        }

        for (String bad : badList) {
            CompanionReviewEntity cre = CompanionReviewEntity.builder()
                .companionId(companionId)
                .userId(userId)
                .feedback(bad)
                .isPositive(false).build();
            companionReviewRepository.save(cre);
        }
    }

    public void saveCompanionApply(Long companionId, Long userId) {

        CompanionUserEntity companionUserEntity = CompanionUserEntity.builder()
            .companionId(companionId)
            .userId(userId)
            .matchingStatus(MatchingStatus.REQUEST.name())
            .reviewYn(false)
            .build();

        companionUserRepository.save(companionUserEntity);
    }

}
