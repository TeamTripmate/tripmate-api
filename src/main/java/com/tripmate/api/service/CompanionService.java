package com.tripmate.api.service;

import com.tripmate.api.domain.CompanionStatus;
import com.tripmate.api.domain.MatchingStatus;
import com.tripmate.api.domain.user.Gender;
import com.tripmate.api.dto.companion.HostInfo;
import com.tripmate.api.dto.companion.ReviewInfo;
import com.tripmate.api.dto.companion.ReviewResult;
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
import com.tripmate.api.entity.TripStyleEntity;
import com.tripmate.api.entity.TripStyleRepository;
import com.tripmate.api.entity.UserEntity;
import com.tripmate.api.entity.UserRepository;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompanionService {

    private final CompanionRepository companionRepository;
    private final CompanionReviewRepository companionReviewRepository;
    private final CompanionUserRepository companionUserRepository;
    private final UserRepository userRepository;
    private final TripStyleRepository tripStyleRepository;
    private final CompanionReviewService companionReviewService;

    @Transactional(readOnly = true)
    public CompanionInfoResponse getCompanionInfo(Long companionId, Long userId) {

        CompanionEntity companionEntity = companionRepository.findById(companionId)
            .orElseThrow(() -> new NoSuchElementException("존재하지않는 동행모집 컨텐츠입니다", null));

        Optional<CompanionUserEntity> companionUserEntity = companionUserRepository.findCompanionUserEntityByCompanionIdAndUserId(
            companionId, userId);

        boolean accompanyYn = false;
        if (companionUserEntity.isPresent()) {
            String matchingStatus = companionUserEntity.get().getMatchingStatus();
            List<String> noLinkList = Arrays.asList(MatchingStatus.REQUEST.name(), MatchingStatus.REJECTED.name(),
                MatchingStatus.CANCELED.name());
            if (!noLinkList.contains(matchingStatus)) {
                accompanyYn = true;
            }
        }

        UserEntity host = userRepository.findById(companionEntity.getHostId())
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다", null));

        TripStyleEntity tripStyleEntity = tripStyleRepository.findById(host.getTripStyleId())
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 여행스타일ID입니다", null));

        // TODO: 매칭 ratio는 개인화 개발 후 변경 필요
        HostInfo hostInfo = HostInfo.builder()
            .profileImage(host.getThumbnailImage())
            .kakaoNickname(host.getNickname())
            .characterName(tripStyleEntity.getStyleName())
            .characterType(host.getCharacterType())
            .selectedKeyword(Arrays.asList(tripStyleEntity.getKeyword1(), tripStyleEntity.getKeyword2(),
                tripStyleEntity.getKeyword3()))
            .matchingRatio(30).build();

        ReviewResult reviewResult = companionReviewService.getReviewInfos(host.getKakaoId());
        List<ReviewInfo> reviewInfos = reviewResult.reviewInfos();
        List<String> reviewRanks = reviewResult.reviewRankList();

        String gender = customGender(companionEntity.isSameGenderYn(), host.getGender());
        String ageRange = customAge(companionEntity.isSameAgeYn(), host.getBirthDate());

        return CompanionInfoResponse.toResponse(companionEntity, hostInfo, reviewInfos, reviewRanks, accompanyYn,
            gender, ageRange);
    }

    @Transactional
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
        return CollectCompanionResponse.builder()
            .companionId(ce.getId()).build();
    }

    /**
     * 동행 리뷰 저장 메서드
     *
     * @param userId
     * @param companionReviewRequest
     */
    @Transactional
    public void saveCompanionReview(Long userId, CompanionReviewRequest companionReviewRequest) {

        Long companionId = companionReviewRequest.companionId();
        List<String> likeList = companionReviewRequest.likeList();
        List<String> badList = companionReviewRequest.badList();
        CompanionEntity companionEntity = companionRepository.findById(companionId)
            .orElseThrow(() -> new NoSuchElementException("존재하지않는 동행모집 컨텐츠입니다", null));

        Long hostId = companionEntity.getHostId();

        for (String like : likeList) {
            CompanionReviewEntity cre = CompanionReviewEntity.builder()
                .companionId(companionId)
                .reviewerId(userId)
                .revieweeId(hostId)
                .feedback(like)
                .isPositive(true).build();
            companionReviewRepository.save(cre);
        }

        for (String bad : badList) {
            CompanionReviewEntity cre = CompanionReviewEntity.builder()
                .companionId(companionId)
                .reviewerId(userId)
                .revieweeId(hostId)
                .feedback(bad)
                .isPositive(false).build();
            companionReviewRepository.save(cre);
        }
    }

    @Transactional
    public void saveCompanionApply(Long companionId, Long userId) {

        CompanionUserEntity companionUserEntity = CompanionUserEntity.builder()
            .companionId(companionId)
            .userId(userId)
            .matchingStatus(MatchingStatus.REQUEST.name())
            .reviewYn(false)
            .build();

        companionUserRepository.save(companionUserEntity);
    }

    /**
     * 호스트 성별에 따라 동행 성별 반환 메서드
     */
    private String customGender(boolean sameGenderYn, Gender hostGender) {
        if (sameGenderYn) {
            return hostGender.getGenderName() + "만";
        }

        return "성별무관";
    }

    /**
     * 호스트 나이에 따라 동행 연령대 반환 메서드
     */
    private String customAge(boolean sameAgeYn, String birthDate) {

        if (sameAgeYn) {
            LocalDate today = LocalDate.now();

            String yearPrefix = (Integer.parseInt(birthDate.substring(0, 2)) >= 40) ? "19" : "20";

            DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate birthLocalDate = LocalDate.parse(yearPrefix + birthDate, yyyyMMdd);

            int age = Period.between(birthLocalDate, today).getYears();
            int ageGroup = (age / 10) * 10;

            return ageGroup + "대만";
        }

        return "연령무관";
    }
}
