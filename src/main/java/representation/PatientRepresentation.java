package representation;

import jpaUtil.JpaUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Patient;
import repository.DoctorRepository;

import javax.persistence.EntityManager;
import java.util.Date;

@Data
@NoArgsConstructor
public class PatientRepresentation {
    private long id;
    private String username;
    private String password;
    private String name;
    private String address;
    private String email;
    private int age;
    private String sex;
    private long doctorId;
    private Date dateRegistered;
    private boolean consultationChanged;
    private String uri;

    public PatientRepresentation(Patient patient) {
        if (patient != null) {
            id = patient.getId();
            username = patient.getUsername();
            password = patient.getPassword();
            name = patient.getName();
            address = patient.getAddress();
            email = patient.getEmail();
            age = patient.getAge();
            sex = patient.getSex();
            dateRegistered = patient.getDateRegistered();
            if (patient.getDoctor() != null) {
                doctorId = patient.getDoctor().getId();
            }
            consultationChanged = patient.isConsultationChanged();

            uri = "http://localhost:9000/v1/patient/" + patient.getId();
        }
    }
}
