package org.eternity.domainmodel.movie.service;

import org.eternity.domainmodel.movie.domain.Customer;
import org.eternity.domainmodel.movie.domain.Movie;
import org.eternity.domainmodel.movie.domain.Reservation;
import org.eternity.domainmodel.movie.domain.Screening;
import org.eternity.domainmodel.movie.persistence.CustomerRepository;
import org.eternity.domainmodel.movie.persistence.MovieRepository;
import org.eternity.domainmodel.movie.persistence.ReservationRepository;
import org.eternity.domainmodel.movie.persistence.ScreeningRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservationService {
    private CustomerRepository customerRepository;
    private ScreeningRepository screeningRepository;
    private MovieRepository movieRepository;
    private ReservationRepository reservationRepository;

    public ReservationService(CustomerRepository customerRepository,
                              ScreeningRepository screeningRepository,
                              MovieRepository movieRepository,
                              ReservationRepository reservationRepository) {
        this.customerRepository = customerRepository;
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Reservation reserveScreening(Long customerId, Long screeningId, Integer audienceCount) {
        Customer customer = customerRepository.findById(customerId).get();
        Screening screening = screeningRepository.findById(screeningId).get();
        Movie movie = movieRepository.findAggregateById(screening.getId()).get();

        Reservation reservation = screening.reserve(movie, customer, audienceCount);

        reservationRepository.save(reservation);

        return reservation;
    }
}
