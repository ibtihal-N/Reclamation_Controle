package ma.enset.reclamation.repositories;

import ma.enset.reclamation.entities.Reclamation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReclamationRepository extends JpaRepository<Reclamation,Long> {
    Page<Reclamation> findByTypeContains(String kw, Pageable pageable);
}
