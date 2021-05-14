package Service;

import Interface.PatientInterface;
import model.ChiefDoctor;
import model.Doctor;
import model.Patient;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import repository.ChiefDoctorRepository;
import repository.DoctorRepository;
import repository.PatientRepository;
import representation.PatientRepresentation;
import java.util.ArrayList;
import java.util.List;


public class PatientServiceImpl implements PatientInterface {

    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;
    private ChiefDoctorRepository chiefDoctorRepository;
    private ModelMapper modelMapper;

    public PatientServiceImpl(PatientRepository patientRepository,
                              DoctorRepository doctorRepository,
                              ChiefDoctorRepository chiefDoctorRepository,
                              ModelMapper modelMapper) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.chiefDoctorRepository = chiefDoctorRepository;
        this.modelMapper = modelMapper;
    }

    public Patient createPatient(PatientRepresentation patientDto) {
        Patient patient = modelMapper.map(patientDto, Patient.class);
        patient.setDoctor(this.doctorRepository.read(patientDto.getDoctorId()));
        this.patientRepository.save(patient);
        return patient;
    }

    public Patient isPatient(String username, String password) {
        Patient patient = this.patientRepository.getByUsername(username);
        if (patient != null) {
            if (patient.getPassword().equals(password)) {
                return patient;
            }
        }
        return null;
    }

    public List<Integer> initializeUserStats(Patient patient, Doctor doctor, ChiefDoctor chiefDoctor) {
        List<Integer> result = new ArrayList<>(3);

        if (patient != null) {
            result.add(1);
            result.add((int) patient.getId());
            result.add(patient.isConsultationChanged() ? 1 : 0);
            resetFlag(patient);
        }
        if (doctor != null) {
            result.add(2);
            result.add((int) doctor.getId());
        }

        if (chiefDoctor != null) {
            result.add(3);
            result.add((int) chiefDoctor.getId());
        }

        return result;
    }

    public void resetFlag(Patient patient) {
        patient.setConsultationChanged(false);
        this.patientRepository.update(patient);
    }

    public Doctor isDoctor(String username, String password) {
        Doctor doctor = this.doctorRepository.getByUsername(username);
        if (doctor != null) {
            if (doctor.getUsername().equals(username) && doctor.getPassword().equals(password)) {
                return doctor;
            }
        }
        return null;
    }

    public ChiefDoctor isChiefDoctor(String username, String password) {
        ChiefDoctor chiefDoctor = this.chiefDoctorRepository.getByUsername(username);
        if (chiefDoctor != null) {
            if (chiefDoctor.getUsername().equals(username) && chiefDoctor.getPassword().equals(password)) {
                return chiefDoctor;
            }
        }
        return null;
    }

    public PatientRepresentation getPatient(long id) {
        Patient patient = this.patientRepository.read(id);
        return new PatientRepresentation(patient);
    }

    public Boolean deletePatient(long id) {
       return this.patientRepository.delete(this.patientRepository.read(id));
    }

    public Patient updatePatient(long id, PatientRepresentation patientDto) {
        Patient patient = this.patientRepository.read(id);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(patientDto, patient);
        patient.setId(id);
        if(patientDto.getDoctorId() == 0) {
            patient.setDoctor(null);
        }
        return this.patientRepository.update(patient);
    }
}
