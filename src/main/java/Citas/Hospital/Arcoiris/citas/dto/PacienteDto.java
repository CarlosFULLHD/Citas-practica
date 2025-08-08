package Citas.Hospital.Arcoiris.citas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PacienteDto {
    private String name;
    private String lastname;
    private String dni;
    private LocalDate dateBirth;
    private String phoneNumber;
    private String email;
}
