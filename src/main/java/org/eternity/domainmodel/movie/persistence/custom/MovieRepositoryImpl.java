package org.eternity.domainmodel.movie.persistence.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.eternity.domainmodel.movie.domain.Movie;
import org.eternity.domainmodel.movie.domain.Screening;
import org.eternity.domainmodel.movie.persistence.MovieRepositoryCustom;
import org.eternity.domainmodel.movie.service.MovieDTO;
import org.eternity.domainmodel.movie.service.ScreeningDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class MovieRepositoryImpl implements MovieRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<MovieDTO> findMoviesLessThan(Integer runningTime) {
        List<Object[]> objects =
            em.createQuery("""
                select 
                    m, s 
                from Movie m left join Screening s 
                     on m.id = s.movieId
                where 
                    m.runningTime < :runningTime
                """)
                .setParameter("runningTime", runningTime)
                .getResultList();

        Map<Long, MovieDTO> movies = new HashMap<>();
        for(Object[] each : objects) {
            Movie movie = (Movie)each[0];
            Screening screening = (Screening)each[1];

            if (!movies.containsKey(movie.getId())) {
                movies.put(movie.getId(), new MovieDTO(movie));
            }

            if (screening != null) {
                movies.get(movie.getId()).addScreeningDTO(new ScreeningDTO(screening));
            }
        }

        return new ArrayList<>(movies.values());
    }
}
