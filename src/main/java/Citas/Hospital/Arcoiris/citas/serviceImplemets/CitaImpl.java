package Citas.Hospital.Arcoiris.citas.serviceImplemets;

import Citas.Hospital.Arcoiris.citas.dto.CitaDto;
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
    public CitaDto createCita(CitaDto citaDto){
        //Validar fecha
        if(citaDto.getDateTime() == null || citaDto.getDateTime().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Fecha debe ser futura");
        }
        //Validar existencia de paciente
        Paciente paciente = pacienteRepository.findById(citaDto.getPacienteId())
                .orElseThrow(()-> new IllegalArgumentException("Paciente no encontrado"));
        //Validar existencia de medico
        Medico medico = medicoRepository.findById(citaDto.getMedicoId())
                .orElseThrow(()-> new IllegalArgumentException("Medico no encontrado"));
        //Validar que el médico tega dos citas al mismo tiempo
        if(citaRepository.existsByMedicoIdAndDateTime(citaDto.getMedicoId(), citaDto.getDateTime())){
            throw new IllegalArgumentException("El medico ya tine una cita a esa hora");
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
        return new CitaDto(
                citaGuardada.getDateTime(),
                citaGuardada.getReasonForConsultation(),
                paciente.getId(),
                medico.getId()
        );
    }
    //Obtener todas las citas
    @Override
    public List<CitaDto> getAllCitas(){
        List<Cita> citas = citaRepository.findAll();
        return citas.stream()
                .map(cita -> new CitaDto(
                        cita.getDateTime(),
                        cita.getReasonForConsultation(),
                        cita.getPaciente().getId(),
                        cita.getMedico().getId()
                ))
                .collect(Collectors.toList());
    }
    //Obtener cita por su Id
    @Override
    public CitaDto getByIdCita(Long id){
        Cita cita = citaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Cita no existe"));
        return new CitaDto(
                cita.getDateTime(),
                cita.getReasonForConsultation(),
                cita.getPaciente().getId(),
                cita.getMedico().getId()
        );
    }
    //Modificar cita
    @Override
    public CitaDto updateCita(Long id, CitaDto citaActu){
        Cita cita = citaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Cita no exite"));
        //Validar nueva fecha
        if(citaActu.getDateTime() == null || citaActu.getDateTime().isBefore(LocalDateTime.now())) {
           throw new IllegalArgumentException("La cita de la fecha debe ser futura");
        }
        //Validar paciente
        Paciente paciente = pacienteRepository.findById(citaActu.getPacienteId())
                .orElseThrow(()-> new IllegalArgumentException("Paciente no encontrado"));
        //Validar medico
        Medico medico = medicoRepository.findById(citaActu.getMedicoId())
                .orElseThrow(()-> new RuntimeException("Medico no encontrado"));
        //Validar que no haya otra cita con el mismo médico y fecha
        boolean conflicto = citaRepository.existsByMedicoIdAndDateTime(medico.getId(),citaActu.getDateTime())
                && !cita.getId().equals(id);
        if(conflicto){
            throw new IllegalArgumentException("El medico ya tiene una cita a esa hora");
        }

        cita.setDateTime(citaActu.getDateTime());
        cita.setReasonForConsultation(citaActu.getReasonForConsultation());
        cita.setPaciente(paciente);
        cita.setMedico(medico);

        Cita citaActualizada = citaRepository.save(cita);

        return new CitaDto(
                citaActualizada.getDateTime(),
                citaActualizada.getReasonForConsultation(),
                citaActualizada.getPaciente().getId(),
                citaActualizada.getMedico().getId()
        );
    }
    //Borrar cita
    @Override
    public void deleteCita(Long id){
        if(!citaRepository.existsById(id)){
            throw new RuntimeException("Cita no encontrada");
        }
        citaRepository.deleteById(id);
    }

    
}
