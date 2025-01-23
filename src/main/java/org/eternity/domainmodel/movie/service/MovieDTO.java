package org.eternity.domainmodel.movie.service;

import org.eternity.domainmodel.movie.domain.Movie;

import java.util.ArrayList;
import java.util.List;

public record MovieDTO(Long movieId, String title, Integer runningTime, Long fee, List<ScreeningDTO> screenings) {
    public MovieDTO(Long movieId, String title, Integer runningTime, Long fee) {
        this(movieId, title, runningTime, fee, new ArrayList<>());
    }

    public MovieDTO(Movie movie) {
        this(movie.getId(), movie.getTitle(), movie.getRunningTime(), movie.getFee().longValue());
    }

    public void addScreeningDTO(ScreeningDTO screeningDTO) {
        screenings.add(screeningDTO);
    }
}
