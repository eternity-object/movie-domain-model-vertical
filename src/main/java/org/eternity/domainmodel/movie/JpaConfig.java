package org.eternity.domainmodel.movie;

import org.eternity.domainmodel.generic.Money;
import org.eternity.domainmodel.movie.domain.*;
import org.eternity.domainmodel.movie.persistence.CustomerRepository;
import org.eternity.domainmodel.movie.persistence.MovieRepository;
import org.eternity.domainmodel.movie.persistence.ScreeningRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Configuration
public class JpaConfig {
    @Bean
    public ApplicationRunner initializer(MovieRepository movieRepository,
                                         ScreeningRepository screeningRepository,
                                         CustomerRepository customerRepository) {
        return args -> {
            Movie movie = new Movie("한산", 120, Money.wons(10000L),
                    new AmountDiscountPolicy(Money.wons(1000),
                            Set.of(new SequenceCondition(1),
                                    new PeriodCondition(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(11, 30)))));
            movieRepository.save(movie);

            Screening screening = new Screening(movie.getId(), 1,
                    LocalDateTime.of(2024, 12, 11, 18, 0));
            screeningRepository.save(screening);

            Customer customer = new Customer("박나경");
            customerRepository.save(customer);
        };
    }
}
