package security;

import jpaUtil.JpaUtil;
import model.ChiefDoctor;
import model.Doctor;
import model.Patient;
import org.restlet.Request;
import org.restlet.security.Role;
import org.restlet.security.SecretVerifier;
import repository.ChiefDoctorRepository;
import repository.DoctorRepository;
import repository.PatientRepository;

import javax.persistence.EntityManager;

public class Verifier extends SecretVerifier {
    public int verify(String username, char[] password) {
        //check db for user
        EntityManager entityManager = JpaUtil.getEntityManager();

        PatientRepository patientRepository = new PatientRepository(entityManager);
        Patient patient = patientRepository.getByUsername(username);
        if (patient != null) {
            if (patient.getUsername().equals(username)) {
                String passwordInDb = patient.getPassword();
                if (compare(passwordInDb.toCharArray(), password)) {
                    Request request = Request.getCurrent();
                    request.getClientInfo().getRoles().add
                            (new Role(patient.getRole()));
                    entityManager.close();
                    return SecretVerifier.RESULT_VALID;
                }
            }
        }
        DoctorRepository doctorRepository = new DoctorRepository(entityManager);
        Doctor doctor = doctorRepository.getByUsername(username);
        if (doctor != null) {
            if (doctor.getUsername().equals(username)) {
                String passwordInDb = doctor.getPassword();
                if (compare(passwordInDb.toCharArray(), password)) {
                    Request request = Request.getCurrent();
                    request.getClientInfo().getRoles().add
                            (new Role(doctor.getRole()));
                    entityManager.close();
                    return SecretVerifier.RESULT_VALID;
                }
            }
        }

        ChiefDoctorRepository chiefDoctorRepository = new ChiefDoctorRepository(entityManager);
        ChiefDoctor chiefDoctor = chiefDoctorRepository.getByUsername(username);
        if (chiefDoctor != null) {
            if (chiefDoctor.getUsername().equals(username)) {
                String passwordInDb = chiefDoctor.getPassword();
                if (compare(passwordInDb.toCharArray(), password)) {
                    Request request = Request.getCurrent();
                    request.getClientInfo().getRoles().add
                            (new Role(chiefDoctor.getRole()));
                    entityManager.close();
                    return SecretVerifier.RESULT_VALID;
                }
            }
        }
        entityManager.close();
        return SecretVerifier.RESULT_INVALID;
    }
}
