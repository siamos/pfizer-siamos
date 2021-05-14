package router;

import Interface.PatientRouterInterface;
import org.restlet.routing.Router;
import resource.patient.*;

public class PatientRouter implements PatientRouterInterface {

    public Router initializePatientRouter(Router router) {

        router.attach("/patientSettings/{id}", PatientSettingsResource.class);
        router.attach("/patient/{patientId}/carb/{carbId}", PatientCarbResource.class);
        router.attach("/patient/{patientId}/carb/", PatientCarbListResource.class);
        router.attach("/patient/{patientId}/glucose/{glucoseId}", PatientGlucoseResource.class);
        router.attach("/patient/{patientId}/glucose/", PatientGlucoseListResource.class);
        router.attach("/patient/{patientId}/consultation/{consultationId}", PatientConsultationResource.class);
        router.attach("/patient/{patientId}/consultation/", PatientConsultationListResource.class);
        router.attach("/patientCarbDailyAverage/{patientId}", PatientCarbDailyAverageResource.class); //get
        router.attach("/patientCarbAverage/{patientId}", PatientCarbAverageResource.class);
        router.attach("/patientGlucoseAverage/{patientId}", PatientGlucoseAverageResource.class); //get

        return router;
    }
}
