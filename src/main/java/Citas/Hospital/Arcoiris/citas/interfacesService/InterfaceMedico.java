package Citas.Hospital.Arcoiris.citas.interfacesService;

import Citas.Hospital.Arcoiris.citas.dto.MedicoDto;
import Citas.Hospital.Arcoiris.citas.dto.ResponseGlobalDto;

public interface InterfaceMedico {
    ResponseGlobalDto createMedico(MedicoDto medicoDto);
    ResponseGlobalDto getAllMedicos();
    ResponseGlobalDto getByIdMedico(Long id);
    ResponseGlobalDto updateMedico(Long id, MedicoDto medicoActu);
    ResponseGlobalDto deleteMedico(Long id);
}
