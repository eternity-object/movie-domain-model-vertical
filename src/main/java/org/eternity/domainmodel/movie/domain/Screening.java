package org.eternity.domainmodel.movie.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eternity.domainmodel.generic.Money;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Screening {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int sequence;
    private LocalDateTime screeningTime;
    private Long movieId;

    public Screening(Long movieId, int sequence, LocalDateTime screeningTime) {
        this.movieId = movieId;
        this.sequence = sequence;
        this.screeningTime = screeningTime;
    }

    public Reservation reserve(Movie movie, Customer customer, int audienceCount) {
        Money fee = movie.calculateFee(this).times(audienceCount);
        return new Reservation(customer, this, audienceCount, fee);
    }

    public Money getFixedFee(Movie movie) {
        return movie.getFee();
    }

    public boolean isSequence(int sequence) {
        return this.sequence == sequence;
    }

    public LocalDateTime getStartTime() {
        return screeningTime;
    }
}
