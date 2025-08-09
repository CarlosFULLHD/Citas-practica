package Citas.Hospital.Arcoiris.citas.controllers;

import Citas.Hospital.Arcoiris.citas.dto.MedicoDto;
import Citas.Hospital.Arcoiris.citas.interfacesService.InterfaceMedico;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {
    private final InterfaceMedico medicoService;
    public MedicoController(InterfaceMedico medicoService) {
        this.medicoService = medicoService;
    }
    //Crear medico
    @PostMapping
    public ResponseEntity<MedicoDto> createMedico(@Valid @RequestBody MedicoDto medicoDto){
        MedicoDto create = medicoService.createMedico(medicoDto);
        return ResponseEntity.status(201).body(create);
    }
    //Obtener todos los medicos
    @GetMapping
    public ResponseEntity<List<MedicoDto>> getAllMedicos(){
        List<MedicoDto> medicos = medicoService.getAllMedicos();
        return ResponseEntity.ok(medicos);
    }
    //Obtener médico por Id
    @GetMapping("/{id}")
    public ResponseEntity<MedicoDto> getMedicoById(@PathVariable Long id){
        MedicoDto medicoDto = medicoService.getByIdMedico(id);
        if(medicoDto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(medicoDto);
    }
    //Actualizar medico
    @PutMapping("/{id}")
    public ResponseEntity<MedicoDto> updateMedico(@PathVariable Long id,
                                                  @Valid @RequestBody MedicoDto medicoDto){
        MedicoDto medicoActu = medicoService.updateMedico(id, medicoDto);
        return ResponseEntity.ok(medicoActu);
    }
    //Borra medico
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedico(@PathVariable Long id){
        medicoService.deleteMedico(id);
        return ResponseEntity.noContent().build();
    }
}
