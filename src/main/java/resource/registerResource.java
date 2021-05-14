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

    private EntityManager em;
    protected void doInit() {
        em = JpaUtil.getEntityManager();
    }

    protected void doRelease() {
        em.close();
    }

    @Post("json")
    public Patient add(PatientRepresentation patientRepresentationIn) throws AuthorizationException {

        if (patientFailedValidation(patientRepresentationIn)) return null;

        PatientServiceImpl patientService = new PatientServiceImpl(
                new PatientRepository(em),
                new DoctorRepository(em),
                new ChiefDoctorRepository(em),
                new ModelMapper());

        return patientService.createPatient(patientRepresentationIn);
    }

    private boolean patientFailedValidation(PatientRepresentation patientRepresentationIn) {
        return patientRepresentationIn == null
                || patientRepresentationIn.getUsername() == null
                || patientRepresentationIn.getPassword().isEmpty();
    }

}
