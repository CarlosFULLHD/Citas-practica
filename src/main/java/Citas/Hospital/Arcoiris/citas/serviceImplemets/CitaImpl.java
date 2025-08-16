package Citas.Hospital.Arcoiris.citas.serviceImplemets;

import Citas.Hospital.Arcoiris.citas.dto.CitaDto;
import Citas.Hospital.Arcoiris.citas.dto.ResponseGlobalDto;
import Citas.Hospital.Arcoiris.citas.entities.Cita;
import Citas.Hospital.Arcoiris.citas.entities.Medico;
import Citas.Hospital.Arcoiris.citas.entities.Paciente;
import Citas.Hospital.Arcoiris.citas.interfacesService.InterfaceCita;
import Citas.Hospital.Arcoiris.citas.repositories.CitaRepository;
import Citas.Hospital.Arcoiris.citas.repositories.MedicoRepository;
import Citas.Hospital.Arcoiris.citas.repositories.PacienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaImpl implements InterfaceCita {
    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public CitaImpl(CitaRepository citaRepository, PacienteRepository pacienteRepository,
                    MedicoRepository medicoRepository) {
        this.citaRepository = citaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }
    //Crear cita
    @Override
    public ResponseGlobalDto createCita(CitaDto citaDto){
        //Validar fecha
        if(citaDto.getDateTime() == null || citaDto.getDateTime().isBefore(LocalDateTime.now())){
            return new ResponseGlobalDto(
                    "Fecha debe ser futura",
                    400,
                    "implements/cita",
                    LocalDateTime.now(),
                    null
            );
        }
        //Validar existencia de paciente
        Paciente paciente = pacienteRepository.findById(citaDto.getPacienteId())
                .orElseThrow(()-> new IllegalArgumentException("Paciente no encontrado"));
        //Validar existencia de medico
        Medico medico = medicoRepository.findById(citaDto.getMedicoId())
                .orElseThrow(()-> new IllegalArgumentException("Medico no encontrado"));
        //Validar que el médico tega dos citas al mismo tiempo
        if(citaRepository.existsByMedicoIdAndDateTime(citaDto.getMedicoId(), citaDto.getDateTime())){
            return new ResponseGlobalDto(
                    "El medico ya tine una cita a esa hora",
                    400,
                    "implements/cita",
                    LocalDateTime.now(),
                    null
            );
        }
        //Mapear DTO a entidad
        Cita cita = new Cita();
        cita.setDateTime(citaDto.getDateTime());
        cita.setReasonForConsultation(citaDto.getReasonForConsultation());
        cita.setPaciente(paciente);
        cita.setMedico(medico);
        //Guardar
        Cita citaGuardada = citaRepository.save(cita);
        //Mapear Dto para devolver
        CitaDto responseDto = new CitaDto(
                citaGuardada.getDateTime(),
                citaGuardada.getReasonForConsultation(),
                paciente.getId(),
                medico.getId()
        );
        return new ResponseGlobalDto(
                "Cita creada correctamente",
                201,
                "implements/cita",
                LocalDateTime.now(),
                responseDto
        );
    }
    //Obtener todas las citas
    @Override
    public ResponseGlobalDto getAllCitas(){
        List<Cita> citas = citaRepository.findAll();
        List<CitaDto> lista = citas.stream()
                .map(cita -> new CitaDto(
                        cita.getDateTime(),
                        cita.getReasonForConsultation(),
                        cita.getPaciente().getId(),
                        cita.getMedico().getId()
                ))
                .collect(Collectors.toList());
        return new ResponseGlobalDto(
                "Lista de citas obtenida correctamente",
                200,
                "implements/cita",
                LocalDateTime.now(),
                lista
        );
    }
    //Obtener cita por su Id
    @Override
    public ResponseGlobalDto getByIdCita(Long id){
        Cita cita = citaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Cita no existe"));
        CitaDto dto = new CitaDto(
                cita.getDateTime(),
                cita.getReasonForConsultation(),
                cita.getPaciente().getId(),
                cita.getMedico().getId()
        );
        return new ResponseGlobalDto(
                "Cita encontrada correctamente",
                200,
                "implements/cita",
                LocalDateTime.now(),
                dto
        );
    }
    //Modificar cita
    @Override
    public ResponseGlobalDto updateCita(Long id, CitaDto citaActu){
        Cita cita = citaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("La cita no exite"));
        //Validar nueva fecha
        if(citaActu.getDateTime() == null || citaActu.getDateTime().isBefore(LocalDateTime.now())) {
           return new ResponseGlobalDto(
                   "La cita de la fecha debe ser futura",
                   400,
                   "implements/cita",
                   LocalDateTime.now(),
                   null
           );
        }
        //Validar paciente
        Paciente paciente = pacienteRepository.findById(citaActu.getPacienteId())
                .orElseThrow(()-> new IllegalArgumentException("Paciente no encontrado"));
        //Validar medico
        Medico medico = medicoRepository.findById(citaActu.getMedicoId())
                .orElseThrow(()-> new RuntimeException("Medico no encontrado"));
        //Validar que no haya otra cita con el mismo médico y fecha
        boolean conflicto = citaRepository.existsByMedicoIdAndDateTime(medico.getId(),citaActu.getDateTime())
                && !cita.getDateTime().equals(citaActu.getDateTime());
        if(conflicto){
            return new ResponseGlobalDto(
                    "El medico ya tiene una cita a esa hora",
                    400,
                    "implements/cita",
                    LocalDateTime.now(),
                    null
            );
        }
        cita.setDateTime(citaActu.getDateTime());
        cita.setReasonForConsultation(citaActu.getReasonForConsultation());
        cita.setPaciente(paciente);
        cita.setMedico(medico);

        Cita citaActualizada = citaRepository.save(cita);

        CitaDto dto = new CitaDto(
                citaActualizada.getDateTime(),
                citaActualizada.getReasonForConsultation(),
                citaActualizada.getPaciente().getId(),
                citaActualizada.getMedico().getId()
        );
        return new ResponseGlobalDto(
                "Cita actualizada correctamente",
                200,
                "implements/cita",
                LocalDateTime.now(),
                dto
        );
    }
    //Borrar cita
    @Override
    public ResponseGlobalDto deleteCita(Long id){
        if(!citaRepository.existsById(id)){
            return new ResponseGlobalDto(
                    "Cita no encontrada",
                    404,
                    "implements/cita",
                    LocalDateTime.now(),
                    null);
        }
        citaRepository.deleteById(id);
        return new ResponseGlobalDto(
                "Cita eliminada correctamente",
                200,
                "implements/cita",
                LocalDateTime.now(),
                null
        );
    }
}
