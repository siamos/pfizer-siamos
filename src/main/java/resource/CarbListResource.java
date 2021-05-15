package resource;

import Service.CarbServiceImpl;
import exception.AuthorizationException;
import jpaUtil.JpaUtil;
import model.Carb;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import repository.CarbRepository;
import representation.CarbRepresentation;
import security.Shield;

import javax.persistence.EntityManager;

import java.util.Date;
import java.util.List;

public class CarbListResource extends ServerResource {

    private EntityManager entityManager;
    protected void doInit() {
        entityManager = JpaUtil.getEntityManager();
    }

    protected void doRelease() {
        entityManager.close();
    }

    @Get("json")
    public List<CarbRepresentation> getCarb() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_CHIEF_DOCTOR);
        CarbServiceImpl carbService = new CarbServiceImpl(new CarbRepository(entityManager));
        List<Carb> carbs = carbService.findAllCarbs(0,10);
        return carbService.createCarbRepresentationList(carbs);
    }

    @Post("json")
    public CarbRepresentation add(CarbRepresentation carbRepresentationIn) throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_CHIEF_DOCTOR);
        if (carbRepresentationIn == null) return null;

        Carb carb = carbRepresentationIn.createCarb();
        if (carb.getDate() == null) carb.setDate(new Date());

        EntityManager entityManager = JpaUtil.getEntityManager();
        CarbRepository carbRepository = new CarbRepository(entityManager);
        carbRepository.save(carb);
        CarbRepresentation p = new CarbRepresentation(carb);
        return p;
    }
}
