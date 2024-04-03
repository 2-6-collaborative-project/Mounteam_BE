package com.example.mountain.domain.curation.dto;

import com.example.mountain.domain.mountain.entity.Mountain;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class SeasonResponse {
    private Long exploredId;
    private String mountain;
    private String imageUrls;
    private String m_location;
    private String m_height;
    private String xData;
    private String yData;
    private int teamCnt; //모임갯수

    public static SeasonResponse from(Mountain mountain, String mountainName){
        return SeasonResponse.builder()
                .exploredId(mountain.getId())
                .mountain(mountainName)
                .imageUrls(mountain.getImg())
                .m_location(mountain.getLocation())
                .m_height(mountain.getHigh())
                .xData(mountain.getLongtitue())
                .yData(mountain.getLattitue())
                .teamCnt(mountain.getTeams().size())
                .build();
    }
}
