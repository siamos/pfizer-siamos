package resource;

import exception.AuthorizationException;
import jpaUtil.JpaUtil;
import model.Doctor;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import repository.DoctorRepository;
import representation.DoctorRepresentation;
import security.Shield;

import javax.persistence.EntityManager;

public class DoctorResource extends ServerResource {
    private long id;

    protected void doInit() {
        id = Long.parseLong(getAttribute("id"));
    }


    @Get("json")
    public DoctorRepresentation getDoctor() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_CHIEF_DOCTOR);
        EntityManager entityManager = JpaUtil.getEntityManager();
        DoctorRepository doctorRepository = new DoctorRepository(entityManager);
        Doctor doctor = doctorRepository.read(id);
        DoctorRepresentation doctorRepresentation = new DoctorRepresentation(doctor);
        entityManager.close();
        return doctorRepresentation;
    }

    @Put("json")
    public DoctorRepresentation updateDoctor(DoctorRepresentation doctorRepresentation) throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_CHIEF_DOCTOR);
        EntityManager entityManager = JpaUtil.getEntityManager();
        DoctorRepository doctorRepository = new DoctorRepository(entityManager);
        Doctor doctor = doctorRepresentation.createDoctor();
        entityManager.detach(doctor);
        doctor.setId(id);
        doctorRepository.update(doctor);
        return doctorRepresentation;
    }

    @Delete("json")
    public void deleteDoctor() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_CHIEF_DOCTOR);
        EntityManager entityManager = JpaUtil.getEntityManager();
        DoctorRepository doctorRepository = new DoctorRepository(entityManager);
        doctorRepository.delete(doctorRepository.read(id));
    }

}
