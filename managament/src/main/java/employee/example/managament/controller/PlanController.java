package employee.example.managament.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import employee.example.managament.entity.Plan;
import employee.example.managament.service.PlanService;

import java.util.List;

@RestController
@RequestMapping("/plans")
public class PlanController {

    @Autowired
    private PlanService service;

    @PostMapping
    public Plan create(@RequestBody Plan p) {
        return service.create(p);
    }

    @PutMapping("/{id}")
    public Plan update(@PathVariable Long id, @RequestBody Plan p) {
        return service.update(id, p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping
    public List<Plan> getAll() {
        return service.getAll();
    }
}