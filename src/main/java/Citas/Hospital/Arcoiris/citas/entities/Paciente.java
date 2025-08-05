package Citas.Hospital.Arcoiris.citas.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String name;

    @Column(name = "apellidos", nullable = false, length = 50)
    private String lastname;

    @Column(name = "dni", nullable = false, unique = true, length = 8)
    private String dni;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate dateBirth;

    @Column(name = "numero_contacto", nullable = false, unique = true, length = 9)
    private String phoneNumber;

    @Column(name = "correo_electronico", nullable = false, unique = true, length = 50)
    private String email;

    @OneToMany(mappedBy = "paciente")
    private List<Cita> citas;

}
