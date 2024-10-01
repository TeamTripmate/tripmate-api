package com.tripmate.api.service;

import com.tripmate.api.domain.CompanionStatus;
import com.tripmate.api.domain.MatchingStatus;
import com.tripmate.api.dto.response.MyApplyCompanionListResponse;
import com.tripmate.api.dto.response.MyApplyCompanionListResponse.TripHostInfo;
import com.tripmate.api.dto.response.MyCollectCompanionListResponse;
import com.tripmate.api.dto.response.MyCollectCompanionListResponse.ApplicantInfo;
import com.tripmate.api.dto.tripList.ApplicantPropertyInfo;
import com.tripmate.api.dto.tripList.MyApplyCompanionListInfo;
import com.tripmate.api.entity.CompanionEntity;
import com.tripmate.api.entity.CompanionRepository;
import com.tripmate.api.entity.CompanionUserEntity;
import com.tripmate.api.entity.CompanionUserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class TripListService {

    private final ModelMapper modelMapper;
    private final CompanionUserRepository companionUserRepository;
    private final CompanionRepository companionRepository;

    public List<MyApplyCompanionListResponse> getMyAppliedCompanionList(Long userId) {

        List<MyApplyCompanionListInfo> myApplyCompanionListInfos = companionUserRepository
                .joinCompanionUserEntityAndCompanionEntity(userId);

        ArrayList<MyApplyCompanionListResponse> responses = new ArrayList<>();

        for (MyApplyCompanionListInfo myApplyCompanionListInfo : myApplyCompanionListInfos) {
            ArrayList<String> keywords = new ArrayList<>();
            keywords.add(myApplyCompanionListInfo.getKeyword1());
            keywords.add(myApplyCompanionListInfo.getKeyword2());
            keywords.add(myApplyCompanionListInfo.getKeyword3());

            TripHostInfo tripHostInfo = TripHostInfo.builder()
                .selectedKeyword(keywords)
                .tripStyle(myApplyCompanionListInfo.getTripStyle())
                .characterId(myApplyCompanionListInfo.getCharacterId())
                .build();

            MyApplyCompanionListResponse myApplyCompanionListResponse = modelMapper.map(myApplyCompanionListInfo,
                MyApplyCompanionListResponse.class);
            myApplyCompanionListResponse.setTripHostInfo(tripHostInfo);

            responses.add(myApplyCompanionListResponse);
        }

        return responses;
    }

    public List<MyCollectCompanionListResponse> getMyCollectCompanionList(Long userId) {

        List<MyCollectCompanionListResponse> responses = new ArrayList<>();

        List<CompanionEntity> companionEntities = companionRepository.findCompanionEntitiesByHostId(userId);
        for (CompanionEntity companion : companionEntities) {

            Long companionId = companion.getId();
            List<ApplicantPropertyInfo> propertyInfos = companionUserRepository.joinCompanionUserEntityAndUserEntity(
                companionId);
            List<ApplicantInfo> applicantInfoList = new ArrayList<>();
            for (ApplicantPropertyInfo propertyInfo : propertyInfos) {
                ApplicantInfo applicantInfo = propertyInfo.getApplicantInfoBuild();
                applicantInfoList.add(applicantInfo);
            }

            MyCollectCompanionListResponse response = MyCollectCompanionListResponse.builder()
                .companionId(companion.getId())
                .title(companion.getTitle())
                .date(companion.getStartDate())
                .companionStatus(companion.getCompanionStatus())
                .applicantInfo(applicantInfoList)
                .build();

            responses.add(response);
        }

        return responses;
    }

    public void saveChosenCompanion(Long companionId, Long userId) {

        // 동행 모집 상태 변경
        CompanionEntity companionEntity = companionRepository.findById(companionId)
            .orElseThrow(() -> new NoSuchElementException("존재하지않는 동행모집 컨텐츠입니다", null));
        companionEntity.changeStatus(CompanionStatus.MATCHED.name());
        companionRepository.save(companionEntity);

        // 신청자 매칭 상태 변경
        CompanionUserEntity chosenApplicant = companionUserRepository.findCompanionUserEntityByCompanionIdAndUserId(
            companionId, userId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 동행 신청자입니다"));
        chosenApplicant.changeMatchingStatus(MatchingStatus.ACCEPTED.name());

        List<CompanionUserEntity> companionUserEntities = companionUserRepository.findCompanionUserEntitiesByCompanionId(
            companionId);

        for (CompanionUserEntity entity : companionUserEntities) {

            if (Objects.equals(entity.getUserId(), userId)) {
                continue;
            }
            entity.changeMatchingStatus(MatchingStatus.REJECTED.name());
        }
    }
}
