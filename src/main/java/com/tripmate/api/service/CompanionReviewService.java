package com.tripmate.api.service;

import com.tripmate.api.dto.companion.ReviewInfo;
import com.tripmate.api.dto.companion.ReviewResult;
import com.tripmate.api.dto.companion.UserInfo;
import com.tripmate.api.entity.CompanionEntity;
import com.tripmate.api.entity.CompanionRepository;
import com.tripmate.api.entity.CompanionReviewEntity;
import com.tripmate.api.entity.CompanionReviewRepository;
import com.tripmate.api.entity.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CompanionReviewService {

    private final CompanionReviewRepository companionReviewRepository;
    private final CompanionRepository companionRepository;
    private final UserRepository userRepository;

    /**
     * 호스트에 대한 모든 동행 리뷰 가져오는 메서드
     *
     * @param hostId FLOW
     *               동행 엔티티에서 hostID로 모든 동행 엔티티 가져옴
     *               동행후기 엔티티에서 동행ID 존재 여부 확인
     *               동행후기 엔티티에서 listList, badList 생성 및 reviewRank 계산
     *               <p>
     *               reviewInfo, reviewRank 생성
     */
    public ReviewResult getReviewInfos(Long hostId) {

        List<CompanionEntity> companionEntities = companionRepository.findCompanionEntitiesByHostId(hostId);

        List<ReviewInfo> reviewInfos = new ArrayList<>();
        HashMap<String, Integer> reviewRanks = new HashMap<>();

        for (CompanionEntity companionEntity : companionEntities) {

            List<CompanionReviewEntity> companionReviewEntities = companionReviewRepository.findCompanionReviewEntitiesByCompanionId(
                companionEntity.getId());

            if (companionReviewEntities.isEmpty()) {
                continue;
            }

            ArrayList<String> likeList = new ArrayList<>();
            ArrayList<String> badList = new ArrayList<>();
            Long reviewerId = null;
            LocalDateTime reviewDate = null;

            for (CompanionReviewEntity companionReviewEntity : companionReviewEntities) {

                String feedback = companionReviewEntity.getFeedback();
                reviewRanks.put(feedback, reviewRanks.getOrDefault(feedback, 0) + 1);
                if (reviewerId == null) {
                    reviewerId = companionReviewEntity.getReviewerId();
                    reviewDate = companionReviewEntity.getCreatedAt();
                }
                if (companionReviewEntity.isPositive()) {
                    likeList.add(feedback);
                } else {
                    badList.add(feedback);
                }
            }

            // UserInfo 만들기
            UserInfo userInfo = userRepository.joinUserEntityAndTripStyleEntityForUserInfo(reviewerId);
            // ReviewInfo 만들기
            ReviewInfo reviewInfo = ReviewInfo.builder()
                .userInfo(userInfo)
                .reviewDate(reviewDate)
                .likeList(likeList)
                .badList(badList).build();
            // HashMap으로 ReviewInfo & ReviewRank 담기
            reviewInfos.add(reviewInfo);
        }

        List<String> reviewRankList = reviewRanks.entrySet().stream()
            .sorted(Entry.<String, Integer>comparingByValue().reversed())
            .limit(3)
            .map(Entry::getKey)
            .collect(Collectors.toList());

        return ReviewResult.builder().reviewInfos(reviewInfos).reviewRankList(reviewRankList).build();
    }

}
