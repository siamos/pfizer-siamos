package Interface;

import model.ChiefDoctor;
import model.Doctor;
import model.Patient;
import representation.PatientRepresentation;

import java.util.List;

public interface PatientInterface {
    Patient createPatient(PatientRepresentation patientDto);
    Patient isPatient(String username, String password);
    List<Integer> initializeUserStats(Patient patient, Doctor doctor, ChiefDoctor chiefDoctor);
    Doctor isDoctor(String username, String password);
    ChiefDoctor isChiefDoctor(String username, String password);
    PatientRepresentation getPatient(long id);
    Boolean deletePatient(long id);
}
