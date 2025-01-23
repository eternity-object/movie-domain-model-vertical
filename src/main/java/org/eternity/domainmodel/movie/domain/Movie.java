package org.eternity.domainmodel.movie.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eternity.domainmodel.generic.Money;

@Entity
@NamedEntityGraphs(
    @NamedEntityGraph(
        name="Movie.policy",
        attributeNodes = {
            @NamedAttributeNode(
                value = "discountPolicy",
                subgraph = "policy.conditions")
        },
        subgraphs = {
            @NamedSubgraph(
                name = "policy.conditions",
                attributeNodes = @NamedAttributeNode("conditions"))
        }
    )
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer runningTime;
    private Money fee;

    @Version
    private long version;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name="POLICY_ID")
    private DiscountPolicy discountPolicy;

    public Movie(String title, Integer runningTime, Money fee, DiscountPolicy discountPolicy) {
        this.title = title;
        this.runningTime = runningTime;
        this.fee = fee;
        this.discountPolicy = discountPolicy;
        this.discountPolicy.setMovie(this);
    }

    public Money calculateFee(Screening screening) {
        return fee.minus(discountPolicy.calculateDiscount(screening));
    }

    public Money getFee() {
        return fee;
    }
}