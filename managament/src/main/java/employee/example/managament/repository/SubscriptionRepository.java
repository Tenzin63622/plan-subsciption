package employee.example.managament.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import employee.example.managament.entity.Subscription;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByUserIdAndStatus(Long userId, String status);

    boolean existsByUserIdAndStatus(Long userId, String status);
}