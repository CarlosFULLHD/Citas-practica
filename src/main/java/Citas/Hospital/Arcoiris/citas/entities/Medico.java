package Citas.Hospital.Arcoiris.citas.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medico", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String name;

    @Column(name = "apellidos", nullable = false, length = 50)
    private String lastname;

    @Column(name = "especialidad", nullable = false, length = 50)
    private String specialty;

    @Column(name = "numero_contacto", nullable = false, unique = true, length = 50)
    private String phoneNumber;

    @Column(name = "correo_electronico", nullable = false, unique = true,length = 50)
    private String email;

    @OneToMany(mappedBy = "medico")
    private List<Cita> citas;
}
