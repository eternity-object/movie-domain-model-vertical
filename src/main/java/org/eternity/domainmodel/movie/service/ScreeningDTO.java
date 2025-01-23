package org.eternity.domainmodel.movie.service;

import org.eternity.domainmodel.movie.domain.Screening;

import java.time.LocalDateTime;

public record ScreeningDTO(Long screeningId, Integer sequence, LocalDateTime screeningTime) {
    public ScreeningDTO(Screening screening) {
        this(screening.getId(), screening.getSequence(), screening.getScreeningTime());
    }
}
