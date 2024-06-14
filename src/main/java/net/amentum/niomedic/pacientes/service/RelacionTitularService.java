package net.amentum.niomedic.pacientes.service;

import net.amentum.niomedic.pacientes.exception.TutoresException;
import net.amentum.niomedic.pacientes.model.PacienteBeneficiarioDTO;
import net.amentum.niomedic.pacientes.views.RelacionTitularView;
import net.amentum.niomedic.pacientes.views.RelacionTutoresView;

import java.util.List;

public interface RelacionTitularService {
    void createRelacionTitular(RelacionTitularView relacionTitularView) throws TutoresException;

    List<PacienteBeneficiarioDTO> getBeneficiariosTitular(String idPacienteTitular) throws Exception;
}
