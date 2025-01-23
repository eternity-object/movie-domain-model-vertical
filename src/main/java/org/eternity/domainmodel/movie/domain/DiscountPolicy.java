package org.eternity.domainmodel.movie.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eternity.domainmodel.generic.Money;

import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="policy_type")
@NoArgsConstructor @Getter
public abstract class DiscountPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "POLICY_ID")
    private Set<DiscountCondition> conditions = new HashSet<>();

    @OneToOne(mappedBy = "discountPolicy")
    private Movie movie;

    public DiscountPolicy(Set<DiscountCondition> conditions) {
        this.conditions = conditions;
    }

    public Money calculateDiscount(Screening screening) {
        for (DiscountCondition each : conditions) {
            if (each.isSatisfiedBy(screening)) {
                return getDiscountAmount(movie, screening);
            }
        }

        return Money.ZERO;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    abstract protected Money getDiscountAmount(Movie movie, Screening screening);
}
