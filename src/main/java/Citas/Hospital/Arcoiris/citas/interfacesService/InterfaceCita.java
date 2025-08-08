package Citas.Hospital.Arcoiris.citas.interfacesService;

import Citas.Hospital.Arcoiris.citas.dto.CitaDto;
import Citas.Hospital.Arcoiris.citas.entities.Cita;

import java.util.List;
import java.util.Optional;

public interface InterfaceCita {
    CitaDto createCita(CitaDto citaDto);
    List<CitaDto> getAllCitas();
    CitaDto getByIdCita(Long id);
    CitaDto updateCita(Long id, CitaDto citaActu);
    void deleteCita(Long id);
}
