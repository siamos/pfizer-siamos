package resource;

import Service.PatientServiceImpl;
import exception.AuthorizationException;
import jpaUtil.JpaUtil;
import model.Patient;
import org.modelmapper.ModelMapper;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import repository.ChiefDoctorRepository;
import repository.DoctorRepository;
import repository.PatientRepository;
import representation.PatientRepresentation;

import javax.persistence.EntityManager;

public class registerResource extends ServerResource {

    private EntityManager entityManager;
    protected void doInit() {
        entityManager = JpaUtil.getEntityManager();
    }

    protected void doRelease() {
        entityManager.close();
    }

    @Post("json")
    public Patient add(PatientRepresentation patientRepresentationIn) throws AuthorizationException {

        if (patientFailedValidation(patientRepresentationIn)) return null;

        PatientServiceImpl patientService = new PatientServiceImpl(
                new PatientRepository(entityManager),
                new DoctorRepository(entityManager),
                new ChiefDoctorRepository(entityManager),
                new ModelMapper());

        return patientService.createPatient(patientRepresentationIn);
    }

    private boolean patientFailedValidation(PatientRepresentation patientRepresentationIn) {
        return patientRepresentationIn == null
                || patientRepresentationIn.getUsername() == null
                || patientRepresentationIn.getPassword().isEmpty();
    }

}
