package com.tripmate.api.service;

import com.tripmate.api.dto.response.MyApplyCompanionListResponse;
import com.tripmate.api.dto.response.MyApplyCompanionListResponse.TripHostInfo;
import com.tripmate.api.dto.response.MyCollectCompanionListResponse;
import com.tripmate.api.dto.response.MyCollectCompanionListResponse.ApplicantInfo;
import com.tripmate.api.dto.tripList.ApplicantPropertyInfo;
import com.tripmate.api.dto.tripList.MyApplyCompanionListInfo;
import com.tripmate.api.entity.CompanionEntity;
import com.tripmate.api.entity.CompanionRepository;
import com.tripmate.api.entity.CompanionUserRepository;
import java.util.ArrayList;
import java.util.List;
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

        List<MyApplyCompanionListInfo> myApplyCompanionListInfos = companionUserRepository.joinCompanionUserEntityAndCompanionEntity(
            userId);

        ArrayList<MyApplyCompanionListResponse> responses = new ArrayList<>();

        for (MyApplyCompanionListInfo myApplyCompanionListInfo : myApplyCompanionListInfos) {
            ArrayList<String> keywords = new ArrayList<>();
            keywords.add(myApplyCompanionListInfo.getKeyword1());
            keywords.add(myApplyCompanionListInfo.getKeyword2());
            keywords.add(myApplyCompanionListInfo.getKeyword3());

            TripHostInfo tripHostInfo = TripHostInfo.builder()
                .selectedKeyword(keywords)
                .tripStyle(myApplyCompanionListInfo.getTripStyle())
                .characterId(myApplyCompanionListInfo.getCharacterId()).build();

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

}
