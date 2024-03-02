package server.database;

import commons.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the participant entity
 */
@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long>{
}
