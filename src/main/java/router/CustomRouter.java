package router;

import org.restlet.Application;
import org.restlet.routing.Router;
import resource.*;

public class CustomRouter {

    private Application application;

    public CustomRouter(Application application) {
        this.application = application;
    }


    public Router publicResources() {
        Router router = new Router();
        router.attach("/ping", PingServerResource.class);
        router.attach("/login", LogInResource.class);
        router.attach("/register", registerResource.class);

        return router;
    }


    public Router protectedResources() {
        Router router = new Router();
        router = (new PatientRouter()).initializePatientRouter(router);
        router = (new DoctorRouter()).initializeDoctorRouter(router);
        router = (new ChiefDoctorRouter()).initializeChiefDoctorRouter(router);

        return router;
    }
}