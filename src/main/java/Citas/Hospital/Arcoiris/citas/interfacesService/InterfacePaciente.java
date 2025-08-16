package Citas.Hospital.Arcoiris.citas.interfacesService;

import Citas.Hospital.Arcoiris.citas.dto.PacienteDto;
import Citas.Hospital.Arcoiris.citas.dto.ResponseGlobalDto;

public interface InterfacePaciente {
    ResponseGlobalDto createPaciente(PacienteDto pacienteDto);
    ResponseGlobalDto getAllPacientes();
    ResponseGlobalDto getPacienteById(Long id);
    ResponseGlobalDto updatePaciente(Long id, PacienteDto pacienteActu);
    ResponseGlobalDto deletePaciente(Long id);
}
