package org.eternity.domainmodel.movie.presentation;

import org.eternity.domainmodel.generic.Money;
import org.eternity.domainmodel.movie.domain.Reservation;
import org.eternity.domainmodel.movie.persistence.CustomerRepository;
import org.eternity.domainmodel.movie.persistence.ReservationRepository;
import org.eternity.domainmodel.movie.persistence.ScreeningRepository;
import org.eternity.domainmodel.movie.service.MovieDTO;
import org.eternity.domainmodel.movie.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//  curl -H "Content-Type: application/json" -X POST http://localhost:8080/reservations  -d '{"customerId":1, "screeningId":1, "audienceCount":2}'
@RestController
public class ReservationController {
    private CustomerRepository customerRepository;
    private ScreeningRepository screeningRepository;
    private ReservationRepository reservationRepository;

    private ReservationService reservationService;

    public ReservationController(CustomerRepository customerRepository, ScreeningRepository screeningRepository, ReservationRepository reservationRepository, ReservationService reservationService) {
        this.customerRepository = customerRepository;
        this.screeningRepository = screeningRepository;
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ReservationResponse reserve(@RequestBody ReservationRequest request) {
        Reservation reservation = reservationService.reserveScreening(request.getCustomerId(), request.getScreeningId(), request.getAudienceCount());

        return new ReservationResponse(reservation);
    }

    @GetMapping("/movies")
    public MoviesResponse findMovies(@RequestParam("runningTime") Integer runningTime) {
        return new MoviesResponse(reservationService.findMovies(runningTime));
    }
}

class MoviesResponse {
    private List<MovieDTO> movies;

    public MoviesResponse(List<MovieDTO> movies) {
        this.movies = movies;
    }

    public List<MovieDTO> getMovies() {
        return movies;
    }
}

class ReservationRequest {
    private Long customerId;
    private Long screeningId;
    private Integer audienceCount;

    public Long getCustomerId() {
        return customerId;
    }

    public Long getScreeningId() {
        return screeningId;
    }

    public Integer getAudienceCount() {
        return audienceCount;
    }
}

class ReservationResponse {
    private Long customerId;
    private Long screeningId;
    private Integer audienceCount;
    private Money fee;

    public ReservationResponse(Reservation reservation) {
        this.customerId = reservation.getCustomerId();
        this.screeningId = reservation.getScreeningId();
        this.audienceCount = reservation.getAudienceCount();
        this.fee = reservation.getFee();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getScreeningId() {
        return screeningId;
    }

    public Integer getAudienceCount() {
        return audienceCount;
    }

    public Money getFee() {
        return fee;
    }
}