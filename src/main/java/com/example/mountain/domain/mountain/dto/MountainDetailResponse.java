package com.example.mountain.domain.mountain.dto;

import com.example.mountain.domain.mountain.entity.Mountain;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MountainDetailResponse {
    private Long exploreId;
    private String mountain;
    private String explanation;
    private String imageUrls;
    private int m_height;
    private String m_location;
    private String difficulty;
    private String season;
    private String theme;
    private String xData;
    private String yData;
    private int teamCnt;
    private int reveiwCnt;

    public static MountainDetailResponse from(Mountain mountain){
        return MountainDetailResponse.builder()
                .exploreId(mountain.getId())
                .mountain(mountain.getName())
                .explanation(mountain.getExplanation())
                .imageUrls(mountain.getImg())
                .m_location(mountain.getLocation())
                .m_height(mountain.getHigh())
                .xData(mountain.getLongtitue())
                .yData(mountain.getLattitue())
                .season(mountain.getSeason())
                .theme(mountain.getTheme())
                .difficulty(mountain.getDifficulty())
                .teamCnt(mountain.getTeams().size())
                .reveiwCnt(mountain.getReviews().size())
                .build();
    }

}

