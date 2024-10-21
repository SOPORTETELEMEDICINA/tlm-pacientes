package net.amentum.niomedic.pacientes.exception;

import lombok.Getter;
import lombok.Setter;
import net.amentum.common.GenericException;

public class PacientesGruposException extends GenericException {
    private final ExceptionServiceCode MODULE_CODE = ExceptionServiceCode.PACIENTE;
    @Getter
    @Setter
    private String layer;
    @Getter @Setter private String action;

    public PacientesGruposException(Exception ex, String message, String layer, String action){
        super(ex,message);
        this.layer = layer;
        this.action = action;
    }

    public PacientesGruposException(String message, String layer, String action){
        super(message);
        this.layer = layer;
        this.action = action;
    }

    @Override
    public String getExceptionCode() {
        return new StringBuffer(layer).append(MODULE_CODE).append(action).toString();
    }
}
