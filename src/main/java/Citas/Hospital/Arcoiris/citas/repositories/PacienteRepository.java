package Citas.Hospital.Arcoiris.citas.repositories;

import Citas.Hospital.Arcoiris.citas.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente>findByDni(String dni);
    Optional<Paciente>findByEmail(String email);
    Optional<Paciente>findByPhoneNumber(String phoneNumber);
}
