package resource;

import Service.CarbServiceImpl;
import exception.AuthorizationException;
import jpaUtil.JpaUtil;
import model.Carb;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import repository.CarbRepository;
import representation.CarbRepresentation;
import security.Shield;

import javax.persistence.EntityManager;

public class CarbResource extends ServerResource {
    private long id;
    private EntityManager entityManager;
    protected void doInit() {
        entityManager = JpaUtil.getEntityManager();
        id = Long.parseLong(getAttribute("id"));
    }

    protected void doRelease() {
        entityManager.close();
    }

    @Get("json")
    public CarbRepresentation getCarb() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_CHIEF_DOCTOR);
        CarbServiceImpl carbService = new CarbServiceImpl(new CarbRepository(entityManager));
        return carbService.getCarb(id);
    }
}
