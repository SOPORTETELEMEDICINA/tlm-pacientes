package net.amentum.niomedic.pacientes.callback;

import org.json.JSONObject;

public interface OneSignalCallback {
    void onSuccess(JSONObject response);
    void onError(String error, JSONObject postbject);
}
