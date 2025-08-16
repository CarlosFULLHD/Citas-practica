package Citas.Hospital.Arcoiris.citas.controllers;

import Citas.Hospital.Arcoiris.citas.dto.PacienteDto;
import Citas.Hospital.Arcoiris.citas.dto.ResponseGlobalDto;
import Citas.Hospital.Arcoiris.citas.interfacesService.InterfacePaciente;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {
    private final InterfacePaciente pacienteService;
    public PacienteController(InterfacePaciente pacienteService) {
        this.pacienteService = pacienteService;
    }
    //Crear paciente
    @PostMapping
    public ResponseEntity<ResponseGlobalDto> createPaciente(@Valid @RequestBody PacienteDto pacienteDto) {
        try {
            ResponseGlobalDto response = pacienteService.createPaciente(pacienteDto);
            return ResponseEntity.status(response.getCodigo()).body(response);
        } catch (IllegalArgumentException e) {
            ResponseGlobalDto responseError = new ResponseGlobalDto(
                    e.getMessage(),
                    400,
                    "/api/pacientes",
                    LocalDateTime.now(),
                    null
            );
            return ResponseEntity.badRequest().body(responseError);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseGlobalDto responseError = new ResponseGlobalDto(
                    "Error interno: " + e.getMessage(),
                    500,
                    "/api/pacientes",
                    LocalDateTime.now(),
                    null
            );
            return ResponseEntity.status(500).body(responseError);
        }
    }
    //Obtener todos los pacientes
    @GetMapping
    public ResponseEntity<ResponseGlobalDto> getAllPacientes(){
        ResponseGlobalDto response = pacienteService.getAllPacientes();
        return ResponseEntity.status(response.getCodigo()).body(response);
    }
    //Obtener paciente por Id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseGlobalDto> getPacienteById(@PathVariable Long id){
        try{
            ResponseGlobalDto response = pacienteService.getPacienteById(id);
            return ResponseEntity.status(response.getCodigo()).body(response);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new ResponseGlobalDto(
                    e.getMessage(),
                    400,
                    "paciente/{id}",
                    LocalDateTime.now(),
                    null
            ));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ResponseGlobalDto(
                    "Paciente con id no encontrado: " + e.getMessage(),
                    500,
                    "paciente/{id}",
                    LocalDateTime.now(),
                    null
            ));
        }
    }
    //Actualizar paciente
    @PutMapping("/{id}")
    public ResponseEntity<ResponseGlobalDto> updatePaciente(@PathVariable Long id, @Valid
                                                      @RequestBody PacienteDto pacienteDto){
        try {
            ResponseGlobalDto response = pacienteService.updatePaciente(id, pacienteDto);
            return ResponseEntity.status(response.getCodigo()).body(response);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new ResponseGlobalDto(
                    e.getMessage(),
                    400,
                    "paciente/{id}",
                    LocalDateTime.now(),
                    null
            ));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ResponseGlobalDto(
                    "Error interno " + e.getMessage(),
                    500,
                    "paciente/{id}",
                    LocalDateTime.now(),
                    null
            ));
        }
    }
    //Eliminar paciente
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseGlobalDto> deletePaciente(@PathVariable Long id){
        try{
            ResponseGlobalDto response = pacienteService.deletePaciente(id);
            return ResponseEntity.status(response.getCodigo()).body(response);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new ResponseGlobalDto(
                    e.getMessage(),
                    400,
                    "paciente/{id}",
                    LocalDateTime.now(),
                    null
            ));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(404).body(new ResponseGlobalDto(
                    "Error al eliminar paciente " + e.getMessage(),
                    404,
                    "paciente/{id}",
                    LocalDateTime.now(),
                    null
            ));
        }
    }
}
