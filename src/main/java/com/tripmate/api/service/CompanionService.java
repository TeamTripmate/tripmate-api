package com.tripmate.api.service;

import com.tripmate.api.dto.request.CollectCompanionRequest;
import com.tripmate.api.dto.request.CompanionReviewRequest;
import com.tripmate.api.dto.response.CollectCompanionResponse;
import com.tripmate.api.dto.response.CompanionInfoResponse;
import com.tripmate.api.entity.CompanionEntity;
import com.tripmate.api.entity.CompanionRepository;
import com.tripmate.api.entity.CompanionReviewEntity;
import com.tripmate.api.entity.CompanionReviewRepository;
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

    public CompanionInfoResponse getCompanionInfo(Long companionId) {
        Optional<CompanionEntity> companion = companionRepository.findById(companionId);

        if (companion.isEmpty()) {
            return null;
        }
        return CompanionInfoResponse.toResponse(companion.get(), null, null, null, null, null);
    }

    public CollectCompanionResponse saveCompanionInfo(CollectCompanionRequest collectCompanionRequest) {

        // 동행 엔티티 생성
        CompanionEntity companionEntity = CompanionEntity.builder()
            .spotId(collectCompanionRequest.spotId())
            .title(collectCompanionRequest.title())
            .description(collectCompanionRequest.description())
            .startDate(collectCompanionRequest.date())
            .companionType(collectCompanionRequest.type())
            .openChatLink(collectCompanionRequest.openChatLink())
            .hostId(collectCompanionRequest.creatorId())
            .sameGenderYn(collectCompanionRequest.sameGenderYn())
            .sameAgeYn(collectCompanionRequest.sameAgeYn())
            .build();

        //
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

}
