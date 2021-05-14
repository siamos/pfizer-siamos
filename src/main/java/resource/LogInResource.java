package resource;

import Service.PatientServiceImpl;
import jpaUtil.JpaUtil;
import model.ChiefDoctor;
import model.Doctor;
import model.Patient;
import org.modelmapper.ModelMapper;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import repository.ChiefDoctorRepository;
import repository.DoctorRepository;
import repository.PatientRepository;
import representation.LoginUserRepresentation;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class LogInResource extends ServerResource {

    private EntityManager em;

    protected void doInit() {
        em = JpaUtil.getEntityManager();
    }

    protected void doRelease() {
        em.close();
    }

    //change all the function login
    @Post("json")
    public List<Integer> logIn(LoginUserRepresentation userDto) {

        PatientServiceImpl patientService = new PatientServiceImpl(
                new PatientRepository(em),
                new DoctorRepository(em),
                new ChiefDoctorRepository(em),
                new ModelMapper());

        if (userFailedValidation(userDto)) return null;

        String username = userDto.getUsername();
        String password = userDto.getPassword();

        Patient patient = patientService.isPatient(username, password);
        Doctor doctor = patientService.isDoctor(userDto.getUsername(), userDto.getPassword());
        ChiefDoctor chiefDoctor = patientService.isChiefDoctor(userDto.getUsername(), userDto.getPassword());

        return patientService.initializeUserStats(patient, doctor, chiefDoctor);
    }

    private boolean userFailedValidation(LoginUserRepresentation userDto) {
        return userDto.getUsername().isEmpty() || userDto.getPassword().isEmpty();
    }
}
