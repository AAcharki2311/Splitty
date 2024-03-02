package server.database;

import commons.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the expense entity
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>{
}
