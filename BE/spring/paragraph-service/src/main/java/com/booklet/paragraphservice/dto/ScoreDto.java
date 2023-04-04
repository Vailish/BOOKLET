package com.booklet.paragraphservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDto {
    int state;
    Score score;
    static class Score{
        double pos;
        double neg;
    }
}
