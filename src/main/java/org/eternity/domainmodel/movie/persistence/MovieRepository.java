package org.eternity.domainmodel.movie.persistence;

import jakarta.persistence.LockModeType;
import org.eternity.domainmodel.movie.domain.Movie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long>, MovieRepositoryCustom {
    @EntityGraph(value = "Movie.policy")
    @Query("select m from Movie m where m.id = :id")
    Optional<Movie> findAggregateById(Long id);

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<Movie> findById(Long id);
}
