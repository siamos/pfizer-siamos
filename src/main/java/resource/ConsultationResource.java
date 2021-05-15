package resource;

import exception.AuthorizationException;
import jpaUtil.JpaUtil;
import model.Consultation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import repository.ConsultationRepository;
import representation.ConsultationRepresentation;
import security.Shield;

import javax.persistence.EntityManager;

public class ConsultationResource extends ServerResource {
    private long id;

    protected void doInit() {
        id = Long.parseLong(getAttribute("id"));
    }


    @Get("json")
    public ConsultationRepresentation getConsultation() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_CHIEF_DOCTOR);
        EntityManager entityManager = JpaUtil.getEntityManager();
        ConsultationRepository consultationRepository = new ConsultationRepository(entityManager);
        Consultation consultation = consultationRepository.read(id);
        ConsultationRepresentation consultationRepresentation = new ConsultationRepresentation(consultation);
        entityManager.close();
        return consultationRepresentation;
    }
}
