package resource.patient;

import exception.AuthorizationException;
import jpaUtil.JpaUtil;
import model.Consultation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import repository.ConsultationRepository;
import repository.PatientRepository;
import representation.ConsultationRepresentation;
import resource.ResourceUtils;
import security.Shield;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class PatientConsultationListResource extends ServerResource {
    private long patientId;

    protected void doInit() {
        patientId = Long.parseLong(getAttribute("patientId"));
    }


    @Get("json")
    public List<ConsultationRepresentation> getConsultationList() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        EntityManager entityManager = JpaUtil.getEntityManager();

        PatientRepository patientRepository = new PatientRepository(entityManager);
        List<Consultation> consultationList = patientRepository.getConsultationList(this.patientId);
        List<ConsultationRepresentation> consultationRepresentationList = new ArrayList<>();

        for (Consultation c : consultationList) {
            consultationRepresentationList.add(new ConsultationRepresentation(c));
        }

        entityManager.close();
        return consultationRepresentationList;
    }

    @Post("json")
    public ConsultationRepresentation add(ConsultationRepresentation consultationRepresentationIn) throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        if (consultationRepresentationIn == null) return null;

        consultationRepresentationIn.setPatientId(this.patientId);
        Consultation consultation = consultationRepresentationIn.createConsultation();
        EntityManager entityManager = JpaUtil.getEntityManager();
        ConsultationRepository consultationRepository = new ConsultationRepository(entityManager);
        consultationRepository.save(consultation);
        ConsultationRepresentation c = new ConsultationRepresentation(consultation);
        return c;
    }
}
