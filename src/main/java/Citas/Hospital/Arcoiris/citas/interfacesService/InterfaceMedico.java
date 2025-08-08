package Citas.Hospital.Arcoiris.citas.interfacesService;

import Citas.Hospital.Arcoiris.citas.dto.MedicoDto;
import Citas.Hospital.Arcoiris.citas.entities.Medico;

import java.util.List;
import java.util.Optional;

public interface InterfaceMedico {
    MedicoDto createMedico(MedicoDto medicoDto);
    List<MedicoDto> getAllMedicos();
    MedicoDto getByIdMedico(Long id);
    MedicoDto updateMedico(Long id, MedicoDto medicoActu);
    void deleteMedico(Long id);
}
