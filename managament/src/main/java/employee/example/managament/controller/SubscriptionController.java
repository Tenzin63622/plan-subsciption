package employee.example.managament.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import employee.example.managament.entity.Subscription;
import employee.example.managament.service.SubscriptionService;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService service;

    @PostMapping("/{userId}/{planId}")
    public Subscription subscribe(@PathVariable Long userId, @PathVariable Long planId) {
        return service.subscribe(userId, planId);
    }

    @PutMapping("/{userId}/change/{planId}")
    public Subscription change(@PathVariable Long userId, @PathVariable Long planId) {
        return service.changePlan(userId, planId);
    }

    @PutMapping("/cancel/{id}")
    public Subscription cancel(@PathVariable Long id, @RequestParam String type) {
        return service.cancel(id, type);
    }

    @GetMapping("/{userId}")
    public Subscription get(@PathVariable Long userId) {
        return service.getByUser(userId);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Subscription deleted successfully";
    }
}