package org.eternity.domainmodel.movie.presentation;

import org.eternity.domainmodel.generic.Money;
import org.eternity.domainmodel.movie.domain.Reservation;
import org.eternity.domainmodel.movie.service.ReservationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//  curl -H "Content-Type: application/json" -X POST http://localhost:8080/reservations  -d '{"customerId":1, "screeningId":1, "audienceCount":2}'
@RestController
public class ReservationController {
    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ReservationResponse reserve(@RequestBody ReservationRequest request) {
        Reservation reservation = reservationService.reserveScreening(request.getCustomerId(), request.getScreeningId(), request.getAudienceCount());

        return new ReservationResponse(reservation);
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