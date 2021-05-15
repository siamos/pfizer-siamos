package resource.patient;

import exception.AuthorizationException;
import jpaUtil.JpaUtil;
import model.Carb;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import repository.CarbRepository;
import repository.PatientRepository;
import representation.CarbRepresentation;
import resource.ResourceUtils;
import security.Shield;

import javax.persistence.EntityManager;
import java.util.List;

public class PatientCarbResource extends ServerResource {
    private long patientId;
    private long carbId;

    protected void doInit() {
        patientId = Long.parseLong(getAttribute("patientId"));
        carbId = Long.parseLong(getAttribute("carbId"));
    }


    @Get("json")
    public CarbRepresentation getCarb() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        EntityManager entityManager = JpaUtil.getEntityManager();

        PatientRepository patientRepository = new PatientRepository(entityManager);
        List<Carb> carbList = patientRepository.getCarbList(this.patientId);
        Carb carb = new Carb();

        for (Carb c : carbList) {
            if (c.getId() == carbId) {
                carb = c;
            }
        }
        CarbRepresentation carbRepresentation = new CarbRepresentation(carb);
        entityManager.close();
        return carbRepresentation;
    }

    @Put("json")
    public CarbRepresentation updateCarb(CarbRepresentation carbRepresentation) throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        if (carbRepresentation == null) return null;

        carbRepresentation.setId(carbId);
        carbRepresentation.setPatientId(patientId);
        EntityManager entityManager = JpaUtil.getEntityManager();
        CarbRepository carbRepository = new CarbRepository(entityManager);
        Carb carb = carbRepresentation.createCarb();
        entityManager.detach(carb);
        carb.setId(carbId);
        carbRepository.update(carb);
        return carbRepresentation;
    }

    @Delete("json")
    public void deleteCarb() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        EntityManager entityManager = JpaUtil.getEntityManager();
        CarbRepository carbRepository = new CarbRepository(entityManager);
        carbRepository.delete(carbRepository.read(carbId));
    }
}