package Citas.Hospital.Arcoiris.citas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CitaDto {
    private LocalDateTime dateTime;
    private String reasonForConsultation;
    private Long pacienteId;
    private Long medicoId;
}
