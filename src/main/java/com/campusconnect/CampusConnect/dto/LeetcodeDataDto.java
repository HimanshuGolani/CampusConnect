package com.campusconnect.CampusConnect.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeetcodeDataDto {

    private long totalSolved;
    private long easySolved;
    private long mediumSolved;
    private long hardSolved;
    private double acceptanceRate;
    private long ranking;
    private long contributionPoints;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LeetcodeDataDto that = (LeetcodeDataDto) o;
        return totalSolved == that.totalSolved && easySolved == that.easySolved && mediumSolved == that.mediumSolved && hardSolved == that.hardSolved && Double.compare(acceptanceRate, that.acceptanceRate) == 0 && ranking == that.ranking && contributionPoints == that.contributionPoints;
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalSolved, easySolved, mediumSolved, hardSolved, acceptanceRate, ranking, contributionPoints);
    }
}
