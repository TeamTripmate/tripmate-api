package com.tripmate.api.service;

import com.tripmate.api.domain.spot.Address;
import com.tripmate.api.domain.spot.Location;
import com.tripmate.api.domain.spot.SpotType;
import com.tripmate.api.domain.user.TripmateCharacter;
import com.tripmate.api.domain.user.TripmateCharacterType;
import com.tripmate.api.dto.companion.CompanionRecruitInfo;
import com.tripmate.api.dto.response.SpotDetailResponse;
import com.tripmate.api.entity.*;
import com.tripmate.integration.tourapi.dto.response.SpotCommonInfo;
import com.tripmate.integration.tourapi.service.TourApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SpotDetailService {

    private final TourApiService tourApiService;

    private final CompanionRepository companionRepository;

    private final UserRepository userRepository;

    private final TripStyleRepository tripStyleRepository;

    @Transactional(readOnly = true)
    public SpotDetailResponse getSpotDetail(Long spotId) {
        SpotCommonInfo spotCommonInfo = tourApiService.getSpotDetailInfo(spotId);
        List<CompanionRecruitInfo> companionRecruits = this.getCompanionRecruitsBySpot(spotId);

        return SpotDetailResponse.toResponse(
                spotCommonInfo,
                List.of(
                        new TripmateCharacter("인스타 모험자 아기 돌고래", TripmateCharacterType.DOLPHIN),
                        new TripmateCharacter("랜드마크 탐험가 아기 펭귄", TripmateCharacterType.PENGUIN),
                        new TripmateCharacter("인생샷 찍는 인스타 인플루언서 꿀벌", TripmateCharacterType.HONEYBEE)
                ),
                companionRecruits
        );
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    protected List<CompanionRecruitInfo> getCompanionRecruitsBySpot(Long spotId) {
        return companionRepository.findCompanionEntitiesBySpotId(spotId)
                .stream()
                .map(companion -> {
                    UserEntity host = userRepository.findById(companion.getHostId())
                            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다.", null));

                    TripStyleEntity tripStyle = tripStyleRepository.findById(host.getTripStyleId())
                            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 여행스타일 ID입니다", null));

                    return CompanionRecruitInfo.fromCompanion(companion, host, tripStyle);
                })
                .toList();
    }
}
