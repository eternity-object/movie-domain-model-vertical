package org.eternity.domainmodel.movie.persistence;

import org.eternity.domainmodel.movie.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
