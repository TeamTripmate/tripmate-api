package com.tripmate.api.service;

import com.tripmate.api.dto.response.MyApplyCompanionListResponse;
import com.tripmate.api.dto.response.MyApplyCompanionListResponse.TripHostInfo;
import com.tripmate.api.dto.tripList.MyApplyCompanionListInfo;
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

    private final CompanionUserRepository companionUserRepository;
    private final ModelMapper modelMapper;

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

}
