package com.tripmate.api.domain.spot;

public record Location(
        String latitude,
        String longitude,
        Address address
){
}
