package net.amentum.niomedic.pacientes.exception;

import lombok.Getter;
import lombok.Setter;
import net.amentum.common.GenericException;

public class ControlNotificacionException extends GenericException {
    private final ExceptionServiceCode MODULE_CODE = ExceptionServiceCode.PACIENTE;
    @Getter @Setter private String layer;
    @Getter @Setter private String action;

    @Override
    public String getExceptionCode() {
        return layer + MODULE_CODE + action;
    }

    public ControlNotificacionException(Exception ex, String message, String layer, String action){
        super(ex,message);
        this.layer = layer;
        this.action = action;
    }

    public ControlNotificacionException(String message, String layer, String action){
        super(message);
        this.layer = layer;
        this.action = action;
    }
}
