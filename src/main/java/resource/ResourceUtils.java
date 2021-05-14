package resource;

import exception.AuthorizationException;
import org.restlet.resource.ServerResource;
import org.restlet.security.Role;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ResourceUtils {
    public static void checkRole(ServerResource serverResource, String role) throws AuthorizationException {
        List<Role> roles = serverResource.getRequest().getClientInfo().getRoles();
        if (!roles.get(0).getName().equalsIgnoreCase(role)) {
            throw new AuthorizationException("You're not authorized to send this call.");
        }
    }

    public static Date stringToDate(String date, int offset) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(formatter.parse(date));
            c.add(Calendar.DAY_OF_MONTH, offset);
            return c.getTime();
        } catch (ParseException e) {
            return null;
        }
    }

    public static String dateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        return formatter.format(date);
    }
}
