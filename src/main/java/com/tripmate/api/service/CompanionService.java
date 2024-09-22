package com.tripmate.api.service;

import com.tripmate.api.dto.request.CollectCompanionRequest;
import com.tripmate.api.dto.response.CompanionInfoResponse;
import com.tripmate.api.entity.CompanionEntity;
import com.tripmate.api.entity.CompanionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompanionService {

    private final ModelMapper modelMapper;
    private final CompanionRepository companionRepository;

    public CompanionInfoResponse getCompanionInfo() {

        return null;
    }

    public void saveCompanionInfo(CollectCompanionRequest collectCompanionRequest) {

        CompanionEntity companionEntity = CompanionEntity.builder()
            .spotId(collectCompanionRequest.spotId())
            .title(collectCompanionRequest.title())
            .description(collectCompanionRequest.description())
            .startDate(collectCompanionRequest.date())
            .companionType(collectCompanionRequest.type())
            .openChatLink(collectCompanionRequest.openChatLink())
            .hostId(collectCompanionRequest.creatorId())
            .build();

//        modelMapper.typeMap(
//                CollectCompanionRequest.class, CompanionEntity.class)
//            .addMappings(mapper -> {
//                mapper.map(CollectCompanionRequest::date, CompanionEntity::setStartDate);
//                mapper.map(CollectCompanionRequest::type, CompanionEntity::setCompanionType);
//                mapper.map(CollectCompanionRequest::creatorId, CompanionEntity::setHostId);
//            });
//        CompanionEntity companionEntity = modelMapper.map(collectCompanionRequest, CompanionEntity.class);

        companionRepository.save(companionEntity);
    }

}
