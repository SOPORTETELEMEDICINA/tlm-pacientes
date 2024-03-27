package net.amentum.niomedic.pacientes.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.amentum.niomedic.pacientes.exception.PacienteException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * GGR20200626 API para hacer llamadas a otros microservicios
 */
@Component
@Slf4j
public class ApiServCaller {

    @Value("${url}")
    private String urlProperti;
    private ObjectMapper mapp = new ObjectMapper();
    private Date fechaExpiraTocken;
    String token = "";

    public Map<String, Object> obtenerToken() throws PacienteException {
        try {
            log.info("obtenerToken() - Solicitando token de acceso a: {} ", urlProperti);
            String params = "client_id=auth.testing&client_secret=auth.testing&username=sysAdmin&password=5ae23bbbb73b35ef9f4a624e656b8240641dc48e005b55482def92901253389f&grant_type=password";
            URL url = new URL(urlProperti + "auth/oauth/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            OutputStream os = conn.getOutputStream();
            os.write(params.getBytes());
            os.flush();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println(conn.getResponseMessage() + "  " + conn.getResponseCode());
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                StringBuilder response = new StringBuilder();
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    response.append(currentLine);
                }
                br.close();
                response.toString();
                PacienteException consE = new PacienteException("No fue posible editar Consulta", PacienteException.LAYER_DAO, PacienteException.ACTION_SELECT);
                consE.addError("No fue posible obtener detalles Consulta");
                log.info("obtenerToken() - Ocurrio un error al obtener el token - error: {}", response.toString());
                throw consE;
            } else {
                log.info("obtenerToken() - El token se generó exitosamente");
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    response.append(currentLine);
                }
                br.close();
                Map<String, Object> JsonResponse = mapp.readValue(response.toString(), Map.class);
                Map<String, Object> infoToken = new HashMap<String, Object>();
                Integer expiraEn = (Integer) JsonResponse.get("expires_in");
                infoToken.put("expires_in", expiraEn);
                infoToken.put("access_token", (String) JsonResponse.get("access_token"));
                infoToken.put("hier_token", (String) JsonResponse.get("hier_token"));
                final Long one_second_in_millis = 1000L;
                Calendar date = Calendar.getInstance();
                long t = date.getTimeInMillis();
                Date afterAdding = new Date(t + (expiraEn * one_second_in_millis));
                fechaExpiraTocken = afterAdding;
                return infoToken;
            }
        } catch (PacienteException ce) {
            throw ce;
        } catch (Exception e) {
            PacienteException consE = new PacienteException("Ocurrio un error inesperado en la configuraion de los reportes", PacienteException.LAYER_DAO, PacienteException.ACTION_SELECT);
            consE.addError("No fue posible crear el token para completar la información de los formatos impreso");
            log.error("Ocurrio un error inesperado al obtener el token para los reportes - error: {}", e);
            throw consE;
        }
    }

    //Obtener la lista de usuarios canalizados
    public List<Long> getListaCanalizados(Long idUserApp) throws PacienteException {
        try {
            log.info("getListaCanalizados() - Solicitando lista de usuarios canalizados a: {} ", urlProperti);
            URL url = new URL(urlProperti + "canalizar/lista/" + idUserApp);
            Integer contador = 0;
            Boolean ciclo = true;
            do {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("GET");

                Map<String, Object> infoTocken = obtenerToken();
                token = "bearer " + (String) infoTocken.get("access_token");

                conn.setRequestProperty("Authorization", token);
                if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    StringBuilder response = new StringBuilder();
                    String currentLine;
                    while ((currentLine = br.readLine()) != null) {
                        response.append(currentLine);
                    }
                    br.close();
                    response.toString();
                    contador++;
                    ciclo = true;
                    if (contador > 3) {
                        PacienteException consE = new PacienteException("Ocurrio un error en ApiServCaller", PacienteException.LAYER_DAO, PacienteException.ACTION_SELECT);
                        consE.addError("No se pudo obtener detalle de los usuarios canalizados");
                        log.info("getListaCanalizados() - Ocurrio un error en los reportes al obtener los detalles del medico - error: {}", response.toString());
                        throw consE;
                    }
                } else {
                    log.info("getListaCanalizados() - Se obtuvo la lista de usuarios canalizados xitosamente");
                    ciclo = false;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String currentLine;
                    while ((currentLine = br.readLine()) != null) {
                        response.append(currentLine);
                    }
                    br.close();
                    List<Long> respuesta = mapp.readValue(response.toString(), new TypeReference<List<Long>>(){});
                    log.info("getListaCanalizados obtengo de nio-security: List: {}", respuesta);
                    return respuesta;
                }
            } while (ciclo);
            return null;
        } catch (PacienteException ce) {
            throw ce;
        } catch (Exception e) {
            PacienteException consE = new PacienteException("Ocurrio un error inesperado en ApiServCaller", PacienteException.LAYER_DAO, PacienteException.ACTION_SELECT);
            consE.addError("No fue posible obtener la informacion de los usuarios canalizados para los pacientes");
            log.error("Ocurrio un error inesperado al obtener los detalles de los usuarios canalizados para lista de pacientes - error: {}", e);
            throw consE;
        }
    }

    public Boolean checkPacientecanalizado(Long idUserApp) throws PacienteException {
        try {
            log.info("checkPacientecanalizado() - Checando usuario canalizado a: {} ", urlProperti);
            URL url = new URL(urlProperti + "canalizar/pacienteCanalizado/" + idUserApp);
            Integer contador = 0;
            Boolean ciclo = true, responseBool = false;
            do {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("GET");

                Map<String, Object> infoTocken = obtenerToken();
                token = "bearer " + (String) infoTocken.get("access_token");

                conn.setRequestProperty("Authorization", token);
                if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    StringBuilder response = new StringBuilder();
                    String currentLine;
                    while ((currentLine = br.readLine()) != null) {
                        response.append(currentLine);
                    }
                    br.close();
                    response.toString();
                    contador++;
                    ciclo = true;
                    if (contador > 3) {
                        PacienteException consE = new PacienteException("Ocurrio un error en ApiServCaller", PacienteException.LAYER_DAO, PacienteException.ACTION_SELECT);
                        consE.addError("No se pudo obtener detalle de los usuarios canalizados");
                        log.info("checkPacientecanalizado() - Ocurrio un error en los reportes al obtener los detalles del medico - error: {}", response.toString());
                        throw consE;
                    }
                } else {
                    log.info("checkPacientecanalizado() - Se obtuvo la lista de usuarios canalizados xitosamente");
                    ciclo = false;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String currentLine;
                    while ((currentLine = br.readLine()) != null)
                        response.append(currentLine);
                    br.close();
                    List<Long> respuesta = mapp.readValue(response.toString(), new TypeReference<List<Long>>(){});
                    log.info("checkPacientecanalizado obtengo de nio-security: respuesta: {}", respuesta);
                    if(!respuesta.isEmpty() && respuesta.contains(idUserApp))
                        responseBool = true;
                    return responseBool;
                }
            } while (ciclo);
            return null;
        } catch (PacienteException ce) {
            throw ce;
        } catch (Exception e) {
            PacienteException consE = new PacienteException("Ocurrio un error inesperado en ApiServCaller", PacienteException.LAYER_DAO, PacienteException.ACTION_SELECT);
            consE.addError("No fue posible obtener la informacion de los usuarios canalizados para los pacientes");
            log.error("Ocurrio un error inesperado al obtener los detalles de los usuarios canalizados para lista de pacientes - error: {}", e);
            throw consE;
        }
    }

    private Boolean tokenActivo() {
        try {
            Date fechaActual = new Date();
            if(fechaExpiraTocken.compareTo(fechaActual) > 0)
                return true;
            else
                return false;
        } catch (Exception ex) {
            return false;
        }
    }

    public JSONObject getListaNotificacionesPorAgendar() throws PacienteException {
        try {
            URL url = new URL(urlProperti + "notificaciones-paciente");
            log.info("Solicitando lista de notificaciones en liga {}", url);
            Integer contador = 0;
            Boolean ciclo = true;
            do {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("GET");
                Map<String, Object> infoTocken = obtenerToken();
                token = "bearer " + infoTocken.get("access_token");
                conn.setRequestProperty("Authorization", token);
                if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    StringBuilder response = new StringBuilder();
                    String currentLine;
                    while ((currentLine = br.readLine()) != null)
                        response.append(currentLine);
                    br.close();
                    response.toString();
                    contador++;
                    ciclo = true;
                    if (contador > 3) {
                        PacienteException consE = new PacienteException("Ocurrió un error en ApiServCaller", PacienteException.LAYER_DAO, PacienteException.ACTION_SELECT);
                        consE.addError("No se pudo obtener la lista de notificaciones");
                        log.error("No se pudo obtener la lista de notificaciones - error: {}", response);
                        throw consE;
                    }
                } else {
                    log.info("Se obtuvo la lista de notificaciones");
                    ciclo = false;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String currentLine;
                    while ((currentLine = br.readLine()) != null)
                        response.append(currentLine);
                    br.close();
                    log.info("Resultado de nio-expediente: List: {}", response);
                    JSONObject json = new JSONObject(response.toString());
                    return json;
                }
            } while (ciclo);
            return null;
        } catch (PacienteException ce) {
            throw ce;
        } catch (Exception e) {
            PacienteException consE = new PacienteException("Ocurrió un error inesperado en ApiServCaller", PacienteException.LAYER_DAO, PacienteException.ACTION_SELECT);
            consE.addError("No fue posible obtener la ´lista de notificaciones de pacientes");
            log.error("Ocurrió un error al obtener la lista de notificaciones de pacientes- error: {}", e.toString());
            throw consE;
        }
    }

    public Map getUserAppById(Long idUserApp) throws PacienteException {
        try {
            URL url = new URL(urlProperti + "users/" + idUserApp);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            if(!tokenActivo()) {
                Map<String, Object> infoTocken = obtenerToken();
                token = "bearer " + infoTocken.get("access_token");
            }
            conn.setRequestProperty("Authorization", token);
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null)
                    response.append(responseLine.trim());
                return mapp.readValue(response.toString(), Map.class);
            }
        } catch (PacienteException ce) {
            throw ce;
        } catch (Exception e) {
            PacienteException consE = new PacienteException("Ocurrio un error inesperado en la configuraion", PacienteException.LAYER_DAO, PacienteException.ACTION_SELECT);
            consE.addError("No fue posible obtener la informacion del usuario");
            log.error("Ocurrio un error inesperado al obtener los detalles del usuario- error: {}", e.getCause().toString());
            throw consE;
        }
    }

}
