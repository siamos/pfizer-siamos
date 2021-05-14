package router;

import Interface.ChiefDoctorRouterInterface;
import org.restlet.routing.Router;
import resource.*;
import resource.chiefDoctor.*;

public class ChiefDoctorRouter implements ChiefDoctorRouterInterface{

    public Router initializeChiefDoctorRouter(Router router) {

        router.attach("/chiefDoctor", ChiefDoctorListResource.class);
        router.attach("/chiefDoctor/{id}", ChiefDoctorResource.class);
        router.attach("/patient", PatientListResource.class);
        router.attach("/patient/{id}", PatientResource.class);
        router.attach("/doctor", DoctorListResource.class);
        router.attach("/doctor/{id}", DoctorResource.class);

        router.attach("/carbs", CarbListResource.class);
        router.attach("/carb/{id}", CarbResource.class);
        router.attach("/glucose", GlucoseListResource.class);
        router.attach("/glucose/{id}", GlucoseResource.class);
        router.attach("/consultation", ConsultationListResource.class);
        router.attach("/consultation/{id}", ConsultationResource.class);

        router.attach("/reportPatientCarb/{patientId}", ReportPatientCarbListResource.class); //get
        router.attach("/reportPatientGlucose/{patientId}", ReportPatientGlucoseListResource.class); //get
        router.attach("/reportDoctorConsultation/{doctorId}", ReportDoctorConsultationListResource.class);
        router.attach("/reportUnconsultedPatient/", ReportUnconsultedPatientListResource.class);//get more than a month
        router.attach("/reportUnconsultedPatientDiff/", ReportUnconsultedPatientDiffListResource.class);
        router.attach("/patientInactive", PatientInactiveListResource.class);
        router.attach("/doctorInactive", DoctorInactiveListResource.class);

        return router;
    }
}
