package resource.doctor;

import Service.PatientServiceImpl;
import exception.AuthorizationException;
import jpaUtil.JpaUtil;
import model.Patient;
import org.modelmapper.ModelMapper;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import repository.ChiefDoctorRepository;
import repository.DoctorRepository;
import repository.PatientRepository;
import representation.PatientRepresentation;
import resource.ResourceUtils;
import security.Shield;

import javax.persistence.EntityManager;
import java.util.List;

public class DoctorNeedConsultationPatientResource extends ServerResource {
    private long doctorId;
    private long needConsultationPatientId;

    protected void doInit() {
        doctorId = Long.parseLong(getAttribute("doctorId"));
        needConsultationPatientId = Long.parseLong(getAttribute("needConsultationPatientId"));
    }

    @Get("json")
    public PatientRepresentation getPatient() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_DOCTOR);
        EntityManager entityManager = JpaUtil.getEntityManager();
        DoctorRepository doctorRepository = new DoctorRepository(entityManager);
        List<Patient> patientList = doctorRepository.getNeedConsultationPatientList(this.doctorId);

        Patient patient = new Patient();
        for (Patient p : patientList) {
            if (p.getId() == needConsultationPatientId) {
                patient = p;
            }
        }
        PatientRepresentation patientRepresentation = new PatientRepresentation(patient);

        entityManager.close();

        return patientRepresentation;
    }

    @Put("json")
    public PatientRepresentation updatePatient(PatientRepresentation patientRepresentation) throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_DOCTOR);
        EntityManager entityManager = JpaUtil.getEntityManager();
        PatientRepository patientRepository = new PatientRepository(entityManager);
        PatientServiceImpl patientService = new PatientServiceImpl(
                new PatientRepository(entityManager),
                new DoctorRepository(entityManager),
                new ChiefDoctorRepository(entityManager),
                new ModelMapper());        Patient patient = patientService.createPatient(patientRepresentation);
        entityManager.detach(patient);
        patient.setId(needConsultationPatientId);
        patientRepository.update(patient);
        return patientRepresentation;
    }
}
