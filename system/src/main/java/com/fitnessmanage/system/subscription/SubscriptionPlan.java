package com.fitnessmanage.system.subscription;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "subscription_plans")
@Getter
@Setter
@NoArgsConstructor
public class SubscriptionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "duration_days", nullable = false)
    private Integer durationDays;

    @Column(name = "includes_trainer_access", nullable = false)
    private Boolean includesTrainerAccess;
}
