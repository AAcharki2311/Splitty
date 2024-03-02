package server.database;

import commons.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the Event entity
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long>{
}
