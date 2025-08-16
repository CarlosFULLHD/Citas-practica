package Citas.Hospital.Arcoiris.citas.serviceImplemets;

import Citas.Hospital.Arcoiris.citas.dto.PacienteDto;
import Citas.Hospital.Arcoiris.citas.dto.ResponseGlobalDto;
import Citas.Hospital.Arcoiris.citas.entities.Paciente;
import Citas.Hospital.Arcoiris.citas.interfacesService.InterfacePaciente;
import Citas.Hospital.Arcoiris.citas.repositories.PacienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteImpl implements InterfacePaciente {
    private final PacienteRepository pacienteRepository;
    public PacienteImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }
    //Crear un paciente
    @Override
    public ResponseGlobalDto createPaciente(PacienteDto pacienteDto) {
        //si DNI ya está registrado
        if(pacienteRepository.findByDni(pacienteDto.getDni()).isPresent()){
            return new ResponseGlobalDto(
                    "El DNI ya esta registrado",
                    400,
                    "implements/paciente",
                    LocalDateTime.now(),
                    null
            );
        }
        //si correo electrónico ya está registrado
        if(pacienteRepository.findByEmail(pacienteDto.getEmail()).isPresent()){
             return new ResponseGlobalDto(
                     "Email ya registrado",
                     400,
                     "implements/paciente",
                     LocalDateTime.now(),
                     null
                     );
        }
        //si numero de teléfono ya está registrado
        if(pacienteRepository.findByPhoneNumber(pacienteDto.getPhoneNumber()).isPresent()){
            return new ResponseGlobalDto(
                    "Teléfono ya registrado",
                    400,
                    "implements/paciente",
                    LocalDateTime.now(),
                    null
            );
        }
        //fecha de nacimiento no puede estar en el futuro
        if(pacienteDto.getDateBirth().isAfter(LocalDate.now())){
            return new ResponseGlobalDto(
                    "La fecha de nacimiento no puede estar en el futuro",
                    400,
                    "Implements/paciente",
                    LocalDateTime.now(),
                    null);
        }
        Paciente paciente = new Paciente();
        paciente.setName(pacienteDto.getName());
        paciente.setLastname(pacienteDto.getLastname());
        paciente.setDni(pacienteDto.getDni());
        paciente.setDateBirth(pacienteDto.getDateBirth());
        paciente.setPhoneNumber(pacienteDto.getPhoneNumber());
        paciente.setEmail(pacienteDto.getEmail());

        Paciente saved = pacienteRepository.save(paciente);
        PacienteDto responseDto = new PacienteDto(
                saved.getName(),
                saved.getLastname(),
                saved.getDni(),
                saved.getDateBirth(),
                saved.getPhoneNumber(),
                saved.getEmail()
        );
        return new ResponseGlobalDto(
                "Paciente creado correctamente",
                201,
                "implements/paciente",
                LocalDateTime.now(),
                responseDto
        );
    }
    //Obtener todos los pacientes
    @Override
    public ResponseGlobalDto getAllPacientes() {
        List<Paciente>pacientes = pacienteRepository.findAll();
        List<PacienteDto> lista = pacientes.stream()
                .map(paciente -> new PacienteDto(
                        paciente.getName(),
                        paciente.getLastname(),
                        paciente.getDni(),
                        paciente.getDateBirth(),
                        paciente.getPhoneNumber(),
                        paciente.getEmail()
                        ))
                .collect(Collectors.toList());
        return new ResponseGlobalDto(
                "Lista de pacientes obtenido correctamente",
                200,
                "implements/paciente",
                LocalDateTime.now(),
                lista
        );
    }
    //Obtener paciente por Id
    @Override
    public ResponseGlobalDto getPacienteById(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Paciente no existe"));
        PacienteDto dto = new PacienteDto(
                paciente.getName(),
                paciente.getLastname(),
                paciente.getDni(),
                paciente.getDateBirth(),
                paciente.getPhoneNumber(),
                paciente.getEmail()
        );
        return new ResponseGlobalDto(
                "Paciente encontrado correctamente",
                200,
                "implements/paciente",
                LocalDateTime.now(),
                dto
        );
    }
    //Modificar paciente
    @Override
    public ResponseGlobalDto updatePaciente(Long id, PacienteDto pacienteActu) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Paciente a modificar no existe"));
        //Validar fecha de nacimiento no puede ser futura
        if (pacienteActu.getDateBirth().isAfter(LocalDate.now())){
            return new ResponseGlobalDto(
                    "La fecha de nacimiento no puede ser futura",
                    400,
                    "implements/paciente",
                    LocalDateTime.now(),
                    null);
        }
        // Validar duplicado de DNI
        pacienteRepository.findByDni(pacienteActu.getDni()).ifPresent(existing->{
            if(!existing.getId().equals(id)) {
                throw new IllegalArgumentException("El DNI pertenece a otro paciente");
            }
        });
        // Validar duplicado de email
        pacienteRepository.findByEmail(pacienteActu.getEmail()).ifPresent(existing->{
            if(!existing.getId().equals(id)){
                throw new IllegalArgumentException("El correo electronic pertenece a otro paciente");
            }
        });
        // Validar duplicado de teléfono
        pacienteRepository.findByPhoneNumber(pacienteActu.getPhoneNumber()).ifPresent(existing->{
            if(!existing.getId().equals(id)){
                throw new IllegalArgumentException("El teléfono pertenece a otro paciente");
            }
        });

         paciente.setName(pacienteActu.getName());
         paciente.setLastname(pacienteActu.getLastname());
         paciente.setDateBirth(pacienteActu.getDateBirth());
         paciente.setDni(pacienteActu.getDni());
         paciente.setPhoneNumber(pacienteActu.getPhoneNumber());
         paciente.setEmail(pacienteActu.getEmail());

    Paciente update = pacienteRepository.save(paciente);

    PacienteDto dto = new PacienteDto(
            update.getName(),
            update.getLastname(),
            update.getDni(),
            update.getDateBirth(),
            update.getPhoneNumber(),
            update.getEmail()
    );
    return new ResponseGlobalDto(
            "Paciente actualizado correctamente",
            200,
            "implements/paciente",
            LocalDateTime.now(),
            dto
    );
    }
    //Borrar paciente
    @Override
    public ResponseGlobalDto deletePaciente(Long id) {
        if(!pacienteRepository.existsById(id)){
            return new ResponseGlobalDto(
                    "Paciente no encontrado",
                    404,
                    "implements/paciente",
                    LocalDateTime.now(),
                    null);
        }
        pacienteRepository.deleteById(id);
        return new ResponseGlobalDto(
                "Paciente eliminado corectamente",
                200,
                "implements/paciente",
                LocalDateTime.now(),
                null
        );
    }
}
