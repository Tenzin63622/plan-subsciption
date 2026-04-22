package employee.example.managament.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import employee.example.managament.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}