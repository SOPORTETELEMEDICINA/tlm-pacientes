package net.amentum.niomedic.pacientes.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;

import net.amentum.niomedic.pacientes.ConfigurationTest;
import net.amentum.niomedic.pacientes.views.DatosAdicionalesView;
import net.amentum.niomedic.pacientes.views.DatosContactoView;
import net.amentum.niomedic.pacientes.views.DomicilioView;
import net.amentum.niomedic.pacientes.views.PacienteView;
import net.amentum.niomedic.pacientes.views.PersonasViviendaView;
import net.amentum.niomedic.pacientes.views.ServicioAdicionalesView;

public class PacienteSuiteTest extends ConfigurationTest{

	long id = new Random().nextInt(9999);

	@Test
	public void createPaciente() throws Exception {

		PacienteView view = new PacienteView();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
		String dateS = "14/09/1978";

		view.setNombre("Juan");
		view.setApellidoPaterno("Hernandez");
		//view.setApellidoMaterno("Fierro");
		//creando fecha nacimiento
		Date dateD = sdf.parse(dateS);
		view.setFechaNacimiento(dateD);
		view.setLugarNacimiento("Guadalajara");
		view.setCurp(view.getNombre()+view.getApellidoPaterno()+id);
		view.setSexo("Masculino");
		view.setReligion("Católico");
		view.setEstadoCivil("Casado");
		view.setTelefonoLocal("3333333333");
		view.setTelefonoCelular("333333333");
		view.setEmail("jSanchez@gmail.com");
		view.setRfc(null);
		view.setFechaCreacion(new Date());
		view.setIdUsuario(1L + id);
		view.setActivo(true);
		//nuevos campos
		view.setPadecimientoCronico("Diabetes");
		view.setAlergias("Polen, Ciprofloxaxino");
		view.setTipoSangre("RH O negativo");
		view.setAfiliacion("IMSS");
		view.setNumeroAfiliacion("1234567890");
		view.setUserName(view.getNombre());
		System.out.println(MAPPER.writeValueAsString(view));
		// enviando-recibiendo los datos de prueba
		mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andDo(MockMvcResultHandlers.print());
	}
	@Test
	public void camposMinimos() throws Exception {
		PacienteView view = new PacienteView();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
		String dateS = "14/09/1978";
		view.setNombre("Juan");
		view.setApellidoPaterno("Hernandez");
		view.setFechaNacimiento(sdf.parse(dateS));
		view.setCurp(null);
		view.setSexo("Masculino");
		view.setIdUsuario(id);
		System.out.println(MAPPER.writeValueAsString(view));
		// enviando-recibiendo los datos de prueba
		String response = mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andReturn().getResponse().getContentAsString();

		PacienteView resultView = MAPPER.readValue(response, new TypeReference<PacienteView>(){});
		mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/{idPaciente}",resultView.getIdPaciente())
				.contentType(JSON))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andDo(MockMvcResultHandlers.print());

	}
	@Test
	public void createPacienteBadRequest() throws Exception {

		PacienteView view = new PacienteView();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
		String dateS = "14/09/1978";

		view=crearPacienteTest();
		view.setNombre(null);
		System.out.println(MAPPER.writeValueAsString(view));
		mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andDo(MockMvcResultHandlers.print());

		System.out.println("\n======================================================================================================");

		view=crearPacienteTest();
		view.setNombre("");
		System.out.println(MAPPER.writeValueAsString(view));
		mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andDo(MockMvcResultHandlers.print());

		System.out.println("\n======================================================================================================");

		view=crearPacienteTest();
		view.setApellidoPaterno(null);
		System.out.println(MAPPER.writeValueAsString(view));
		mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andDo(MockMvcResultHandlers.print());

		System.out.println("\n======================================================================================================");

		view=crearPacienteTest();
		view.setApellidoPaterno("");
		System.out.println(MAPPER.writeValueAsString(view));
		mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andDo(MockMvcResultHandlers.print());
		

		System.out.println("\n======================================================================================================");

		view=crearPacienteTest();
		view.setCurp("");
		System.out.println(MAPPER.writeValueAsString(view));
		mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andDo(MockMvcResultHandlers.print());
	}

	public PacienteView crearPacienteTest() throws Exception {
		PacienteView view = new PacienteView();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
		String dateS = "14/09/1978";

		view.setNombre("Juan");
		view.setApellidoPaterno("Hernandez");
		view.setApellidoMaterno("Fierro");
		//creando fecha nacimiento
		Date dateD = sdf.parse(dateS);
		view.setFechaNacimiento(dateD);
		view.setLugarNacimiento("Guadalajara");
		view.setCurp(view.getNombre()+view.getApellidoPaterno()+id);
		view.setSexo("Masculino");
		view.setReligion("Católico");
		view.setEstadoCivil("Casado");
		view.setTelefonoLocal("3333333333");
		view.setTelefonoCelular("333333333");
		view.setEmail("jSanchez@gmail.com");
		view.setRfc(view.getNombre()+view.getApellidoPaterno()+id);
		view.setFechaCreacion(new Date());
		view.setIdUsuario(1L + id);
		view.setActivo(true);
		//nuevos campos
		view.setPadecimientoCronico("Diabetes");
		view.setAlergias("Polen, Ciprofloxaxino");
		view.setTipoSangre("RH O negativo");
		view.setAfiliacion("IMSS");
		view.setNumeroAfiliacion("1234567890");
		view.setUserName(view.getNombre());
		return view;
	}



}
