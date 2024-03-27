package net.amentum.niomedic.pacientes.configuration;

import net.amentum.niomedic.pacientes.callback.OneSignalCallback;
import net.amentum.niomedic.pacientes.exception.PacienteException;
import net.amentum.niomedic.pacientes.service.PacienteService;
import net.amentum.niomedic.pacientes.views.PacienteView;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Map;

@Component
public class OneSignalApi {

    final String CONTENT_TYPE_KEY = "Content-Type";
    final String APP_JSON = "application/json";
    final String REQUEST_METHOD_POST = "POST";
    final String HEADER_AUTHORIZATION = "Authorization";
    final String APP_ID_KEY = "app_id";
    final String ID_KEY = "ID";
    final String PERD_KEY = "PERD";
    final String PERD_VALUE = "periodo";
    final String DATA_KEY = "data";
    final String DEVICE_VALUE = "idDevice";
    final String PLAYERS_ID_KEY = "include_player_ids";
    final String SEND_AFTER_KEY = "send_after";
    final String TEMPLATE_KEY = "template_id";
    final String RESPONSE_ID_USER = "idUserApp";

    @Value("${onesignal.bearer}")
    String BEARER;
    @Value("${onesignal.app_id}")
    String APP_ID_VALUE;
    @Value("${onesignal.template.glucosa}")
    String TEMPLATE_GLUCOSA;
    @Value("${onesignal.template.pre_art}")
    String TEMPLATE_PRE_ART;
    @Value("${onesignal.template.nutricion}")
    String TEMPLATE_NUTRICION;
    @Value("${onesignal.template.covid}")
    String TEMPLATE_COVID;

    final Long TYPE_GLUCOSA = 1L;
    final Long TYPE_PRESION_ARTERIAL = 2L;
    final Long TYPE_NUTRICION = 3L;
    final Long TYPE_COVID = 4L;

    @Autowired
    PacienteService pacienteService;

    @Autowired
    ApiServCaller apiServCaller;

    @Async
    public void scheduleOneSignal(JSONObject element, Long notificationType, OneSignalCallback callback) {
        try {
            //Traemos informacion de usuario y paciente
            PacienteView pacienteView;
            try {
                pacienteView = pacienteService.getDetailsPacienteById((String) element.get("idPaciente"));
                if(pacienteView.getIdDevice().isEmpty() || pacienteView.getIdDevice() == null || pacienteView.getIdDevice().equalsIgnoreCase("")) {
                    callback.onError("Id device vacío", element);
                    return;
                }

            } catch (PacienteException ex) {
                callback.onError("Paciente no encontrado: " + element.get("idPaciente"), element);
                return;
            }
            Map userInfo;
            try {
                userInfo = apiServCaller.getUserAppById(pacienteView.getIdUsuario());
                if(userInfo.containsKey("status") && !String.valueOf(userInfo.get("status")).equalsIgnoreCase("activo")) {
                    element.put(DEVICE_VALUE, pacienteView.getIdDevice());
                    element.put(RESPONSE_ID_USER, pacienteView.getIdUsuario());
                    callback.onError("Usuario" + pacienteView.getIdUsuario() + "no activo", element);
                    return;
                }
            } catch (PacienteException ex) {
                element.put(DEVICE_VALUE, pacienteView.getIdDevice());
                callback.onError("Usuario no encontrado: " + pacienteView.getIdUsuario(), element);
                return;
            }
            JSONObject postObject = new JSONObject();
            postObject.put(APP_ID_KEY, APP_ID_VALUE);
            JSONObject data = new JSONObject();
            data.put(ID_KEY, notificationType);
            data.put(PERD_KEY, element.get(PERD_VALUE));
            postObject.put(DATA_KEY, data);
            JSONArray array = new JSONArray();
            array.put(pacienteView.getIdDevice());
            postObject.put(PLAYERS_ID_KEY, array);
            Calendar calendar = Calendar.getInstance();
            String sendAfter = String.format("%d-%d-%d %s GMT-6", calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH)+1),
                    calendar.get(Calendar.DAY_OF_MONTH), element.get("hora"));
            postObject.put(SEND_AFTER_KEY, sendAfter);
            if(notificationType == TYPE_PRESION_ARTERIAL)
                postObject.put(TEMPLATE_KEY, TEMPLATE_PRE_ART);
            else if(notificationType == TYPE_NUTRICION)
                postObject.put(TEMPLATE_KEY, TEMPLATE_NUTRICION);
            else if(notificationType == TYPE_GLUCOSA)
                postObject.put(TEMPLATE_KEY, TEMPLATE_GLUCOSA);
            else if(notificationType == TYPE_COVID)
                postObject.put(TEMPLATE_KEY, TEMPLATE_COVID);

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection connOneSignal = (HttpURLConnection) url.openConnection();
            connOneSignal.setDoOutput(true);
            connOneSignal.setRequestProperty(HEADER_AUTHORIZATION, BEARER);
            connOneSignal.setRequestProperty(CONTENT_TYPE_KEY, APP_JSON);
            connOneSignal.setRequestMethod(REQUEST_METHOD_POST);
            try(OutputStream os = connOneSignal.getOutputStream()) {
                byte[] input = postObject.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(connOneSignal.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null)
                    response.append(responseLine.trim());
                JSONObject responseJson = new JSONObject(response.toString());
                responseJson.put(DEVICE_VALUE, pacienteView.getIdDevice());
                responseJson.put(RESPONSE_ID_USER, pacienteView.getIdUsuario());
                responseJson.put("idPaciente", pacienteView.getIdPaciente());
                responseJson.put("notificationType", notificationType);
                callback.onSuccess(responseJson);
            }
        }
        catch (Exception e) {
            callback.onError(e.toString(), element);
            e.printStackTrace();
        }
    }

}
