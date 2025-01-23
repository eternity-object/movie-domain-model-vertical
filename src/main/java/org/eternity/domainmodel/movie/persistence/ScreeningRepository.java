package org.eternity.domainmodel.movie.persistence;

import org.eternity.domainmodel.movie.domain.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {
}
