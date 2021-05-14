package router;

import Interface.DoctorRouterInterface;
import org.restlet.routing.Router;
import resource.doctor.*;

public class DoctorRouter implements DoctorRouterInterface {
    @Override
    public Router initializeDoctorRouter(Router router) {

        router.attach("/doctor/{doctorId}/patient/", DoctorPatientListResource.class);
        router.attach("/doctor/{doctorId}/patient/{patientId}", DoctorPatientResource.class);
        router.attach("/doctor/{patientId}/carb/", DoctorPatientCarbListResource.class);
        router.attach("/doctor/{patientId}/glucose/", DoctorPatientGlucoseListResource.class);
        router.attach("/doctorPatient/{patientId}/consultation/", DoctorPatientConsultationListResource.class);
        router.attach("/doctorPatient/{patientId}/consultation/{consultationId}", DoctorPatientConsultationResource.class);

        router.attach("/doctor/{doctorId}/unconsultedPatients/", DoctorUnconsultedPatientListResource.class);//
        router.attach("/doctor/{doctorId}/unconsultedPatient/{unconsultedPatientId}", DoctorUnconsultedPatientResource.class);//
        router.attach("/doctor/{doctorId}/needConsultationPatients/", DoctorNeedConsultationPatientListResource.class);//
        router.attach("/doctor/{doctorId}/needConsultationPatient/{needConsultationPatientId}", DoctorNeedConsultationPatientResource.class);//
        router.attach("/doctor/{doctorId}/consultation/", DoctorConsultationListResource.class);// put
        router.attach("/doctor/{doctorId}/consultation/{consultationId}", DoctorConsultationResource.class);

        return router;
    }
}
