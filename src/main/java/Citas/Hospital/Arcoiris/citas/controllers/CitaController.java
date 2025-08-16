package Citas.Hospital.Arcoiris.citas.controllers;

import Citas.Hospital.Arcoiris.citas.dto.CitaDto;
import Citas.Hospital.Arcoiris.citas.dto.ResponseGlobalDto;
import Citas.Hospital.Arcoiris.citas.interfacesService.InterfaceCita;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/citas")
public class CitaController {
    private final InterfaceCita citaService;

    public CitaController(InterfaceCita citaService) {
        this.citaService = citaService;
    }

    //Crear cita
    @PostMapping
    public ResponseEntity<ResponseGlobalDto> createCita(@Valid @RequestBody CitaDto citaDto) {
        try {
            ResponseGlobalDto response = citaService.createCita(citaDto);
            return ResponseEntity.status(response.getCodigo()).body(response);
        } catch (IllegalArgumentException e) {
            ResponseGlobalDto responseError = new ResponseGlobalDto(
                    e.getMessage(),
                    400,
                    "/api/cita/{id}",
                    LocalDateTime.now(),
                    null
            );
            return ResponseEntity.badRequest().body(responseError);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseGlobalDto responseError = new ResponseGlobalDto(
                    "Error interno: " + e.getMessage(),
                    500,
                    "/api/cita/{id}",
                    LocalDateTime.now(),
                    null
            );
            return ResponseEntity.status(500).body(responseError);
        }
    }
    //Obtener todas las citas
    @GetMapping
    public ResponseEntity<ResponseGlobalDto> getAllCitas(){
        ResponseGlobalDto response = citaService.getAllCitas();
        return ResponseEntity.status(response.getCodigo()).body(response);
    }
    //Obtener cita por Id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseGlobalDto> getCitaById(@PathVariable Long id){
        try{
            ResponseGlobalDto response = citaService.getByIdCita(id);
            return ResponseEntity.status(response.getCodigo()).body(response);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new ResponseGlobalDto(
                    e.getMessage(),
                    400,
                    "/api/cita/{id}",
                    LocalDateTime.now(),
                    null
            ));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(404).body(new ResponseGlobalDto(
                    "Error cita con id no encontrado: " + e.getMessage(),
                    404,
                    "api/cita/{id}",
                    LocalDateTime.now(),
                    null
            ));
        }
    }
    //Actualizar cita
    @PutMapping("/{id}")
    public ResponseEntity<ResponseGlobalDto> updateCita(@PathVariable Long id,
                                              @Valid @RequestBody CitaDto citaDto){
        try{
            ResponseGlobalDto response = citaService.updateCita(id, citaDto);
            return ResponseEntity.status(response.getCodigo()).body(response);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new ResponseGlobalDto(
                    e.getMessage(),
                    400,
                    "/api/cita/{id}",
                    LocalDateTime.now(),
                    null
            ));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(404).body(new ResponseGlobalDto(
                    "Error cita con id no encontrado: " + e.getMessage(),
                    404,
                    "/api/cita/{id}",
                    LocalDateTime.now(),
                    null
            ));
        }
    }
    //Eliminar cita
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseGlobalDto> deleteCita(@PathVariable Long id){
        try{
            ResponseGlobalDto response = citaService.deleteCita(id);
            return ResponseEntity.status(response.getCodigo()).body(response);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new ResponseGlobalDto(
                    e.getMessage(),
                    400,
                    "/api/cita/{id}",
                    LocalDateTime.now(),
                    null
            ));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(404).body(new ResponseGlobalDto(
                    "Error cita con id no encontrado: " + e.getMessage(),
                    404,
                    "/api/cita/{id}",
                    LocalDateTime.now(),
                    null
            ));
        }
    }
}
