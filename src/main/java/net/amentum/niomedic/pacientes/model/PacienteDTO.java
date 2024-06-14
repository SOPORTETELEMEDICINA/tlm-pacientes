package net.amentum.niomedic.pacientes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PacienteDTO {
    private String idPaciente;
    private String nombreCompleto;
    private String telefono;
    private String email;
}