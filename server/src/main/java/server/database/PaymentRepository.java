package server.database;

import commons.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the payment entity
 */
@Repository
public interface    PaymentRepository extends JpaRepository<Payment, Long>{
}
