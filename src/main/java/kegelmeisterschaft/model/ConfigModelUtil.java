package kegelmeisterschaft.model;

import java.util.HashMap;
import java.util.Map;

public final class ConfigModelUtil {

    @SuppressWarnings("serial")
    private static final Map<String, String> VIEW_YEARS = new HashMap<String, String>() {
	{
	    put("2014", "2015");
	    put("2015", "2014");
	}
    };

    public static String getOtherYear(String viewYear) {
	return VIEW_YEARS.get(viewYear);
    }

}
