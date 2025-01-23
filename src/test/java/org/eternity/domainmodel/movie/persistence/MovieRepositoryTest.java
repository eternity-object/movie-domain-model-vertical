package org.eternity.domainmodel.movie.persistence;

import jakarta.persistence.EntityManager;
import org.eternity.domainmodel.generic.Money;
import org.eternity.domainmodel.movie.domain.AmountDiscountPolicy;
import org.eternity.domainmodel.movie.domain.Movie;
import org.eternity.domainmodel.movie.domain.Screening;
import org.eternity.domainmodel.movie.domain.SequenceCondition;
import org.eternity.domainmodel.movie.service.MovieDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
public class MovieRepositoryTest {
    @Autowired
    private EntityManager em;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void find_movies() {
        Movie movie1 = new Movie("영화1", 120, Money.wons(10000),
                new AmountDiscountPolicy(
                        Money.wons(1000),
                        Set.of(new SequenceCondition(1))));
        Movie movie2 = new Movie("영화2", 120, Money.wons(10000),
                new AmountDiscountPolicy(
                        Money.wons(1000),
                        Set.of(new SequenceCondition(1), new SequenceCondition(2))));

        em.persist(movie1);
        em.persist(movie2);

        em.persist(new Screening(movie1.getId(), 1, LocalDateTime.of(2025, 1, 1, 9, 0)));
        em.persist(new Screening(movie2.getId(), 1, LocalDateTime.of(2025, 1, 2, 9, 0)));
        em.persist(new Screening(movie2.getId(), 1, LocalDateTime.of(2025, 1, 2, 11, 30)));

        em.flush();
        em.clear();

        List<MovieDTO> movies = movieRepository.findMoviesLessThan(150);

        assertThat(movies).hasSize(2);
        assertThat(movies.get(0).screenings()).hasSize(1);
        assertThat(movies.get(1).screenings()).hasSize(2);
    }
}
