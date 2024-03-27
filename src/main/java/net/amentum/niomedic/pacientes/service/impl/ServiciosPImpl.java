package net.amentum.niomedic.pacientes.service.impl;

import net.amentum.niomedic.pacientes.converter.ServiciosConverter;
import net.amentum.niomedic.pacientes.exception.ServiciosException;
import net.amentum.niomedic.pacientes.model.CatServicios;
import net.amentum.niomedic.pacientes.model.Servicios;
import net.amentum.niomedic.pacientes.persistence.CatServiciosRepository;
import net.amentum.niomedic.pacientes.persistence.ServiciosRepository;
import net.amentum.niomedic.pacientes.service.ServiciosP;
import net.amentum.niomedic.pacientes.views.CatServicio;
import net.amentum.niomedic.pacientes.views.CatServicioView;
import net.amentum.niomedic.pacientes.views.ServiciosView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ServiciosPImpl implements ServiciosP {

    private final Logger logger = LoggerFactory.getLogger(ServiciosPImpl.class);

    @Autowired
    private ServiciosRepository repository;

    @Autowired
    private CatServiciosRepository catServiciosRepository;

    @Autowired
    private ServiciosConverter converter;

    @Transactional(rollbackFor = ServiciosException.class)
    @Override
    public void createServicio(ServiciosView serviciosView) throws ServiciosException {
        try {
            if(!catServiciosRepository.existServicio(serviciosView.getIdServicio()))
                throw new ServiciosException("El servicio recibido no existe o está desactivado", ServiciosException.LAYER_REST, ServiciosException.ACTION_INSERT);
            Servicios entity = converter.toEntity(serviciosView);
            entity.setCreatedDate(Calendar.getInstance().getTime());
            repository.save(entity);
        } catch (Exception ex) {
            logger.error("Error al insertar el tutor - {}", ex.getMessage());
            throw new ServiciosException("Error al insertar nuevo tutor - " + ex.getMessage(), ServiciosException.LAYER_REST, ServiciosException.ACTION_INSERT);
        }
    }

    @Override
    public void deleteServiceById(String idPaciente) throws ServiciosException {
        try {
            List<Servicios> servicios = repository.findByidPaciente(idPaciente);
            logger.info("Lista de servicios a eliminar - " + servicios);
            for(Servicios entity : servicios)
                repository.delete(entity);
            logger.info("Servicios eliminados para el paciente - {}", idPaciente);
        } catch (Exception ex) {
            logger.error("Error al eliminar el servicio - {}", ex.getMessage());
            throw new ServiciosException("Error al eliminar el servicio - " + ex.getMessage(), ServiciosException.LAYER_REST, ServiciosException.ACTION_INSERT);
        }
    }

    @Override
    public CatServicio findByIdPaciente(String idPaciente) throws Exception {
        List<Servicios> entityList = repository.findByidPaciente(idPaciente);
        List<CatServicioView> list = new ArrayList<>();
        for(Servicios entity : entityList) {
            CatServicios servicio = catServiciosRepository.findByIdServicio(entity.getIdServicio());
            CatServicioView view = new CatServicioView();
            view.setIdCatServicio(servicio.getIdServicio());
            view.setNombre(servicio.getNombre());
            list.add(view);
        }
        CatServicio result = new CatServicio();
        result.setServicios(list);
        return result;
    }

    @Transactional(rollbackFor = ServiciosException.class)
    @Override
    public void addService(String idPaciente) throws ServiciosException {
        try {
            logger.info("Buscando servicios activos");
            List<CatServicios> serviciosList = catServiciosRepository.findCatServicioActivo();
            logger.info("Servicios activos - " + serviciosList.toString());
            List<Integer> serviciosExist = repository.findListByIdPaciente(idPaciente);
            logger.info("Servicios registrados con el paciente - " + serviciosExist.toString());
            for(CatServicios entity : serviciosList) {
                if(serviciosExist.contains(entity.getIdServicio()))
                    continue;
                Servicios servicios = new Servicios();
                servicios.setIdServicio(entity.getIdServicio());
                servicios.setIdPaciente(idPaciente);
                servicios.setCreatedDate(Calendar.getInstance().getTime());
                logger.info("Servicio a guardar - " + servicios);
                repository.save(servicios);
            }
        } catch (Exception ex) {
            logger.error("Error al agregar permisos al paciente - {}", ex.getMessage());
            throw new ServiciosException("Error al agregar permisos al paciente - " + ex.getMessage(), ServiciosException.LAYER_REST, ServiciosException.ACTION_INSERT);
        }
    }

}
