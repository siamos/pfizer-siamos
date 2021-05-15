package resource.patient;

import exception.AuthorizationException;
import jpaUtil.JpaUtil;
import model.Glucose;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import repository.GlucoseRepository;
import repository.PatientRepository;
import representation.GlucoseRepresentation;
import resource.ResourceUtils;
import security.Shield;

import javax.persistence.EntityManager;
import java.util.List;

public class PatientGlucoseResource extends ServerResource {
    private long patientId;
    private long glucoseId;

    protected void doInit() {
        patientId = Long.parseLong(getAttribute("patientId"));
        glucoseId = Long.parseLong(getAttribute("glucoseId"));
    }


    @Get("json")
    public GlucoseRepresentation getGlucose() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        EntityManager entityManager = JpaUtil.getEntityManager();
        PatientRepository patientRepository = new PatientRepository(entityManager);
        List<Glucose> glucoseList = patientRepository.getGlucoseList(this.patientId);
        Glucose glucose = new Glucose();
        for (Glucose g : glucoseList) {
            if (g.getId() == glucoseId) {
                glucose = g;
            }
        }
        GlucoseRepresentation glucoseRepresentation = new GlucoseRepresentation(glucose);
        entityManager.close();
        return glucoseRepresentation;
    }

    @Put("json")
    public GlucoseRepresentation updateGlucose(GlucoseRepresentation glucoseRepresentationIn) throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        if (glucoseRepresentationIn == null) return null;

        EntityManager entityManager = JpaUtil.getEntityManager();
        GlucoseRepository glucoseRepository = new GlucoseRepository(entityManager);
        Glucose glucose = glucoseRepository.read(glucoseId);
        glucose.setGlucose(glucoseRepresentationIn.getGlucose());

        entityManager.detach(glucose);
        glucose.setId(glucoseId);
        glucoseRepository.update(glucose);
        return glucoseRepresentationIn;
    }

    @Delete("json")
    public void deleteGlucose() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        EntityManager entityManager = JpaUtil.getEntityManager();
        GlucoseRepository glucoseRepository = new GlucoseRepository(entityManager);
        glucoseRepository.delete(glucoseRepository.read(glucoseId));
    }
}
