package Citas.Hospital.Arcoiris.citas.serviceImplemets;

import Citas.Hospital.Arcoiris.citas.dto.MedicoDto;
import Citas.Hospital.Arcoiris.citas.dto.ResponseGlobalDto;
import Citas.Hospital.Arcoiris.citas.entities.Medico;
import Citas.Hospital.Arcoiris.citas.interfacesService.InterfaceMedico;
import Citas.Hospital.Arcoiris.citas.repositories.MedicoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoImpl implements InterfaceMedico {
    private final MedicoRepository medicoRepository;
    public MedicoImpl(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }
    //Crear un medico
    @Override
    public ResponseGlobalDto createMedico(MedicoDto medicoDto){
        //si numero teléfono ya esta registrado
        if(medicoRepository.findByPhoneNumber(medicoDto.getPhoneNumber()).isPresent()){
            return new ResponseGlobalDto(
                    "El numero de teléfono ya existe",
                    400,
                    "implements/medico",
                    LocalDateTime.now(),
                    null
            );
        }
        //si correo electrónico ya existe
        if(medicoRepository.findByEmail(medicoDto.getEmail()).isPresent()){
            return new ResponseGlobalDto(
                    "El correo electrónico ya existe",
                    400,
                    "implements/medico",
                    LocalDateTime.now(),
                   null
            );
        }
        Medico medico = new Medico();
        medico.setName(medicoDto.getName());
        medico.setLastname(medicoDto.getLastname());
        medico.setSpecialty(medicoDto.getSpeciality());
        medico.setPhoneNumber(medicoDto.getPhoneNumber());
        medico.setEmail(medicoDto.getEmail());

        Medico saved = medicoRepository.save(medico);
        MedicoDto responseDto = new MedicoDto(
                saved.getName(),
                saved.getLastname(),
                saved.getSpecialty(),
                saved.getPhoneNumber(),
                saved.getEmail()
        );
        return new ResponseGlobalDto(
                "Medico creado correctamente",
                201,
                "implements/medico",
                LocalDateTime.now(),
                responseDto
        );
    }
    //Obtener todos los medicos
    @Override
    public ResponseGlobalDto getAllMedicos(){
        List<Medico> medicos = medicoRepository.findAll();
        List<MedicoDto> lista = medicos.stream()
                .map(medico -> new MedicoDto(
                        medico.getName(),
                        medico.getLastname(),
                        medico.getSpecialty(),
                        medico.getPhoneNumber(),
                        medico.getEmail()
                ))
                .collect(Collectors.toList());
        return new ResponseGlobalDto(
                "Lista de medicos obtenidos correctamente",
                200,
                "implements/medico",
                LocalDateTime.now(),
                lista
        );
    }
    //Obtener médico por Id
    @Override
    public ResponseGlobalDto getByIdMedico(Long id){
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Medico no existe"));
        MedicoDto dto = new MedicoDto(
                medico.getName(),
                medico.getLastname(),
                medico.getSpecialty(),
                medico.getPhoneNumber(),
                medico.getEmail()
        );
        return new ResponseGlobalDto(
                "Medico encontrado correctamente",
                200,
                "implements/medico",
                LocalDateTime.now(),
                dto
        );
    }
    //Modificar medico
    @Override
    public ResponseGlobalDto updateMedico(Long id, MedicoDto medicoActu){
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Medico no existe"));
        //Validar numero teléfono no pertenezca a otro medico
        medicoRepository.findByPhoneNumber(medicoActu.getPhoneNumber()).ifPresent(existing->{
            if(!existing.getId().equals(id)){
                throw new IllegalArgumentException("El numero de teléfono pertenece a otro medico");
            }
        });
        //Validar correo electrónico no pertenezca a otro médico
        medicoRepository.findByEmail(medicoActu.getEmail()).ifPresent(existing->{
            if(!existing.getId().equals(id)){
                throw new IllegalArgumentException("El correo electrónico pertenece a otro medico");
            }
        });

        medico.setName(medicoActu.getName());
        medico.setLastname(medicoActu.getLastname());
        medico.setSpecialty(medicoActu.getSpeciality());
        medico.setPhoneNumber(medicoActu.getPhoneNumber());
        medico.setEmail(medicoActu.getEmail());

        Medico update = medicoRepository.save(medico);

        MedicoDto dto = new MedicoDto(
                update.getName(),
                update.getLastname(),
                update.getSpecialty(),
                update.getPhoneNumber(),
                update.getEmail()
        );
        return new ResponseGlobalDto(
                "Medico actualizado corectamente",
                200,
                "implements/medico",
                LocalDateTime.now(),
                dto
        );
    }
    //Borrar medico
    @Override
    public ResponseGlobalDto deleteMedico(Long id){
        if(!medicoRepository.existsById(id)){
            return new ResponseGlobalDto(
                    "Medico no encontrado",
                    404,
                    "implements/medico",
                    LocalDateTime.now(),
                    null);
        }
        medicoRepository.deleteById(id);
        return new ResponseGlobalDto(
                "Medico eliminado correctamente",
                200,
                "implements/medico",
                LocalDateTime.now(),
                null
        );
    }
}
