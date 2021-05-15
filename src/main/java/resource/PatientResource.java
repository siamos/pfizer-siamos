package resource;

import Service.PatientServiceImpl;
import exception.AuthorizationException;
import jpaUtil.JpaUtil;
import model.Patient;
import org.modelmapper.ModelMapper;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import repository.ChiefDoctorRepository;
import repository.DoctorRepository;
import repository.PatientRepository;
import representation.PatientRepresentation;
import security.Shield;

import javax.persistence.EntityManager;

public class PatientResource extends ServerResource {
    private long id;
    private EntityManager entityManager;
    private PatientServiceImpl patientService;

    protected void doInit() {
        entityManager = JpaUtil.getEntityManager();
        patientService = new PatientServiceImpl(
                new PatientRepository(entityManager),
                new DoctorRepository(entityManager),
                new ChiefDoctorRepository(entityManager),
                new ModelMapper());
        id = Long.parseLong(getAttribute("id"));
    }

    protected void doRelease() {
        entityManager.close();
    }


    @Get("json")
    public PatientRepresentation getPatient() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_CHIEF_DOCTOR);
        if (id <= 0) return null;
        return patientService.getPatient(id);
    }

    @Put("json")
    public PatientRepresentation updatePatient(PatientRepresentation patientRepresentation) throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_CHIEF_DOCTOR);
        Patient patient = patientService.updatePatient(id, patientRepresentation);
        return new PatientRepresentation(patient);
    }

    @Delete("json")
    public Boolean deletePatient() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_CHIEF_DOCTOR);
        return patientService.deletePatient(id);
    }

}
