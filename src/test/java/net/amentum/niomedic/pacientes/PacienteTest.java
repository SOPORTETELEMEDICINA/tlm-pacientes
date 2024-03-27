package net.amentum.niomedic.pacientes;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.amentum.niomedic.pacientes.views.DatosAdicionalesView;
import net.amentum.niomedic.pacientes.views.DatosContactoView;
import net.amentum.niomedic.pacientes.views.DomicilioView;
import net.amentum.niomedic.pacientes.views.PacienteView;
import net.amentum.niomedic.pacientes.views.PersonasViviendaView;
import net.amentum.niomedic.pacientes.views.ServicioAdicionalesView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {PacientesApplication.class})
public class PacienteTest {
   private final MediaType jsonType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf-8"));
   public MockMvc mockMvc;
   @Autowired
   private WebApplicationContext webApplicationContext;
   @Autowired
   private ObjectMapper objectMapper;

   @Before
   public void setup() {
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
   }

   /**
    * Prueba para generar un nuevo paciente
    */
   @Test
   public void createPaciente() throws Exception {
      Integer id = 15;
      PacienteView pacienteView = new PacienteView();
//      // PacientePrueba = PP
//      pacienteView.setNombre("PP-Nombre-" + id);
//      pacienteView.setApellidoPaterno("PP-Apellido Paterno-" + id);
//      pacienteView.setApellidoMaterno("PP-Apellido Materno-" + id);
//      pacienteView.setFechaNacimiento(new Date());
//      pacienteView.setLugarNacimiento("PP-Lugar-" + id);
////      pacienteView.setCurp("PP-CURP-" + id);
//      pacienteView.setCurp("PP-CURP-2");
//      pacienteView.setSexo("PP-Rico-" + id);
//      pacienteView.setReligion("PP-Religion-" + id);
//      pacienteView.setEstadoCivil("PP-Edo Civil-" + id);
//      pacienteView.setTelefonoLocal("PP-Telef Local-" + id);
//      pacienteView.setTelefonoCelular("PP-Telef Celular-" + id);
//      pacienteView.setEmail("PP-Email-" + id);
//      pacienteView.setRfc("PP-RFC-" + id);
//      pacienteView.setFechaCreacion(new Date());
//      pacienteView.setIdUsuario(123456 + id);
////        pacienteView.setIdDomicilio("PP-Domicilio-"+id);
//      pacienteView.setActivo(true);
//      //nuevos campos
//      pacienteView.setPadecimientoCronico("PP-padecimiento-cronico-"+id);
//      pacienteView.setAlergias("PP-alergias-"+id);
//      pacienteView.setTipoSangre("PP-tipo-sangre-"+id);
//      pacienteView.setAfiliacion("PP-afiliacion-"+id);
//      pacienteView.setNumeroAfiliacion("PP-numero-afiliacion-"+id);
//      pacienteView.setNumeroExpediente(id*100L);
//
//      // DatosContactoPrueba = DCP
//      DatosContactoView datosContactoView = new DatosContactoView();
//      datosContactoView.setNombre("DCP-Nombre-" + id);
//      datosContactoView.setParentesco("DCP-Parentesco-" + id);
//      datosContactoView.setEdad(99);
//      datosContactoView.setConvivencia("DCP-Convivencia-" + id);
//      datosContactoView.setLlamarCasoEmergencia(911);
//      datosContactoView.setCuentaConLlaves(true);
//      datosContactoView.setTelefonoLocal("DCP-Telef Local-" + id);
//      datosContactoView.setTelefonoCelular("DCP-Telef Celular-" + id);
//      datosContactoView.setTelefonoOficina("DCP-Telef Oficina-" + id);
//      datosContactoView.setTipoApoyoMaterial("DCP-Tipo Apoyo Material-" + id);
//      datosContactoView.setTipoApoyoEmocional("DCP-Tipo Apoyo Emocional-" + id);
//      datosContactoView.setTipoApoyoSocial("DCP-Tipo Apoyo Social-" + id);
//      datosContactoView.setTipoApoyoInstrumental("DCP-Tipo Apoyo Instrumental-" + id);
//      datosContactoView.setFechaCreacion(new Date());
//
//      DatosContactoView datosContactoView2 = new DatosContactoView();
//      datosContactoView2.setNombre("DCP-2-Nombre-" + id);
//      datosContactoView2.setParentesco("DCP-2-Parentesco-" + id);
//      datosContactoView2.setEdad(99);
//      datosContactoView2.setConvivencia("DCP-2-Convivencia-" + id);
//      datosContactoView2.setLlamarCasoEmergencia(911);
//      datosContactoView2.setCuentaConLlaves(true);
//      datosContactoView2.setTelefonoLocal("DCP-2-Telef Local-" + id);
//      datosContactoView2.setTelefonoCelular("DCP-2-Telef Celular-" + id);
//      datosContactoView2.setTelefonoOficina("DCP-2-Telef Oficina-" + id);
//      datosContactoView2.setTipoApoyoMaterial("DCP-2-Tipo Apoyo Material-" + id);
//      datosContactoView2.setTipoApoyoEmocional("DCP-2-Tipo Apoyo Emocional-" + id);
//      datosContactoView2.setTipoApoyoSocial("DCP-2-Tipo Apoyo Social-" + id);
//      datosContactoView2.setTipoApoyoInstrumental("DCP-2-Tipo Apoyo Instrumental-" + id);
//      datosContactoView2.setFechaCreacion(new Date());
//
//      ArrayList<DatosContactoView> datosContactoViewArrayList = new ArrayList<>();
//      datosContactoViewArrayList.add(datosContactoView);
//      datosContactoViewArrayList.add(datosContactoView2);
//
//      pacienteView.setDatosContactoViewList(datosContactoViewArrayList);
//
//      // ServicioAdicionalesPrueba = SAP
//      ServicioAdicionalesView servicioAdicionalesView = new ServicioAdicionalesView();
//      servicioAdicionalesView.setTipoServicio("SAP-Tipo Servicio-" + id);
//      servicioAdicionalesView.setNombre("SAP-Nombre-" + id);
//      servicioAdicionalesView.setDomicilio("SAP-Domicilio-" + id);
//      servicioAdicionalesView.setComentarios("SAP-Comentarios-" + id);
//      servicioAdicionalesView.setFechaCreacion(new Date());
//
//      ArrayList<ServicioAdicionalesView> servicioAdicionalesViewArrayList = new ArrayList<>();
//      servicioAdicionalesViewArrayList.add(servicioAdicionalesView);
//      pacienteView.setServicioAdicionalesViewList(servicioAdicionalesViewArrayList);
//
//      // PersonasViviendaPrueba = PVP
//      PersonasViviendaView personasViviendaView = new PersonasViviendaView();
//      personasViviendaView.setParentesco("PVP-Parentesco-" + id);
//      personasViviendaView.setEdad(99);
//      personasViviendaView.setConvivencia("PVP-Convivencia-" + id);
//      personasViviendaView.setFechaCreacion(new Date());
////      otro
//      PersonasViviendaView personasViviendaView2 = new PersonasViviendaView();
//      personasViviendaView2.setParentesco("PVP-2-Parentesco-" + id);
//      personasViviendaView2.setEdad(99);
//      personasViviendaView2.setConvivencia("PVP-2-Convivencia-" + id);
//      personasViviendaView2.setFechaCreacion(new Date());
//
//      ArrayList<PersonasViviendaView> personasViviendaViewArrayList = new ArrayList<>();
//      personasViviendaViewArrayList.add(personasViviendaView);
//      personasViviendaViewArrayList.add(personasViviendaView2);
//      pacienteView.setPersonasViviendaViewList(personasViviendaViewArrayList);
//
//      // DatosAdicionales = DAP
//      DatosAdicionalesView datosAdicionalesView = new DatosAdicionalesView();
//      datosAdicionalesView.setEscolaridad("DAP-Escolaridad-" + id);
//      datosAdicionalesView.setOcupacion("DAP-Ocupacion-" + id);
//      datosAdicionalesView.setOcupacionAnterior("DAP-Ocupacion Anterior-" + id);
//      datosAdicionalesView.setFechaCreacion(new Date());
//      datosAdicionalesView.setOrigenEtnico("DAP-origen-etnico-" + id);
//      datosAdicionalesView.setViveSolo(Boolean.TRUE);
//
//      pacienteView.setDatosAdicionalesView(datosAdicionalesView);
//
//      // Domicilio = DP
////      DomicilioView domicilioView = new DomicilioView();
////      domicilioView.setDomicilio("DP-domicilio-" + id);
////      domicilioView.setColonia("DP-colonia-" + id);
////      domicilioView.setMunicipio("DP-municipio-" + id);
////      domicilioView.setEstado("DP-estado-" + id);
////      domicilioView.setPais("DP-pais-" + id);
////      domicilioView.setCp("DP-codigo postal-" + id);
////      domicilioView.setFechaCreacion(new Date());
////
////      pacienteView.setDomicilioView(domicilioView);
//
//      DomicilioView domicilioView = new DomicilioView();
//      domicilioView.setDomicilio("DP-A-domicilio-" + id);
//      domicilioView.setColonia("DP-A-colonia-" + id);
//      domicilioView.setMunicipio("DP-A-municipio-" + id);
//      domicilioView.setEstado("DP-A-estado-" + id);
//      domicilioView.setPais("DP-A-pais-" + id);
//      domicilioView.setCp("DP-A-codigo postal-" + id);
//      domicilioView.setFechaCreacion(new Date());
//      domicilioView.setActivo(Boolean.TRUE);
//
//      DomicilioView domicilioView2 = new DomicilioView();
//      domicilioView2.setDomicilio("DP-B-domicilio-" + id);
//      domicilioView2.setColonia("DP-B-colonia-" + id);
//      domicilioView2.setMunicipio("DP-B-municipio-" + id);
//      domicilioView2.setEstado("DP-B-estado-" + id);
//      domicilioView2.setPais("DP-B-pais-" + id);
//      domicilioView2.setCp("DP-B-codigo postal-" + id);
//      domicilioView2.setFechaCreacion(new Date());
//      domicilioView2.setActivo(Boolean.FALSE);
//
//      ArrayList<DomicilioView> domicilioViewArrayList = new ArrayList<>();
//      domicilioViewArrayList.add(domicilioView);
//      domicilioViewArrayList.add(domicilioView2);
//      pacienteView.setDomicilioViewList(domicilioViewArrayList);

      // PacientePrueba = PP
      pacienteView.setNombre("Juan");
      pacienteView.setApellidoPaterno("Togata");
      pacienteView.setApellidoMaterno("Deltu");
      pacienteView.setUserName("Juant");
      SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
      String dateS = "14/09/1978";
      Date dateD = sdf.parse(dateS);
      pacienteView.setFechaNacimiento(dateD);
      pacienteView.setLugarNacimiento("Guadalajara");
//      pacienteView.setCurp("PP-CURP-" + id);
      pacienteView.setCurp("GAVMP760914HGOXX3"+id);
      pacienteView.setSexo("Masculino");
      pacienteView.setReligion("Católico");
      pacienteView.setEstadoCivil("Casado");
      pacienteView.setTelefonoLocal("771-1234567");
      pacienteView.setTelefonoCelular("771-0987654");
      pacienteView.setEmail("jSanchez@gmail.com");
//      pacienteView.setRfc("GAVMP760914G9");
      pacienteView.setRfc("GAVMP760914G9"+id);
      pacienteView.setFechaCreacion(new Date());
      pacienteView.setIdUsuario(1L + id);
//        pacienteView.setIdDomicilio("PP-Domicilio-"+id);
      pacienteView.setActivo(true);
      //nuevos campos
      pacienteView.setPadecimientoCronico("Diabetes");
      pacienteView.setAlergias("Polen, Ciprofloxaxino");
      pacienteView.setTipoSangre("RH O negativo");
      pacienteView.setAfiliacion("IMSS");
      pacienteView.setNumeroAfiliacion("1234567890");
//      pacienteView.setNumeroExpediente(331843L);

      // DatosContactoPrueba = DCP
      DatosContactoView datosContactoView = new DatosContactoView();
      datosContactoView.setNombre("Jaqueline Bejarano Paredes");
      datosContactoView.setParentesco("Esposa");
      datosContactoView.setEdad(33);
      datosContactoView.setConvivencia("Hogar, Familiar");
      datosContactoView.setLlamarCasoEmergencia(1);
      datosContactoView.setCuentaConLlaves(true);
      datosContactoView.setTelefonoLocal("771-1234567");
      datosContactoView.setTelefonoCelular("771-0987654");
      datosContactoView.setTelefonoOficina("555-543210");
      datosContactoView.setTipoApoyoMaterial("Ninguno");
      datosContactoView.setTipoApoyoEmocional("Ninguno");
      datosContactoView.setTipoApoyoSocial("Ninguno");
      datosContactoView.setTipoApoyoInstrumental("Ninguno");
      datosContactoView.setFechaCreacion(new Date());
      datosContactoView.setPrioridad("1ERO");

      DatosContactoView datosContactoView2 = new DatosContactoView();
      datosContactoView2.setNombre("Victor Gallegos Vargas");
      datosContactoView2.setParentesco("Hermano");
      datosContactoView2.setEdad(27);
      datosContactoView2.setConvivencia("Hogar, Familiar");
      datosContactoView2.setLlamarCasoEmergencia(2);
      datosContactoView2.setCuentaConLlaves(true);
      datosContactoView2.setTelefonoLocal("771-1234567");
      datosContactoView2.setTelefonoCelular("771-0987654");
      datosContactoView2.setTelefonoOficina("555-543210");
      datosContactoView2.setTipoApoyoMaterial("Ninguno");
      datosContactoView2.setTipoApoyoEmocional("Ninguno");
      datosContactoView2.setTipoApoyoSocial("Ninguno");
      datosContactoView2.setTipoApoyoInstrumental("Ninguno");
      datosContactoView2.setFechaCreacion(new Date());
      datosContactoView2.setPrioridad("2DO");

      ArrayList<DatosContactoView> datosContactoViewArrayList = new ArrayList<>();
      datosContactoViewArrayList.add(datosContactoView);
      datosContactoViewArrayList.add(datosContactoView2);

      pacienteView.setDatosContactoViewList(datosContactoViewArrayList);

      // ServicioAdicionalesPrueba = SAP
      ServicioAdicionalesView servicioAdicionalesView = new ServicioAdicionalesView();
      servicioAdicionalesView.setTipoServicio("Ninguno");
      servicioAdicionalesView.setNombre("Ninguno");
      servicioAdicionalesView.setDomicilio("Ninguno");
      servicioAdicionalesView.setComentarios("Ninguno");
      servicioAdicionalesView.setFechaCreacion(new Date());

      ArrayList<ServicioAdicionalesView> servicioAdicionalesViewArrayList = new ArrayList<>();
      servicioAdicionalesViewArrayList.add(servicioAdicionalesView);
      pacienteView.setServicioAdicionalesViewList(servicioAdicionalesViewArrayList);

      // PersonasViviendaPrueba = PVP
      PersonasViviendaView personasViviendaView = new PersonasViviendaView();
      personasViviendaView.setParentesco("Hijo");
      personasViviendaView.setEdad(10);
      personasViviendaView.setConvivencia("Familiar");
      personasViviendaView.setFechaCreacion(new Date());
//      otro
      PersonasViviendaView personasViviendaView2 = new PersonasViviendaView();
      personasViviendaView2.setParentesco("Hija");
      personasViviendaView2.setEdad(8);
      personasViviendaView2.setConvivencia("Familiar");
      personasViviendaView2.setFechaCreacion(new Date());

      ArrayList<PersonasViviendaView> personasViviendaViewArrayList = new ArrayList<>();
      personasViviendaViewArrayList.add(personasViviendaView);
      personasViviendaViewArrayList.add(personasViviendaView2);
      pacienteView.setPersonasViviendaViewList(personasViviendaViewArrayList);

      // DatosAdicionales = DAP
      DatosAdicionalesView datosAdicionalesView = new DatosAdicionalesView();
      datosAdicionalesView.setEscolaridad("Ingeniero Civil");
      datosAdicionalesView.setOcupacion("Construciones");
      datosAdicionalesView.setOcupacionAnterior("SCT");
      datosAdicionalesView.setFechaCreacion(new Date());
      datosAdicionalesView.setOrigenEtnico("Caucásico");
      datosAdicionalesView.setViveSolo(Boolean.TRUE);
      datosAdicionalesView.setLocalidad("Pachuquita");
      datosAdicionalesView.setLenguaIndigena("Nauatl");
      datosAdicionalesView.setAntecedentes("Robachicos");

      pacienteView.setDatosAdicionalesView(datosAdicionalesView);

      // Domicilio = DP
//      DomicilioView domicilioView = new DomicilioView();
//      domicilioView.setDomicilio("DP-domicilio-" + id);
//      domicilioView.setColonia("DP-colonia-" + id);
//      domicilioView.setMunicipio("DP-municipio-" + id);
//      domicilioView.setEstado("DP-estado-" + id);
//      domicilioView.setPais("DP-pais-" + id);
//      domicilioView.setCp("DP-codigo postal-" + id);
//      domicilioView.setFechaCreacion(new Date());
//
//      pacienteView.setDomicilioView(domicilioView);

      DomicilioView domicilioView = new DomicilioView();
      domicilioView.setDomicilio("Puerto Madero #204");
      domicilioView.setColonia("Real del Valle");
      domicilioView.setMunicipio("Mineral de la Reforma");
      domicilioView.setEstado("Hidalgo");
      domicilioView.setPais("México");
      domicilioView.setCp("42083");
      domicilioView.setFechaCreacion(new Date());
      domicilioView.setActivo(Boolean.TRUE);

      DomicilioView domicilioView2 = new DomicilioView();
      domicilioView2.setDomicilio("Corredor del Puerto #693");
      domicilioView2.setColonia("Villas del Corredor");
      domicilioView2.setMunicipio("Tulancingo de Bravo");
      domicilioView2.setEstado("Hidalgo");
      domicilioView2.setPais("México");
      domicilioView2.setCp("42080");
      domicilioView2.setFechaCreacion(new Date());
      domicilioView2.setActivo(Boolean.TRUE);

      ArrayList<DomicilioView> domicilioViewArrayList = new ArrayList<>();
      domicilioViewArrayList.add(domicilioView);
      domicilioViewArrayList.add(domicilioView2);
      pacienteView.setDomicilioViewList(domicilioViewArrayList);

      System.out.println(objectMapper.writeValueAsString(pacienteView));

      // enviando-recibiendo los datos de prueba
      mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
         .contentType(jsonType)
         .content(objectMapper.writeValueAsString(pacienteView)))
         .andExpect(MockMvcResultMatchers.status().isCreated())
         .andDo(MockMvcResultHandlers.print());
   }

   @Test
   public void updatePaciente() throws Exception {
      Integer id = 16;
      String idPaciente = "a4ea4d51-d3d5-44a0-bbc5-c7e8d7f1421e";

      String setIdDatosContacto = "686fba51-de92-4666-bcd2-79065c56785d";
      String setIdDatosContacto2 = "b49d45d3-1cd2-40be-910e-5b98c6c93675";

      String setIdPersonasVivienda = "858940c5-6143-4f78-ad1b-7dd1b21dadee";
      String setIdPersonasVivienda2 = "d4c2327e-3002-4bb2-9965-4f8772ca9061";

      String setIdServicioAdicionales = "a9f411db-b91f-4c9c-9714-4ddf9c4993be";

      String setIdDatosAdicionales = "4ace5084-ba31-4a62-9d34-61e8a05a914f";

      String setIdDomicilio = "dacac56b-0e49-4045-a0d9-df303e5f3580";
      String setIdDomicilio2 = "f38122a4-dba1-4fcf-aef6-0740dd9656d2";

      PacienteView pacienteView = new PacienteView();
      // PacientePrueba = PP
      pacienteView.setNombre("PP-Nombre-U-" + id);
      pacienteView.setApellidoPaterno("PP-Apellido Paterno-U-" + id);
      pacienteView.setApellidoMaterno("PP-Apellido Materno-U-" + id);
      pacienteView.setFechaNacimiento(new Date());
      pacienteView.setLugarNacimiento("PP-Lugar-U-" + id);
      pacienteView.setCurp("PP-CURP-U-" + id);
      pacienteView.setSexo("PP-Rico-U-" + id);
      pacienteView.setReligion("PP-Religion-U-" + id);
      pacienteView.setEstadoCivil("PP-Edo Civil-U-" + id);
      pacienteView.setTelefonoLocal("PP-Telef Local-U-" + id);
      pacienteView.setTelefonoCelular("PP-Telef Celular-U-" + id);
      pacienteView.setEmail("PP-Email-U-" + id);
      pacienteView.setRfc("PP-RFC-U-" + id);
      pacienteView.setFechaCreacion(new Date());
      pacienteView.setIdUsuario(13579L + id);
//    pacienteView.setIdDomicilio("PP-Domicilio-U-" + id);
      pacienteView.setActivo(true);

      // DatosContactoPrueba = DCP
      DatosContactoView datosContactoView = new DatosContactoView();
      datosContactoView.setIdDatosContacto(setIdDatosContacto);
      datosContactoView.setNombre("DCP-Nombre-U2-" + id);
      datosContactoView.setParentesco("DCP-Parentesco-U-" + id);
      datosContactoView.setEdad(99);
      datosContactoView.setConvivencia("DCP-Convivencia-U-" + id);
      datosContactoView.setLlamarCasoEmergencia(911);
      datosContactoView.setCuentaConLlaves(false);
      datosContactoView.setTelefonoLocal("DCP-Telef Local-U-" + id);
      datosContactoView.setTelefonoCelular("DCP-Telef Celular-U-" + id);
      datosContactoView.setTelefonoOficina("DCP-Telef Oficina-U-" + id);
      datosContactoView.setTipoApoyoMaterial("DCP-Tipo Apoyo Material-U-" + id);
      datosContactoView.setTipoApoyoEmocional("DCP-Tipo Apoyo Emocional-U-" + id);
      datosContactoView.setTipoApoyoSocial("DCP-Tipo Apoyo Social-U-" + id);
      datosContactoView.setTipoApoyoInstrumental("DCP-Tipo Apoyo Instrumental-U-" + id);
      datosContactoView.setFechaCreacion(new Date());
//      Otro mas de DatosContato
      DatosContactoView datosContactoView2 = new DatosContactoView();
      datosContactoView2.setIdDatosContacto(setIdDatosContacto2);
      datosContactoView2.setNombre("DCP-2-Nombre-U-" + id);
      datosContactoView2.setParentesco("DCP-2-Parentesco-U-" + id);
      datosContactoView2.setEdad(99);
      datosContactoView2.setConvivencia("DCP-2-Convivencia-U-" + id);
      datosContactoView2.setLlamarCasoEmergencia(911);
      datosContactoView2.setCuentaConLlaves(false);
      datosContactoView2.setTelefonoLocal("DCP-2-Telef Local-" + id);
      datosContactoView2.setTelefonoCelular("DCP-2-Telef Celular-" + id);
      datosContactoView2.setTelefonoOficina("DCP-2-Telef Oficina-" + id);
      datosContactoView2.setTipoApoyoMaterial("DCP-2-Tipo Apoyo Material-" + id);
      datosContactoView2.setTipoApoyoEmocional("DCP-2-Tipo Apoyo Emocional-" + id);
      datosContactoView2.setTipoApoyoSocial("DCP-2-Tipo Apoyo Social-" + id);
      datosContactoView2.setTipoApoyoInstrumental("DCP-2-Tipo Apoyo Instrumental-" + id);
      datosContactoView2.setFechaCreacion(new Date());
////      Otro mas de DatosContato
//      DatosContactoView datosContactoView3 = new DatosContactoView();
////      datosContactoView3.setIdDatosContacto(setIdDatosContacto2);
//      datosContactoView3.setNombre("DCP-3-Nombre-U-" + id);
//      datosContactoView3.setParentesco("DCP-3-Parentesco-U-" + id);
//      datosContactoView3.setEdad(99);
//      datosContactoView3.setConvivencia("DCP-3-Convivencia-U-" + id);
//      datosContactoView3.setLlamarCasoEmergencia(911);
//      datosContactoView3.setCuentaConLlaves(true);
//      datosContactoView3.setTelefonoLocal("DCP-3-Telef Local-" + id);
//      datosContactoView3.setTelefonoCelular("DCP-3-Telef Celular-" + id);
//      datosContactoView3.setTelefonoOficina("DCP-3-Telef Oficina-" + id);
//      datosContactoView3.setTipoApoyoMaterial("DCP-3-Tipo Apoyo Material-" + id);
//      datosContactoView3.setTipoApoyoEmocional("DCP-3-Tipo Apoyo Emocional-" + id);
//      datosContactoView3.setTipoApoyoSocial("DCP-3-Tipo Apoyo Social-" + id);
//      datosContactoView3.setTipoApoyoInstrumental("DCP-3-Tipo Apoyo Instrumental-" + id);
//      datosContactoView3.setFechaCreacion(new Date());

      ArrayList<DatosContactoView> datosContactoViewArrayList = new ArrayList<>();
      datosContactoViewArrayList.add(datosContactoView);
      datosContactoViewArrayList.add(datosContactoView2);
//      datosContactoViewArrayList.add(datosContactoView3);
      pacienteView.setDatosContactoViewList(datosContactoViewArrayList);

      // ServicioAdicionalesPrueba = SAP
      ServicioAdicionalesView servicioAdicionalesView = new ServicioAdicionalesView();
      servicioAdicionalesView.setIdServicioAdicionales(setIdServicioAdicionales);
      servicioAdicionalesView.setTipoServicio("SAP-Tipo Servicio-U-" + id);
      servicioAdicionalesView.setNombre("SAP-Nombre-U-" + id);
      servicioAdicionalesView.setDomicilio("SAP-Domicilio-U-" + id);
      servicioAdicionalesView.setComentarios("SAP-Comentarios-U-" + id);
      servicioAdicionalesView.setFechaCreacion(new Date());

      ArrayList<ServicioAdicionalesView> servicioAdicionalesViewArrayList = new ArrayList<>();
      servicioAdicionalesViewArrayList.add(servicioAdicionalesView);
      pacienteView.setServicioAdicionalesViewList(servicioAdicionalesViewArrayList);

      // PersonasViviendaPrueba = PVP
      PersonasViviendaView personasViviendaView = new PersonasViviendaView();
      personasViviendaView.setIdPersonasVivienda(setIdPersonasVivienda);
      personasViviendaView.setParentesco("PVP-Parentesco-U-" + id);
      personasViviendaView.setEdad(69);
      personasViviendaView.setConvivencia("PVP-Convivencia-U-" + id);
      personasViviendaView.setFechaCreacion(new Date());
//      otro
      PersonasViviendaView personasViviendaView2 = new PersonasViviendaView();
      personasViviendaView2.setIdPersonasVivienda(setIdPersonasVivienda2);
      personasViviendaView2.setParentesco("PVP-2-Parentesco-U-" + id);
      personasViviendaView2.setEdad(99);
      personasViviendaView2.setConvivencia("PVP-2-Convivencia-U-" + id);
      personasViviendaView2.setFechaCreacion(new Date());
//      otro
//      PersonasViviendaView personasViviendaView3 = new PersonasViviendaView();
////      personasViviendaView3.setIdPersonasVivienda(setIdPersonasVivienda2);
//      personasViviendaView3.setParentesco("PVP-3-Parentesco-U-" + id);
//      personasViviendaView3.setEdad(33);
//      personasViviendaView3.setConvivencia("PVP-3-Convivencia-U-" + id);
//      personasViviendaView3.setFechaCreacion(new Date());

      ArrayList<PersonasViviendaView> personasViviendaViewArrayList = new ArrayList<>();
      personasViviendaViewArrayList.add(personasViviendaView);
      personasViviendaViewArrayList.add(personasViviendaView2);
//      personasViviendaViewArrayList.add(personasViviendaView3);
      pacienteView.setPersonasViviendaViewList(personasViviendaViewArrayList);

      // DatosAdicionalesPrueba = DAP
      DatosAdicionalesView datosAdicionalesView = new DatosAdicionalesView();
      datosAdicionalesView.setIdDatosAdicionales(setIdDatosAdicionales);
      datosAdicionalesView.setEscolaridad("DAP-Escolaridad-U-" + id);
      datosAdicionalesView.setOcupacion("DAP-Ocupacion-U-" + id);
      datosAdicionalesView.setOcupacionAnterior("DAP-Ocupacion Anterior-U-" + id);
      datosAdicionalesView.setFechaCreacion(new Date());
      datosAdicionalesView.setOrigenEtnico("DAP-origen-etnico-U-" + id);
      datosAdicionalesView.setViveSolo(Boolean.FALSE);

      pacienteView.setDatosAdicionalesView(datosAdicionalesView);

      // Domicilio = DP
//      DomicilioView domicilioView = new DomicilioView();
//      domicilioView.setIdDomicilio(setIdDomicilio);
//      domicilioView.setDomicilio("DP-domicilio-U-" + id);
//      domicilioView.setColonia("DP-colonia-U-" + id);
//      domicilioView.setMunicipio("DP-municipio-U-" + id);
//      domicilioView.setEstado("DP-estado-U-" + id);
//      domicilioView.setPais("DP-pais-U-" + id);
//      domicilioView.setCp("DP-codigo postal-U-" + id);
//      domicilioView.setFechaCreacion(new Date());
//
//      pacienteView.setDomicilioView(domicilioView);

      DomicilioView domicilioView = new DomicilioView();
      domicilioView.setIdDomicilio(setIdDomicilio);
      domicilioView.setDomicilio("DP-domicilio-U-" + id);
      domicilioView.setColonia("DP-colonia-U-" + id);
      domicilioView.setMunicipio("DP-municipio-U-" + id);
      domicilioView.setEstado("DP-estado-U-" + id);
      domicilioView.setPais("DP-pais-U-" + id);
      domicilioView.setCp("DP-codigo postal-U-" + id);
      domicilioView.setFechaCreacion(new Date());

      DomicilioView domicilioView2 = new DomicilioView();
      domicilioView2.setIdDomicilio(setIdDomicilio2);
      domicilioView2.setDomicilio("DP-2-domicilio-U-" + id);
      domicilioView2.setColonia("DP-2-colonia-U-" + id);
      domicilioView2.setMunicipio("DP-2-municipio-U-" + id);
      domicilioView2.setEstado("DP-2-estado-U-" + id);
      domicilioView2.setPais("DP-2-pais-U-" + id);
      domicilioView2.setCp("DP-2-codigo postal-U-" + id);
      domicilioView2.setFechaCreacion(new Date());

      ArrayList<DomicilioView> domicilioViewArrayList = new ArrayList<>();
      domicilioViewArrayList.add(domicilioView);
      domicilioViewArrayList.add(domicilioView2);
      pacienteView.setDomicilioViewList(domicilioViewArrayList);

      pacienteView.setNumeroExpediente(id.longValue());
      mockMvc.perform(MockMvcRequestBuilders.put("/pacientes/" + idPaciente)
         .contentType(jsonType)
         .content(objectMapper.writeValueAsString(pacienteView)))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
   }


   @Test
   public void getDetailsPacienteById() throws Exception {
      String idPaciente = "6a709c31-b989-4202-868a-0326eb5ab1ac";
      mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/" + idPaciente))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
   }

   @Test
   public void getDetailsPacienteByIdUsuario() throws Exception {
      String idUsuario = "378921962";
      mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/obtenerPorIdUsuario/" + idUsuario))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
   }

   @Test
   public void getDetailsPacienteByCurp() throws Exception {
      String curp = "GAVMP760914HGOXX36";
      mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/obtenerPorCURP/" + curp))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
   }


   @Test
   public void getPacientePage() throws Exception {
//      "/pacientes/search?active=&datosBusqueda=&nombre=&apellidoPaterno=&apellidoMaterno=&telefonoMovil=&telefonoFijo=&correo=&rfc=&curp=&page&size&orderColumn=&orderType="
//      mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/page?active=&name=pedro&page=&size=&orderColumn=nombre&orderType=desc")
//      mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/page?active=true&page=&size=&orderColumn=&orderType=ninguno")
//      mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/page?datosBusqueda=&active=true&page=&size=&orderColumn=&orderType=ninguno")
      mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/page?datosBusqueda=uno&active=&page=&size=&orderColumn=&orderType=desc")
         .contentType(jsonType))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
   }

   @Test
   public void createDatosAdicionales() throws Exception {
      String id = "1";
      String idPaciente = "eccf2241-c810-4eba-8892-919b038532d0";

      DatosAdicionalesView datosAdicionalesView = new DatosAdicionalesView();
//      datosAdicionalesView.setIdDatosAdicionales("7f62906b-bccd-4789-9b5f-4a116a3de365-" + id);
      datosAdicionalesView.setEscolaridad("DA-Escolaridad-Only-" + id);
      datosAdicionalesView.setOcupacion("DA-Ocupacion-Only-" + id);
      datosAdicionalesView.setOcupacionAnterior("DA-OcupacionAnterior-Only-" + id);
      datosAdicionalesView.setFechaCreacion(new Date());
      datosAdicionalesView.setOrigenEtnico("DA-origen-etnico-Only-" + id);
      datosAdicionalesView.setViveSolo(Boolean.TRUE);
      datosAdicionalesView.setLocalidad("DA-localidad-Only-" + id);
      datosAdicionalesView.setLenguaIndigena("DA-lengua-indigena-Only-" + id);
      datosAdicionalesView.setAntecedentes("DA-antecedentes-Only-" + id);


      System.out.println("===>>>" + objectMapper.writeValueAsString(datosAdicionalesView));


//      mockMvc.perform(MockMvcRequestBuilders.post("/pacientes/" + idPaciente + "/datos-adicionales")
//         .contentType(jsonType)
//         .content(objectMapper.writeValueAsString(datosAdicionalesView)))
//         .andExpect(MockMvcResultMatchers.status().isCreated())
//         .andDo(MockMvcResultHandlers.print());

   }

   @Test
   public void updateDatosAdicionales() throws Exception {
      String id = "111";
      String idPaciente = "2f2100ab-25d3-419d-839d-d12b7c9662cd";
      String setIdDatosAdicionales = "da46bf5f-b841-469c-a0f3-138a461b6135";

      DatosAdicionalesView datosAdicionalesView = new DatosAdicionalesView();
//      datosAdicionalesView.setIdDatosAdicionales(setIdDatosAdicionales);
      datosAdicionalesView.setEscolaridad("DA-Escolaridad-Only-U-" + id);
      datosAdicionalesView.setOcupacion("DA-Ocupacion-Only-U-" + id);
      datosAdicionalesView.setOcupacionAnterior("DA-OcupacionAnterior-Only-U-" + id);
      datosAdicionalesView.setFechaCreacion(new Date());
      datosAdicionalesView.setOrigenEtnico("DA-origen-etnico-Only-U-" + id);
      datosAdicionalesView.setViveSolo(Boolean.TRUE);

      mockMvc.perform(MockMvcRequestBuilders.put("/pacientes/" + idPaciente + "/datos-adicionales")
         .contentType(jsonType)
         .content(objectMapper.writeValueAsString(datosAdicionalesView)))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
   }


   @Test
   public void createServicioAdicionales() throws Exception {
      String id = "1";
      String idPaciente = "b9b8a8cd-4809-468c-b374-5669aa9b61a8";
      ServicioAdicionalesView servicioAdicionalesView = new ServicioAdicionalesView();
      servicioAdicionalesView.setTipoServicio("SAP-Tipo Servicio-Only-" + id);
      servicioAdicionalesView.setNombre("SAP-Nombre-Only-" + id);
      servicioAdicionalesView.setDomicilio("SAP-Domicilio-Only-" + id);
      servicioAdicionalesView.setComentarios("SAP-Comentarios-Only-" + id);
      servicioAdicionalesView.setFechaCreacion(new Date());

      ServicioAdicionalesView servicioAdicionalesView2 = new ServicioAdicionalesView();
      servicioAdicionalesView2.setTipoServicio("SAP-2-Tipo Servicio-Only-" + id);
      servicioAdicionalesView2.setNombre("SAP-2-Nombre-Only-" + id);
      servicioAdicionalesView2.setDomicilio("SAP-2-Domicilio-Only-" + id);
      servicioAdicionalesView2.setComentarios("SAP-2-Comentarios-Only-" + id);
      servicioAdicionalesView2.setFechaCreacion(new Date());

      ArrayList<ServicioAdicionalesView> servicioAdicionalesViewArrayList = new ArrayList<>();
      servicioAdicionalesViewArrayList.add(servicioAdicionalesView);
      servicioAdicionalesViewArrayList.add(servicioAdicionalesView2);

      System.out.println(objectMapper.writeValueAsString(servicioAdicionalesViewArrayList));

//      mockMvc.perform(MockMvcRequestBuilders.post("/pacientes/" + idPaciente + "/servicio-adicionales")
//         .contentType(jsonType)
//         .content(objectMapper.writeValueAsString(servicioAdicionalesViewArrayList)))
//         .andExpect(MockMvcResultMatchers.status().isCreated())
//         .andDo(MockMvcResultHandlers.print());
   }


   @Test
   public void updateServicioAdicionales() throws Exception {
      String id = "3";
      String idPaciente = "b9b8a8cd-4809-468c-b374-5669aa9b61a8";
      String setIdServicioAdicionales = "43f521e2-9e6d-4891-901c-55720f63d406";
      String setIdServicioAdicionales2 = "71cbf27c-a5ae-40d2-b835-2ebe41faddf6";

      ArrayList<ServicioAdicionalesView> servicioAdicionalesViewArrayList = new ArrayList<>();

      ServicioAdicionalesView servicioAdicionalesView = new ServicioAdicionalesView();
      servicioAdicionalesView.setIdServicioAdicionales(setIdServicioAdicionales);
      servicioAdicionalesView.setTipoServicio("SAP-Tipo Servicio-Only-U-" + id);
      servicioAdicionalesView.setNombre("SAP-Nombre-Only-U-" + id);
      servicioAdicionalesView.setDomicilio("SAP-Domicilio-Only-U-" + id);
      servicioAdicionalesView.setComentarios("SAP-Comentarios-Only-U-" + id);
      servicioAdicionalesView.setFechaCreacion(new Date());

      servicioAdicionalesViewArrayList.add(servicioAdicionalesView);

      ServicioAdicionalesView servicioAdicionalesView2 = new ServicioAdicionalesView();
      servicioAdicionalesView2.setIdServicioAdicionales(setIdServicioAdicionales2);
      servicioAdicionalesView2.setTipoServicio("SAP-2-Tipo Servicio-Only-U-" + id);
      servicioAdicionalesView2.setNombre("SAP-2-Nombre-Only-U-" + id);
      servicioAdicionalesView2.setDomicilio("SAP-2-Domicilio-Only-U-" + id);
      servicioAdicionalesView2.setComentarios("SAP-2-Comentarios-Only-U-" + id);
      servicioAdicionalesView2.setFechaCreacion(new Date());

      servicioAdicionalesViewArrayList.add(servicioAdicionalesView2);

      mockMvc.perform(MockMvcRequestBuilders.put("/pacientes/" + idPaciente + "/servicio-adicionales")
         .contentType(jsonType)
         .content(objectMapper.writeValueAsString(servicioAdicionalesViewArrayList)))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
   }


   @Test
   public void deleteServicioAdicionales() throws Exception {
      String id = "1";
      String idPaciente = "b9b8a8cd-4809-468c-b374-5669aa9b61a8";
      String setIdServicioAdicionales = "ef9068e7-e2b2-411c-8bbb-81954d2b3dc0";
//      String setIdServicioAdicionales2 = "71cbf27c-a5ae-40d2-b835-2ebe41faddf6";

      ArrayList<ServicioAdicionalesView> servicioAdicionalesViewArrayList = new ArrayList<>();

      ServicioAdicionalesView servicioAdicionalesView = new ServicioAdicionalesView();
      servicioAdicionalesView.setIdServicioAdicionales(setIdServicioAdicionales);

      servicioAdicionalesViewArrayList.add(servicioAdicionalesView);

//      ServicioAdicionalesView servicioAdicionalesView2 = new ServicioAdicionalesView();
//      servicioAdicionalesView2.setIdServicioAdicionales(setIdServicioAdicionales2);
//
//      servicioAdicionalesViewArrayList.add(servicioAdicionalesView2);


      mockMvc.perform(MockMvcRequestBuilders.delete("/pacientes/" + idPaciente + "/servicio-adicionales")
         .contentType(jsonType)
         .content(objectMapper.writeValueAsString(servicioAdicionalesViewArrayList)))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
   }

   @Test
   public void createPersonasVivienda() throws Exception {
      String id = "1";
      String idPaciente = "b9b8a8cd-4809-468c-b374-5669aa9b61a8";

      ArrayList<PersonasViviendaView> personasViviendaViewArrayList = new ArrayList<>();

      PersonasViviendaView personasViviendaView = new PersonasViviendaView();
      personasViviendaView.setParentesco("PVP-Parentesco-Only-" + id);
      personasViviendaView.setEdad(66);
      personasViviendaView.setConvivencia("PVP-Convivencia-Only-" + id);
      personasViviendaView.setFechaCreacion(new Date());

      personasViviendaViewArrayList.add(personasViviendaView);

      PersonasViviendaView personasViviendaView2 = new PersonasViviendaView();
      personasViviendaView2.setParentesco("PVP-2-Parentesco-Only-" + id);
      personasViviendaView2.setEdad(99);
      personasViviendaView2.setConvivencia("PVP-2-Convivencia-Only-" + id);
      personasViviendaView.setFechaCreacion(new Date());

      personasViviendaViewArrayList.add(personasViviendaView2);

      System.out.println(objectMapper.writeValueAsString(personasViviendaViewArrayList));

//      mockMvc.perform(MockMvcRequestBuilders.post("/pacientes/" + idPaciente + "/personas-vivienda")
//         .contentType(jsonType)
//         .content(objectMapper.writeValueAsString(personasViviendaViewArrayList)))
//         .andExpect(MockMvcResultMatchers.status().isCreated())
//         .andDo(MockMvcResultHandlers.print());
   }

   @Test
   public void updatePersonasVivienda() throws Exception {
      String id = "22";
      String idPaciente = "b9b8a8cd-4809-468c-b374-5669aa9b61a8";
      String setIdPersonasVivienda = "9ee948ec-1e50-480b-8f83-3eb6b1f20bf1";
      String setIdPersonasVivienda2 = "6ff8c1a7-98cf-4e1d-8c92-0e6cb3ee42cb";

      ArrayList<PersonasViviendaView> personasViviendaViewArrayList = new ArrayList<>();

      PersonasViviendaView personasViviendaView = new PersonasViviendaView();
      personasViviendaView.setIdPersonasVivienda(setIdPersonasVivienda);
      personasViviendaView.setParentesco("PVP-Parentesco-Only-U-" + id);
      personasViviendaView.setEdad(66);
      personasViviendaView.setConvivencia("PVP-Convivencia-Only-U-" + id);

      personasViviendaViewArrayList.add(personasViviendaView);

      PersonasViviendaView personasViviendaView2 = new PersonasViviendaView();
      personasViviendaView2.setIdPersonasVivienda(setIdPersonasVivienda2);
      personasViviendaView2.setParentesco("PVP2-2-Parentesco-Only-U-" + id);
      personasViviendaView2.setEdad(333);
      personasViviendaView2.setConvivencia("PVP-2-Convivencia-Only-U-" + id);

      personasViviendaViewArrayList.add(personasViviendaView2);

      mockMvc.perform(MockMvcRequestBuilders.put("/pacientes/" + idPaciente + "/personas-vivienda")
         .contentType(jsonType)
         .content(objectMapper.writeValueAsString(personasViviendaViewArrayList)))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
   }

   @Test
   public void deletePersonasVivienda() throws Exception {
      String id = "1";
      String idPaciente = "b9b8a8cd-4809-468c-b374-5669aa9b61a8";
      String setIdPersonasVivienda = "f415e18e-fd84-4cbf-b2d9-261958a34f55";
      String setIdPersonasVivienda2 = "5495c558-20fc-4504-9425-3d228f699f09";

      ArrayList<PersonasViviendaView> personasViviendaViewArrayList = new ArrayList<>();
      PersonasViviendaView personasViviendaView = new PersonasViviendaView();
      personasViviendaView.setIdPersonasVivienda(setIdPersonasVivienda);

      personasViviendaViewArrayList.add(personasViviendaView);

//      PersonasViviendaView personasViviendaView2 = new PersonasViviendaView();
//      personasViviendaView2.setIdPersonasVivienda(setIdPersonasVivienda2);
//
//      personasViviendaViewArrayList.add(personasViviendaView2);


      mockMvc.perform(MockMvcRequestBuilders.delete("/pacientes/" + idPaciente + "/personas-vivienda")
         .contentType(jsonType)
         .content(objectMapper.writeValueAsString(personasViviendaViewArrayList)))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
   }

   @Test
   public void createDatosContacto() throws Exception {
      String id = "1";
      String idPaciente = "eccf2241-c810-4eba-8892-919b038532d0";

      ArrayList<DatosContactoView> datosContactoViewArrayList = new ArrayList<>();
      DatosContactoView datosContactoView = new DatosContactoView();
      datosContactoView.setNombre("DCP-Nombre-Only-" + id);
      datosContactoView.setParentesco("DCP-Parentesco-Only-" + id);
      datosContactoView.setEdad(99);
      datosContactoView.setConvivencia("DCP-Convivencia-Only-" + id);
      datosContactoView.setLlamarCasoEmergencia(911);
      datosContactoView.setCuentaConLlaves(true);
      datosContactoView.setTelefonoLocal("DCP-Telef Local-Only-" + id);
      datosContactoView.setTelefonoCelular("DCP-Telef Celular-Only-" + id);
      datosContactoView.setTelefonoOficina("DCP-Telef Oficina-Only-" + id);
      datosContactoView.setTipoApoyoMaterial("DCP-Tipo Apoyo Material-Only-" + id);
      datosContactoView.setTipoApoyoEmocional("DCP-Tipo Apoyo Emocional-Only-" + id);
      datosContactoView.setTipoApoyoSocial("DCP-Tipo Apoyo Social-Only-" + id);
      datosContactoView.setTipoApoyoInstrumental("DCP-Tipo Apoyo Instrumental-Only-" + id);
      datosContactoView.setFechaCreacion(new Date());
      datosContactoView.setPrioridad("DCP-prioridad-Only-" + id);

      datosContactoViewArrayList.add(datosContactoView);

      DatosContactoView datosContactoView2 = new DatosContactoView();
      datosContactoView2.setNombre("DCP-2-Nombre-Only-" + id);
      datosContactoView2.setParentesco("DCP-2-Parentesco-Only-" + id);
      datosContactoView2.setEdad(99);
      datosContactoView2.setConvivencia("DCP-2-Convivencia-Only-" + id);
      datosContactoView2.setLlamarCasoEmergencia(911);
      datosContactoView2.setCuentaConLlaves(true);
      datosContactoView2.setTelefonoLocal("DCP-2-Telef Local-Only-" + id);
      datosContactoView2.setTelefonoCelular("DCP-2-Telef Celular-Only-" + id);
      datosContactoView2.setTelefonoOficina("DCP-2-Telef Oficina-Only-" + id);
      datosContactoView2.setTipoApoyoMaterial("DCP-2-Tipo Apoyo Material-Only-" + id);
      datosContactoView2.setTipoApoyoEmocional("DCP-2-Tipo Apoyo Emocional-Only-" + id);
      datosContactoView2.setTipoApoyoSocial("DCP-2-Tipo Apoyo Social-Only-" + id);
      datosContactoView2.setTipoApoyoInstrumental("DCP-2-Tipo Apoyo Instrumental-Only-" + id);
      datosContactoView2.setFechaCreacion(new Date());
      datosContactoView2.setPrioridad("DCP-2-prioridad-Only-" + id);

      datosContactoViewArrayList.add(datosContactoView2);

      System.out.println("===>>>" + objectMapper.writeValueAsString(datosContactoViewArrayList));

//      mockMvc.perform(MockMvcRequestBuilders.post("/pacientes/" + idPaciente + "/datos-contacto")
//         .contentType(jsonType)
//         .content(objectMapper.writeValueAsString(datosContactoViewArrayList)))
//         .andExpect(MockMvcResultMatchers.status().isCreated())
//         .andDo(MockMvcResultHandlers.print());
   }

   @Test
   public void updateDatosContacto() throws Exception {
      String id = "1";
      String idPaciente = "eccf2241-c810-4eba-8892-919b038532d0";
      String setIdDatosContacto = "qwe-123";
      String setIdDatosContacto2 = "f587e7de-7b8f-4594-9e22-2f5596509349";

      ArrayList<DatosContactoView> datosContactoViewArrayList = new ArrayList<>();
      DatosContactoView datosContactoView = new DatosContactoView();
      datosContactoView.setIdDatosContacto(setIdDatosContacto);
      datosContactoView.setNombre("DCP-Nombre-Only-U-" + id);
      datosContactoView.setParentesco("DCP-Parentesco-Only-U-" + id);
      datosContactoView.setEdad(11);
      datosContactoView.setConvivencia("DCP-Convivencia-Only-U-" + id);
      datosContactoView.setLlamarCasoEmergencia(119);
      datosContactoView.setCuentaConLlaves(true);
      datosContactoView.setTelefonoLocal("DCP-Telef Local-Only-U-" + id);
      datosContactoView.setTelefonoCelular("DCP-Telef Celular-Only-U-" + id);
      datosContactoView.setTelefonoOficina("DCP-Telef Oficina-Only-U-" + id);
      datosContactoView.setTipoApoyoMaterial("DCP-Tipo Apoyo Material-Only-U-" + id);
      datosContactoView.setTipoApoyoEmocional("DCP-Tipo Apoyo Emocional-Only-U-" + id);
      datosContactoView.setTipoApoyoSocial("DCP-Tipo Apoyo Social-Only-U-" + id);
      datosContactoView.setTipoApoyoInstrumental("DCP-Tipo Apoyo Instrumental-Only-U-" + id);
      datosContactoView.setFechaCreacion(new Date());

      datosContactoViewArrayList.add(datosContactoView);

//      DatosContactoView datosContactoView2 = new DatosContactoView();
//      datosContactoView2.setIdDatosContacto(setIdDatosContacto2);
//      datosContactoView2.setNombre("DCP-2-Nombre-Only-U-" + id);
//      datosContactoView2.setParentesco("DCP-2-Parentesco-Only2-U-" + id);
//      datosContactoView2.setEdad(22);
//      datosContactoView2.setConvivencia("DCP-2-Convivencia-Only2-U-" + id);
//      datosContactoView2.setLlamarCasoEmergencia(066);
//      datosContactoView2.setCuentaConLlaves(true);
//      datosContactoView2.setTelefonoLocal("DCP-2-Telef Local-Only2-U-" + id);
//      datosContactoView2.setTelefonoCelular("DCP-2-Telef Celular-Only2-U-" + id);
//      datosContactoView2.setTelefonoOficina("DCP-2-Telef Oficina-Only2-U-" + id);
//      datosContactoView2.setTipoApoyoMaterial("DCP-2-Tipo Apoyo Material-Only2-U-" + id);
//      datosContactoView2.setTipoApoyoEmocional("DCP-2-Tipo Apoyo Emocional-Only2-U-" + id);
//      datosContactoView2.setTipoApoyoSocial("DCP-2-Tipo Apoyo Social-Only2-U-" + id);
//      datosContactoView2.setTipoApoyoInstrumental("DCP-2-Tipo Apoyo Instrumental-Only2-U-" + id);
//      datosContactoView2.setFechaCreacion(new Date());
//
//      datosContactoViewArrayList.add(datosContactoView2);

      mockMvc.perform(MockMvcRequestBuilders.put("/pacientes/" + idPaciente + "/datos-contacto")
         .contentType(jsonType)
         .content(objectMapper.writeValueAsString(datosContactoViewArrayList)))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
   }


   @Test
   public void deleteDatosContacto() throws Exception {
      String id = "1";
      String idPaciente = "34eb3df3-1c09-4ff6-8246-b2117bca38a7";
      String setIdDatosContacto = "3bda1c4e-21d9-4dab-a03d-bea30985f533";
      String setIdDatosContacto2 = "asd123-asd123";

      ArrayList<DatosContactoView> datosContactoViewArrayList = new ArrayList<>();
      DatosContactoView datosContactoView = new DatosContactoView();
      datosContactoView.setIdDatosContacto(setIdDatosContacto);

      datosContactoViewArrayList.add(datosContactoView);

      DatosContactoView datosContactoView2 = new DatosContactoView();
      datosContactoView2.setIdDatosContacto(setIdDatosContacto2);

      datosContactoViewArrayList.add(datosContactoView2);

      System.out.println("===>>>" + objectMapper.writeValueAsString(datosContactoViewArrayList));

      mockMvc.perform(MockMvcRequestBuilders.delete("/pacientes/" + idPaciente + "/datos-contacto")
         .contentType(jsonType)
         .content(objectMapper.writeValueAsString(datosContactoViewArrayList)))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
   }

   @Test
   public void createDomicilio() throws Exception {
      String id = "1";
      String idPaciente = "5e15675c-d864-4726-bec8-e72fc086e128";

      ArrayList<DomicilioView> domicilioViewArrayList = new ArrayList<>();

      DomicilioView domicilioView = new DomicilioView();
      domicilioView.setDomicilio("DP-domicilio-Only-" + id);
      domicilioView.setColonia("DP-colonia-Only-" + id);
      domicilioView.setMunicipio("DP-municipio-Only-" + id);
      domicilioView.setEstado("DP-estado-Only-" + id);
      domicilioView.setPais("DP-pais-Only-" + id);
      domicilioView.setCp("DP-codigo postal-Only-" + id);
      domicilioView.setFechaCreacion(new Date());
      domicilioView.setActivo(Boolean.FALSE);

      DomicilioView domicilioView2 = new DomicilioView();
      domicilioView2.setDomicilio("DP-2-domicilio-Only-" + id);
      domicilioView2.setColonia("DP-2-colonia-Only-" + id);
      domicilioView2.setMunicipio("DP-2-municipio-Only-" + id);
      domicilioView2.setEstado("DP-2-estado-Only-" + id);
      domicilioView2.setPais("DP-2-pais-Only-" + id);
      domicilioView2.setCp("DP-2-codigo postal-Only-" + id);
      domicilioView2.setFechaCreacion(new Date());
      domicilioView2.setActivo(Boolean.TRUE);


      domicilioViewArrayList.add(domicilioView);
//      domicilioViewArrayList.add(domicilioView2);

      System.out.println("===>>>"+objectMapper.writeValueAsString(domicilioViewArrayList));

//      mockMvc.perform(MockMvcRequestBuilders.post("/pacientes/" + idPaciente + "/domicilio")
//         .contentType(jsonType)
//         .content(objectMapper.writeValueAsString(domicilioViewArrayList)))
//         .andExpect(MockMvcResultMatchers.status().isCreated())
//         .andDo(MockMvcResultHandlers.print());
//
   }

   @Test
   public void updateDomicilio() throws Exception {
      String id = "111";
      String idPaciente = "5e15675c-d864-4726-bec8-e72fc086e128";
      String setIdDomicilio = "435888f0-a5cd-40ad-aa63-d8b347a0fcb4";
      String setIdDomiclio2 = "baa44277-d45e-4b23-84ea-4cc175b28511";

      ArrayList<DomicilioView> domicilioViewArrayList = new ArrayList<>();

      DomicilioView domicilioView = new DomicilioView();
      domicilioView.setDomicilio("DP-domicilio-Only-U-" + id);
      domicilioView.setColonia("DP-colonia-Only-U-" + id);
      domicilioView.setMunicipio("DP-municipio-Only-U-" + id);
      domicilioView.setEstado("DP-estado-Only-U-" + id);
      domicilioView.setPais("DP-pais-Only-U-" + id);
      domicilioView.setCp("DP-codigo postal-Only-U-" + id);
      domicilioView.setFechaCreacion(new Date());
      domicilioView.setActivo(Boolean.TRUE);

      DomicilioView domicilioView2 = new DomicilioView();
      domicilioView2.setDomicilio("DP-2-domicilio-Only-U-" + id);
      domicilioView2.setColonia("DP-2-colonia-Only-U-" + id);
      domicilioView2.setMunicipio("DP-2-municipio-Only-U-" + id);
      domicilioView2.setEstado("DP-2-estado-Only-U-" + id);
      domicilioView2.setPais("DP-2-pais-Only-U-" + id);
      domicilioView2.setCp("DP-2-codigo postal-Only-U-" + id);
      domicilioView2.setFechaCreacion(new Date());
      domicilioView2.setActivo(Boolean.FALSE);

      domicilioViewArrayList.add(domicilioView);
      domicilioViewArrayList.add(domicilioView2);

      mockMvc.perform(MockMvcRequestBuilders.put("/pacientes/" + idPaciente + "/domicilio")
         .contentType(jsonType)
         .content(objectMapper.writeValueAsString(domicilioViewArrayList)))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
   }


   @Test
   public void deleteDomicilio() throws Exception {
      String idPaciente = "5e15675c-d864-4726-bec8-e72fc086e128";
      String setIdDomicilio = "3ee25f3f-9752-48eb-97f2-0533845c4ecb";

      ArrayList<DomicilioView> domicilioViewArrayList = new ArrayList<>();

      DomicilioView domicilioView = new DomicilioView();
      domicilioView.setIdDomicilio(setIdDomicilio);

      domicilioViewArrayList.add(domicilioView);

      mockMvc.perform(MockMvcRequestBuilders.delete("/pacientes/" + idPaciente + "/domicilio")
         .contentType(jsonType)
         .content(objectMapper.writeValueAsString(domicilioViewArrayList)))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());

   }

   @Test
   public void getPacienteSearch() throws Exception {
      //mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/search?active=true&datosBusqueda=&nombre=Juan&apellidoPaterno=Togata&apellidoMaterno=Deltu&telefonoMovil=0123456789&telefonoFijo=0123456789&correo=jSanchez@gmail.com&rfc=&curp=&page&size&orderColumn=idUsuario&orderType=desc")
      mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/search?active=&datosBusqueda=uno&nombre=&apellidoPaterno=&apellidoMaterno=&telefonoMovil=&telefonoFijo=&correo=&rfc=&curp=&page&size&orderColumn=&orderType=")
         .contentType(jsonType))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
   }

}
