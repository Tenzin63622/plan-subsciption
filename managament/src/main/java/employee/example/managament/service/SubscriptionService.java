package employee.example.managament.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import employee.example.managament.entity.Plan;
import employee.example.managament.entity.Subscription;
import employee.example.managament.repository.PlanRepository;
import employee.example.managament.repository.SubscriptionRepository;

import java.time.LocalDate;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository repo;

    @Autowired
    private PlanRepository planRepo;

    public Subscription subscribe(Long userId, Long planId) {

        if (repo.existsByUserIdAndStatus(userId, "ACTIVE")) {
            throw new RuntimeException("User already has active subscription");
        }

        Plan plan = planRepo.findById(planId).orElseThrow();

        Subscription s = new Subscription();
        s.setUserId(userId);
        s.setPlan(plan);
        s.setStartDate(LocalDate.now());

        if (plan.getDuration().equalsIgnoreCase("MONTHLY")) {
            s.setEndDate(LocalDate.now().plusMonths(1));
        } else {
            s.setEndDate(LocalDate.now().plusYears(1));
        }

        s.setStatus("ACTIVE");
        s.setAutoRenew(true);

        return repo.save(s);
    }

    public void delete(Long id) {
        Subscription s = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        repo.delete(s);
    }

    public Subscription changePlan(Long userId, Long newPlanId) {
        Subscription current = repo.findByUserIdAndStatus(userId, "ACTIVE").orElseThrow();

        current.setStatus("CANCELLED");

        return subscribe(userId, newPlanId);
    }

    public Subscription cancel(Long id, String type) {
        Subscription s = repo.findById(id).orElseThrow();

        if (type.equalsIgnoreCase("IMMEDIATE")) {
            s.setStatus("CANCELLED");
        } else {
            s.setAutoRenew(false);
        }

        return repo.save(s);
    }

    public Subscription getByUser(Long userId) {
        return repo.findByUserIdAndStatus(userId, "ACTIVE").orElseThrow();
    }
}