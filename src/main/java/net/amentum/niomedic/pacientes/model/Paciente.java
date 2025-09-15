package net.amentum.niomedic.pacientes.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {
   @Id
   @Column(name = "id_paciente")
   @GeneratedValue(generator = "UUID")
   @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
   private String idPaciente;
   private String nombre;
   private String apellidoPaterno;
   private String apellidoMaterno;
   private Date fechaNacimiento;
   private String lugarNacimiento;
   private String curp;
   private String sexo;
   private String religion;
   private String estadoCivil;
   private String telefonoLocal;
   private String telefonoCelular;
   private String email;
   private String rfc;
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaCreacion;
   @Column(unique = true, nullable = false)
   private long idUsuario;
   private Boolean activo;
   //nuevos campos
   private String padecimientoCronico;
   private String transfusiones;
   private Long idUnidadMedica;
   private String alergias;
   private String tipoSangre;
   private String afiliacion;
   private String numeroAfiliacion;
   private String userName;
   @Column(name = "numero_expediente", columnDefinition = "serial")
   @Generated(GenerationTime.INSERT)
   private Long numeroExpediente;
   //   campo compuesto de busqueda
   private String datosBusqueda;
   private Boolean es_titular;
    @Column(name = "id_device")
    private String idDevice;
   private String claveElector;

   private Boolean pacienteAtendido;

   //relaciones entre tablas
   @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "paciente")
   private Collection<DatosContacto> datosContactoList = new ArrayList<>();

   @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "paciente")
   private Collection<ServicioAdicionales> servicioAdicionalesList = new ArrayList<>();

   @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "paciente")
   private Collection<PersonasVivienda> personasViviendaList = new ArrayList<>();

   @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "paciente")
   private DatosAdicionales datosAdicionales;

//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "paciente")
//    private Domicilio domicilio;

   @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "paciente")
   private Collection<Domicilio> domicilioList = new ArrayList<>();


   @Override
   public String toString() {
      return "Paciente{" +
              "idPaciente='" + idPaciente + '\'' +
              ", nombre='" + nombre + '\'' +
              ", apellidoPaterno='" + apellidoPaterno + '\'' +
              ", apellidoMaterno='" + apellidoMaterno + '\'' +
              ", fechaNacimiento=" + fechaNacimiento +
              ", lugarNacimiento='" + lugarNacimiento + '\'' +
              ", curp='" + curp + '\'' +
              ", sexo='" + sexo + '\'' +
              ", religion='" + religion + '\'' +
              ", estadoCivil='" + estadoCivil + '\'' +
              ", telefonoLocal='" + telefonoLocal + '\'' +
              ", telefonoCelular='" + telefonoCelular + '\'' +
              ", email='" + email + '\'' +
              ", rfc='" + rfc + '\'' +
              ", fechaCreacion=" + fechaCreacion +
              ", idUsuario=" + idUsuario +
              ", activo=" + activo +
              ", padecimientoCronico='" + padecimientoCronico + '\'' +
              ", alergias='" + alergias + '\'' +
              ", tipoSangre='" + tipoSangre + '\'' +
              ", afiliacion='" + afiliacion + '\'' +
              ", numeroAfiliacion='" + numeroAfiliacion + '\'' +
              ", userName='" + userName + '\'' +
              ", numeroExpediente=" + numeroExpediente +
              ", datosBusqueda='" + datosBusqueda + '\'' +
              ", esTutor=" + es_titular +
              ", idDevice='" + idDevice + '\'' +
              ", claveElector='" + claveElector + '\'' +
              ", pacienteAtendido=" + pacienteAtendido +
              '}';
   }
}
