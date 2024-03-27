package net.amentum.niomedic.pacientes.exception;

import lombok.Getter;
import lombok.Setter;
import net.amentum.common.GenericException;

public class ServiciosException extends GenericException {

    private final ExceptionServiceCode MODULE_CODE = ExceptionServiceCode.SERVICE;
    @Getter
    @Setter
    private String layer;
    @Getter @Setter private String action;

    public ServiciosException(Exception ex, String message, String layer, String action){
        super(ex,message);
        this.layer = layer;
        this.action = action;
    }

    public ServiciosException(String message, String layer, String action){
        super(message);
        this.layer = layer;
        this.action = action;
    }

    @Override
    public String getExceptionCode() {
        return null;
    }

}
