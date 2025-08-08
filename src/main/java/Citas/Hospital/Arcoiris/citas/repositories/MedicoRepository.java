package Citas.Hospital.Arcoiris.citas.repositories;

import Citas.Hospital.Arcoiris.citas.entities.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findByPhoneNumber(String phoneNumber);
    Optional<Medico> findByEmail(String email);
}
