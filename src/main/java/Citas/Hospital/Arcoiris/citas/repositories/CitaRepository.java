package Citas.Hospital.Arcoiris.citas.repositories;

import Citas.Hospital.Arcoiris.citas.entities.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    boolean existsByMedicoIdAndDateTime(Long medicoId, LocalDateTime dateTime);

}
