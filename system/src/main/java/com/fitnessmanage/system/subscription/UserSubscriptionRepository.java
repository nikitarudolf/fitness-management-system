package com.fitnessmanage.system.subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    
    List<UserSubscription> findByUserId(Long userId);

    @Query("SELECT us FROM UserSubscription us " +
           "WHERE us.user.id = :userId " +
           "AND us.isActive = true " +
           "AND us.endDate >= CURRENT_DATE")
    Optional<UserSubscription> findActiveSubscription(@Param("userId") Long userId);

    @Query("SELECT COUNT(us) FROM UserSubscription us " +
            "WHERE us.isActive = true AND us.endDate >= CURRENT_DATE")
    long countActiveSubscriptions();

    @Query("SELECT SUM(us.plan.price) FROM UserSubscription us")
    BigDecimal calculateTotalRevenue();
}
