package employee.example.managament.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import employee.example.managament.repository.SubscriptionRepository;
import employee.example.managament.entity.Subscription;

import java.time.LocalDate;
import java.util.List;

@Component
public class SubscriptionScheduler {

    @Autowired
    private SubscriptionRepository repo;

    @Scheduled(cron = "0 0 0 * * ?") //important, run this method automatically based on time
    public void checkExpiry() {
        List<Subscription> subs = repo.findAll();

        for (Subscription s : subs) {
            if (s.getEndDate().isBefore(LocalDate.now())) {
                if (s.isAutoRenew()) {
                    s.setStartDate(LocalDate.now());
                    s.setEndDate(LocalDate.now().plusMonths(1));
                    s.setStatus("ACTIVE");
                } else {
                    s.setStatus("EXPIRED");
                }
                repo.save(s);
            }
        }
    }
}