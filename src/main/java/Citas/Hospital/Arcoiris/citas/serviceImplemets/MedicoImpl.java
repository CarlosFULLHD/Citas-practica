package Citas.Hospital.Arcoiris.citas.serviceImplemets;

import Citas.Hospital.Arcoiris.citas.dto.MedicoDto;
import Citas.Hospital.Arcoiris.citas.entities.Medico;
import Citas.Hospital.Arcoiris.citas.interfacesService.InterfaceMedico;
import Citas.Hospital.Arcoiris.citas.repositories.MedicoRepository;
import org.springframework.stereotype.Service;

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
    public MedicoDto createMedico(MedicoDto medicoDto){
        //si numero teléfono ya esta registrado
        if(medicoRepository.findByPhoneNumber(medicoDto.getPhoneNumber()).isPresent()){
            throw new IllegalArgumentException("El numero de teléfono ya existe");
        }
        //si correo electrónico ya existe
        if(medicoRepository.findByEmail(medicoDto.getEmail()).isPresent()){
            throw new IllegalArgumentException("El correo electrónico ya existe");
        }
        Medico medico = new Medico();
        medico.setName(medicoDto.getName());
        medico.setLastname(medicoDto.getLastname());
        medico.setSpecialty(medicoDto.getSpeciality());
        medico.setPhoneNumber(medicoDto.getPhoneNumber());
        medico.setEmail(medicoDto.getEmail());

        Medico saved = medicoRepository.save(medico);
        return new MedicoDto(
                saved.getName(),
                saved.getLastname(),
                saved.getSpecialty(),
                saved.getPhoneNumber(),
                saved.getEmail()
        );
    }
    //Obtener todos los medicos
    @Override
    public List<MedicoDto> getAllMedicos(){
        List<Medico> medicos = medicoRepository.findAll();
        return medicos.stream()
                .map(medico -> new MedicoDto(
                        medico.getName(),
                        medico.getLastname(),
                        medico.getSpecialty(),
                        medico.getPhoneNumber(),
                        medico.getEmail()
                ))
                .collect(Collectors.toList());
    }
    //Obtener médico por Id
    @Override
    public MedicoDto getByIdMedico(Long id){
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Medico no existe"));
        return new MedicoDto(
                medico.getName(),
                medico.getLastname(),
                medico.getSpecialty(),
                medico.getPhoneNumber(),
                medico.getEmail()
        );
    }
    //Modificar medico
    @Override
    public MedicoDto updateMedico(Long id, MedicoDto medicoActu){
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

        return new MedicoDto(
                update.getName(),
                update.getLastname(),
                update.getSpecialty(),
                update.getPhoneNumber(),
                update.getEmail()
        );
    }
    //Borrar medico
    @Override
    public void deleteMedico(Long id){
        if(!medicoRepository.existsById(id)){
            throw new RuntimeException("Medico no encontrado");
        }
        medicoRepository.deleteById(id);
    }
}
