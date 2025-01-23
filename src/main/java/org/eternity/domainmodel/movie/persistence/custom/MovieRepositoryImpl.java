package org.eternity.domainmodel.movie.persistence.custom;

import lombok.AllArgsConstructor;
import org.eternity.domainmodel.movie.persistence.MovieRepositoryCustom;
import org.eternity.domainmodel.movie.service.MovieDTO;
import org.eternity.domainmodel.movie.service.ScreeningDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class MovieRepositoryImpl implements MovieRepositoryCustom {
    private JdbcClient jdbcClient;

    @Override
    public List<MovieDTO> findMoviesLessThan(Integer runningTime) {
        return jdbcClient.sql("""
                        select m.id, m.title, m.running_time, m.fee, s.id as screening_id, s.sequence, s.screening_time 
                        from movie as m left join screening as s on m.id = s.movie_id 
                        where m.running_time < ?
                        """)
                .param(1, runningTime)
                .query(new ResultSetExtractor<List<MovieDTO>>() {
                    @Override
                    public List<MovieDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        Map<Long, MovieDTO> movies = new HashMap<>();
                        while (rs.next()) {
                            if (!movies.containsKey(rs.getLong("id"))) {
                                movies.put(rs.getLong("id"), new MovieDTO(rs.getLong("id"), rs.getString("title"), rs.getInt("running_time"), rs.getLong("fee")));
                            }

                            if (rs.getLong("screening_id") != 0) {
                                movies.get(rs.getLong("id")).addScreeningDTO(new ScreeningDTO(rs.getLong("screening_id"), rs.getInt("sequence"), rs.getTimestamp("screening_time").toLocalDateTime()));
                            }
                        }
                        return new ArrayList<>(movies.values());
                    }
                });
    }
}
