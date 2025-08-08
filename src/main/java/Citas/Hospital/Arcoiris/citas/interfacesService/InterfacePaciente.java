package Citas.Hospital.Arcoiris.citas.interfacesService;

import Citas.Hospital.Arcoiris.citas.dto.PacienteDto;
import Citas.Hospital.Arcoiris.citas.entities.Paciente;

import java.util.List;
import java.util.Optional;

public interface InterfacePaciente {
    PacienteDto createPaciente(PacienteDto pacienteDto);
    List<PacienteDto> getAllPacientes();
    PacienteDto getPacienteById(Long id);
    PacienteDto updatePaciente(Long id, PacienteDto pacienteActu);
    void deletePaciente(Long id);
}
