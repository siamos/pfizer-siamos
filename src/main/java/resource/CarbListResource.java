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

    private EntityManager em;
    protected void doInit() {
        em = JpaUtil.getEntityManager();
    }

    protected void doRelease() {
        em.close();
    }

    @Get("json")
    public List<CarbRepresentation> getCarb() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_CHIEF_DOCTOR);
        CarbServiceImpl carbService = new CarbServiceImpl(new CarbRepository(em));
        List<Carb> carbs = carbService.findAllCarbs(0,10);
        return carbService.createCarbRepresentationList(carbs);
    }

    @Post("json")
    public CarbRepresentation add(CarbRepresentation carbRepresentationIn) throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_CHIEF_DOCTOR);
        if (carbRepresentationIn == null) return null;

        Carb carb = carbRepresentationIn.createCarb();
        if (carb.getDate() == null) carb.setDate(new Date());

        EntityManager em = JpaUtil.getEntityManager();
        CarbRepository carbRepository = new CarbRepository(em);
        carbRepository.save(carb);
        CarbRepresentation p = new CarbRepresentation(carb);
        return p;
    }
}
