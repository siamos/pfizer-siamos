package resource;

import exception.AuthorizationException;
import jpaUtil.JpaUtil;
import model.Glucose;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import repository.GlucoseRepository;
import representation.GlucoseRepresentation;
import security.Shield;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GlucoseListResource extends ServerResource {
    @Get("json")
    public List<GlucoseRepresentation> getGlucose() throws AuthorizationException {
        resource.ResourceUtils.checkRole(this, Shield.ROLE_CHIEF_DOCTOR);
        EntityManager entityManager = JpaUtil.getEntityManager();
        GlucoseRepository glucoseRepository = new GlucoseRepository(entityManager);
        List<Glucose> glucoses = glucoseRepository.findAll(0,10);
        entityManager.close();

        List<GlucoseRepresentation> glucoseRepresentationList = new ArrayList<>();
        for (Glucose p : glucoses)
            glucoseRepresentationList.add(new GlucoseRepresentation(p));

        return glucoseRepresentationList;
    }

    @Post("json")
    public GlucoseRepresentation add(GlucoseRepresentation glucoseRepresentationIn) throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_CHIEF_DOCTOR);
        if (glucoseRepresentationIn == null) return null;

        Glucose glucose = glucoseRepresentationIn.createGlucose();
        if (glucoseRepresentationIn.getDate() == null) glucose.setDate(new Date());
        EntityManager entityManager = JpaUtil.getEntityManager();
        GlucoseRepository glucoseRepository = new GlucoseRepository(entityManager);
        glucoseRepository.save(glucose);
        GlucoseRepresentation p = new GlucoseRepresentation(glucose);
        return p;
    }
}
