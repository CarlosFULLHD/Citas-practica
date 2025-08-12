package Citas.Hospital.Arcoiris.citas.controllers;

import Citas.Hospital.Arcoiris.citas.dto.PacienteDto;
import Citas.Hospital.Arcoiris.citas.interfacesService.InterfacePaciente;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {
    private final InterfacePaciente pacienteService;
    public PacienteController(InterfacePaciente pacienteService) {
        this.pacienteService = pacienteService;
    }
    //Crear paciente
    @PostMapping
    public ResponseEntity<?> createPaciente(@Valid @RequestBody PacienteDto pacienteDto) {
        try {
            PacienteDto create = pacienteService.createPaciente(pacienteDto);
            return ResponseEntity.status(201).body(create);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error intermo: " + e.getMessage());
        }
    }
    //Obtener todos los pacientes
    @GetMapping
    public ResponseEntity<List<PacienteDto>> getAllPacientes(){
        List<PacienteDto> pacientes = pacienteService.getAllPacientes();
        return ResponseEntity.ok(pacientes);
    }
    //Obtener paciente por Id
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDto> getPacienteById(@PathVariable Long id){
        PacienteDto pacienteDto = pacienteService.getPacienteById(id);
        if(pacienteDto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pacienteDto);
    }
    //Actualizar paciente
    @PutMapping("/{id}")
    public ResponseEntity<PacienteDto> updatePaciente(@PathVariable Long id, @Valid
                                                      @RequestBody PacienteDto pacienteDto){
        PacienteDto pacienteActu = pacienteService.updatePaciente(id,pacienteDto);
        return ResponseEntity.ok(pacienteActu);
    }
    //Eliminar paciente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id){
        pacienteService.deletePaciente(id);
        return ResponseEntity.noContent().build();
    }
}
