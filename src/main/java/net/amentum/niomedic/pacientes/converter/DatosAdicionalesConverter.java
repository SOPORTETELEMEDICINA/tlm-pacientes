package net.amentum.niomedic.pacientes.converter;

import net.amentum.niomedic.pacientes.model.DatosAdicionales;
import net.amentum.niomedic.pacientes.model.Paciente;
import net.amentum.niomedic.pacientes.views.DatosAdicionalesView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DatosAdicionalesConverter {
   private Logger logger = LoggerFactory.getLogger(DatosAdicionalesConverter.class);

   public DatosAdicionales toEntity(DatosAdicionalesView datosAdicionalesView, DatosAdicionales datosAdicionales, Paciente paciente, Boolean update) {

//        if(datosAdicionalesView.getIdDatosAdicionales()!=null && !datosAdicionalesView.getIdDatosAdicionales().equals(""))
      DatosAdicionales da = new DatosAdicionales();
      if (update)
         da.setIdDatosAdicionales(datosAdicionalesView.getIdDatosAdicionales());
      da.setEscolaridad(datosAdicionalesView.getEscolaridad());
      da.setOcupacion(datosAdicionalesView.getOcupacion());
      da.setOcupacionAnterior(datosAdicionalesView.getOcupacionAnterior());
      da.setFechaCreacion((!update) ? new Date() : paciente.getFechaCreacion());
      da.setOrigenEtnico(datosAdicionalesView.getOrigenEtnico());
      da.setViveSolo(datosAdicionalesView.getViveSolo());
      da.setLocalidad(datosAdicionalesView.getLocalidad());
      da.setLenguaIndigena(datosAdicionalesView.getLenguaIndigena());
      da.setAntecedentes(datosAdicionalesView.getAntecedentes());
      da.setPaciente(paciente);
      datosAdicionales = da;
      logger.debug("converter DatosAdicionalesView to Entity: {}", datosAdicionales);
      return datosAdicionales;
   }

   public DatosAdicionalesView toView(DatosAdicionales datosAdicionales, Boolean completeConversion) {
      DatosAdicionalesView datosAdicionalesView = new DatosAdicionalesView();
      datosAdicionalesView.setIdDatosAdicionales(datosAdicionales.getIdDatosAdicionales());
      datosAdicionalesView.setEscolaridad(datosAdicionales.getEscolaridad());
      datosAdicionalesView.setOcupacion(datosAdicionales.getOcupacion());
      datosAdicionalesView.setOcupacionAnterior(datosAdicionales.getOcupacionAnterior());
      datosAdicionalesView.setFechaCreacion(datosAdicionales.getFechaCreacion());
      datosAdicionalesView.setOrigenEtnico(datosAdicionales.getOrigenEtnico());
      datosAdicionalesView.setViveSolo(datosAdicionales.getViveSolo());
      datosAdicionalesView.setLocalidad(datosAdicionales.getLocalidad());
      datosAdicionalesView.setLenguaIndigena(datosAdicionales.getLenguaIndigena());
      datosAdicionalesView.setAntecedentes(datosAdicionales.getAntecedentes());
      logger.debug("converter DatosAdicionales to View: {}", datosAdicionalesView);
      return datosAdicionalesView;
   }
}
