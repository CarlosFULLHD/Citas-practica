package Citas.Hospital.Arcoiris.citas.serviceImplemets;

import Citas.Hospital.Arcoiris.citas.dto.PacienteDto;
import Citas.Hospital.Arcoiris.citas.entities.Paciente;
import Citas.Hospital.Arcoiris.citas.interfacesService.InterfacePaciente;
import Citas.Hospital.Arcoiris.citas.repositories.PacienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public PacienteDto createPaciente(PacienteDto pacienteDto) {
        //si DNI ya está registrado
        if(pacienteRepository.findByDni(pacienteDto.getDni()).isPresent()){
            throw new IllegalArgumentException("DNI ya registrado");
        }
        //si correo electrónico ya está registrado
        if(pacienteRepository.findByEmail(pacienteDto.getEmail()).isPresent()){
             throw  new IllegalArgumentException("Email ya registrado");
        }
        //si numero de teléfono ya está registrado
        if(pacienteRepository.findByPhoneNumber(pacienteDto.getPhoneNumber()).isPresent()){
            throw new IllegalArgumentException("Teléfono ya registrado");
        }
        //fecha de nacimiento no puede estar en el futuro
        if(pacienteDto.getDateBirth().isAfter(LocalDate.now())){
            throw new IllegalArgumentException("La fecha de nacimiento no puede estar en el futuro");
        }
        Paciente paciente = new Paciente();
        paciente.setName(pacienteDto.getName());
        paciente.setLastname(pacienteDto.getLastname());
        paciente.setDni(pacienteDto.getDni());
        paciente.setDateBirth(pacienteDto.getDateBirth());
        paciente.setPhoneNumber(pacienteDto.getPhoneNumber());
        paciente.setEmail(pacienteDto.getEmail());

        Paciente saved = pacienteRepository.save(paciente);
        return  new PacienteDto(
                saved.getName(),
                saved.getLastname(),
                saved.getDni(),
                saved.getDateBirth(),
                saved.getPhoneNumber(),
                saved.getEmail()
        );
    }
    //Obtener todos los pacientes
    @Override
    public List<PacienteDto> getAllPacientes() {
        List<Paciente>pacientes = pacienteRepository.findAll();
        return pacientes.stream()
                .map(paciente -> new PacienteDto(
                        paciente.getName(),
                        paciente.getLastname(),
                        paciente.getDni(),
                        paciente.getDateBirth(),
                        paciente.getPhoneNumber(),
                        paciente.getEmail()
                        ))
                .collect(Collectors.toList());
    }
    //Obtener paciente por Id
    @Override
    public PacienteDto getPacienteById(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Paciente no existe"));
        return new PacienteDto(
                paciente.getName(),
                paciente.getLastname(),
                paciente.getDni(),
                paciente.getDateBirth(),
                paciente.getPhoneNumber(),
                paciente.getEmail()
        );
    }
    //Modificar paciente
    @Override
    public PacienteDto updatePaciente(Long id, PacienteDto pacienteActu) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Paciente a modificar no existe"));
        //Validar fecha de nacimiento no puede ser futura
        if (pacienteActu.getDateBirth().isAfter(LocalDate.now())){
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura");
        }
        // Validar duplicado de DNI
        pacienteRepository.findByDni(pacienteActu.getDni()).ifPresent(existing->{
            if(!existing.getId().equals(id)){
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

    return new PacienteDto(
            update.getName(),
            update.getLastname(),
            update.getDni(),
            update.getDateBirth(),
            update.getPhoneNumber(),
            update.getEmail()
    );
    }
    //Borrar paciente
    @Override
    public void deletePaciente(Long id) {
        if(!pacienteRepository.existsById(id)){
            throw new RuntimeException("Paciente no encontrado");
        }
        pacienteRepository.deleteById(id);
    }
}
