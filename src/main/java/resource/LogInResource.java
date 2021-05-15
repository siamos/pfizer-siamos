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

    private EntityManager entityManager;
    private PatientServiceImpl patientService;

    protected void doInit() {
        entityManager = JpaUtil.getEntityManager();
        patientService = new PatientServiceImpl(
                new PatientRepository(entityManager),
                new DoctorRepository(entityManager),
                new ChiefDoctorRepository(entityManager),
                new ModelMapper());
    }

    protected void doRelease() {
        entityManager.close();
    }

    //change all the function login
    @Post("json")
    public List<Integer> logIn(LoginUserRepresentation userDto) {

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
