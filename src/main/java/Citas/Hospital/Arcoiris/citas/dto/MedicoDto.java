package Citas.Hospital.Arcoiris.citas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MedicoDto {
    private String name;
    private String lastname;
    private String speciality;
    private String phoneNumber;
    private String email;
}
