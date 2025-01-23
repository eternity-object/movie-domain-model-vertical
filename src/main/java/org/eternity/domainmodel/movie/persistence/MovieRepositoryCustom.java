package org.eternity.domainmodel.movie.persistence;

import org.eternity.domainmodel.movie.service.MovieDTO;

import java.util.List;

public interface MovieRepositoryCustom {
    List<MovieDTO> findMoviesLessThan(Integer runningTime);
}
