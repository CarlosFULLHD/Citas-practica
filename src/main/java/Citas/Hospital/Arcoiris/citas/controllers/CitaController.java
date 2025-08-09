package Citas.Hospital.Arcoiris.citas.controllers;

import Citas.Hospital.Arcoiris.citas.dto.CitaDto;
import Citas.Hospital.Arcoiris.citas.interfacesService.InterfaceCita;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {
    private final InterfaceCita citaService;
    public CitaController(InterfaceCita citaService) {
        this.citaService = citaService;
    }
    //Crear cita
    @PostMapping
    public ResponseEntity<CitaDto> createCita(@Valid @RequestBody CitaDto citaDto){
        CitaDto create = citaService.createCita(citaDto);
        return ResponseEntity.status(201).body(create);
    }
    //Obtener todas las citas
    @GetMapping
    public ResponseEntity<List<CitaDto>> getAllCitas(){
        List<CitaDto> citas = citaService.getAllCitas();
        return ResponseEntity.ok(citas);
    }
    //Obtener cita por Id
    @GetMapping("/{id}")
    public ResponseEntity<CitaDto> getCitaById(@PathVariable Long id){
        CitaDto citaDto = citaService.getByIdCita(id);
        if(citaDto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(citaDto);
    }
    //Actualizar cita
    @PutMapping("/{id}")
    public ResponseEntity<CitaDto> updateCita(@PathVariable Long id,
                                              @Valid @RequestBody CitaDto citaDto){
        CitaDto citaActu = citaService.updateCita(id, citaDto);
        return ResponseEntity.ok(citaActu);
    }
    //Eliminar cita
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCita(@PathVariable Long id){
        citaService.deleteCita(id);
        return ResponseEntity.noContent().build();
    }
}
