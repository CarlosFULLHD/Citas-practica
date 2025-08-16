package Citas.Hospital.Arcoiris.citas.controllers;

import Citas.Hospital.Arcoiris.citas.dto.MedicoDto;
import Citas.Hospital.Arcoiris.citas.dto.ResponseGlobalDto;
import Citas.Hospital.Arcoiris.citas.interfacesService.InterfaceMedico;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {
    private final InterfaceMedico medicoService;
    public MedicoController(InterfaceMedico medicoService) {
        this.medicoService = medicoService;
    }
    //Crear medico
    @PostMapping
    public ResponseEntity<ResponseGlobalDto> createMedico(@Valid @RequestBody MedicoDto medicoDto){
        try{
            ResponseGlobalDto response = medicoService.createMedico(medicoDto);
            return ResponseEntity.status(response.getCodigo()).body(response);
        }catch (IllegalArgumentException e){
            ResponseGlobalDto responseError = new ResponseGlobalDto(
                    e.getMessage(),
                    400,
                    "/api/medicos",
                    LocalDateTime.now(),
                    null
            );
            return ResponseEntity.badRequest().body(responseError);
        }catch (Exception e){
            e.printStackTrace();
            ResponseGlobalDto responseError = new ResponseGlobalDto(
                    "Error interno : " + e.getMessage(),
                    500,
                    "/api/medicos",
                    LocalDateTime.now(),
                    null
            );
            return ResponseEntity.status(500).body(responseError);
        }
    }
    //Obtener todos los medicos
    @GetMapping
    public ResponseEntity<ResponseGlobalDto> getAllMedicos(){
        ResponseGlobalDto response = medicoService.getAllMedicos();
        return ResponseEntity.status(response.getCodigo()).body(response);
    }
    //Obtener médico por Id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseGlobalDto> getMedicoById(@PathVariable Long id){
        try{
            ResponseGlobalDto response = medicoService.getByIdMedico(id);
            return ResponseEntity.status(response.getCodigo()).body(response);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new ResponseGlobalDto(
                    e.getMessage(),
                    400,
                    "/api/medicos/{id}",
                    LocalDateTime.now(),
                    null
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body(new ResponseGlobalDto(
                    "Error medico con id no encontrado: " + e.getMessage(),
                    500,
                    "/api/medicos/{id}",
                    LocalDateTime.now(),
                    null
            ));
        }
    }
    //Actualizar medico
    @PutMapping("/{id}")
    public ResponseEntity<ResponseGlobalDto> updateMedico(@PathVariable Long id,
                                                  @Valid @RequestBody MedicoDto medicoDto){
        try{
            ResponseGlobalDto response = medicoService.updateMedico(id, medicoDto);
            return ResponseEntity.status(response.getCodigo()).body(response);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new ResponseGlobalDto(
                    e.getMessage(),
                    400,
                    "/api/medicos/{id}",
                    LocalDateTime.now(),
                    null
            ));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ResponseGlobalDto(
                    "Error interno " + e.getMessage(),
                    500,
                    "/api/medicos/{id}",
                    LocalDateTime.now(),
                    null
            ));
        }
    }
    //Borra medico
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseGlobalDto> deleteMedico(@PathVariable Long id) {
        try {
            ResponseGlobalDto response = medicoService.deleteMedico(id);
            return ResponseEntity.status(response.getCodigo()).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseGlobalDto(
                    e.getMessage(),
                    400,
                    "/api/medicos/{id}",
                    LocalDateTime.now(),
                    null
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body(new ResponseGlobalDto(
                    "Error medico con id no encontrado :" + e.getMessage(),
                    404,
                    "/api/medicos/{id}",
                    LocalDateTime.now(),
                    null
            ));
        }
    }
}
