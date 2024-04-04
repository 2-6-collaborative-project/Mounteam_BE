package com.example.mountain.domain.mountain.dto;

import com.example.mountain.domain.mountain.entity.Mountain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MountainScrollResponse {
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
    private boolean hasNext;

    public static List<MountainScrollResponse> from(List<Mountain> mountains, boolean hasNext) {
        return mountains.stream()
                .map(mountain -> MountainScrollResponse.builder()
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
                        .hasNext(hasNext)
                        .build()
                ).collect(Collectors.toList());
    }
}
