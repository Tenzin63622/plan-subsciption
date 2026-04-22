package employee.example.managament.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import employee.example.managament.entity.Plan;
import employee.example.managament.repository.PlanRepository;

import java.util.List;

@Service
public class PlanService {

    @Autowired
    private PlanRepository repo;

    public Plan create(Plan p) {
        return repo.save(p);
    }

    public Plan update(Long id, Plan p) {
        Plan existing = repo.findById(id).orElseThrow();
        existing.setName(p.getName());
        existing.setPrice(p.getPrice());
        existing.setDuration(p.getDuration());
        existing.setFeatures(p.getFeatures());
        return repo.save(existing);
    }

    public void delete(Long id) {
        Plan p = repo.findById(id).orElseThrow();
        p.setActive(false);
        repo.save(p);
    }

    public List<Plan> getAll() {
        return repo.findAll();
    }
}