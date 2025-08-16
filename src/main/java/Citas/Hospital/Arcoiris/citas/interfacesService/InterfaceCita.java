package Citas.Hospital.Arcoiris.citas.interfacesService;

import Citas.Hospital.Arcoiris.citas.dto.CitaDto;
import Citas.Hospital.Arcoiris.citas.dto.ResponseGlobalDto;

public interface InterfaceCita {
    ResponseGlobalDto createCita(CitaDto citaDto);
    ResponseGlobalDto getAllCitas();
    ResponseGlobalDto getByIdCita(Long id);
    ResponseGlobalDto updateCita(Long id, CitaDto citaActu);
    ResponseGlobalDto deleteCita(Long id);
}
