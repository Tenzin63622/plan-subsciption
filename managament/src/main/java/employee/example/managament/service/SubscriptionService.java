
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

    // creating subscription
    public Subscription subscribe(Long userId, Long planId) {

        if (repo.existsByUserIdAndStatus(userId, "ACTIVE")) {
            throw new RuntimeException("User already has active subscription");
        }

        Plan plan = planRepo.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

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

    //  code to change the plan of subscription
    public Subscription changePlan(Long userId, Long newPlanId) {

        Subscription current = repo.findByUserIdAndStatus(userId, "ACTIVE")
                .orElseThrow(() -> new RuntimeException("No active subscription found"));

        // cancel current subscription
        current.setStatus("CANCELLED");
        repo.save(current);

        // create new subscription
        return subscribe(userId, newPlanId);
    }

    // canel subscription code is below is currently not working
    public Subscription cancel(Long id, String type) {

        Subscription s = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        if (type.equalsIgnoreCase("IMMEDIATE")) {
            s.setStatus("CANCELLED");
            s.setAutoRenew(false);
        } else {
            s.setAutoRenew(false);
        }

        return repo.save(s);
    }

    //  to get all active subcription
    public Subscription getByUser(Long userId) {
        return repo.findByUserIdAndStatus(userId, "ACTIVE")
                .orElseThrow(() -> new RuntimeException("No active subscription found"));
    }

    // delete of the subscription
    public void delete(Long id) {

        Subscription s = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        // soft delete instead of physical delete
        s.setStatus("DELETED");
        s.setAutoRenew(false);

        repo.save(s);
    }
}