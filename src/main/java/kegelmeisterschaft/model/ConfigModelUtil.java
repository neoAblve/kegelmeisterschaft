package kegelmeisterschaft.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

public final class ConfigModelUtil {

    public static final String RECENT_YEAR = "2016";

    @SuppressWarnings("serial")
    private static final Map<String, List<String>> VIEW_YEARS = new HashMap<String, List<String>>() {
	{
	    put("2014", Lists.newArrayList("2016", "2015"));
	    put("2015", Lists.newArrayList("2016", "2014"));
	    put("2016", Lists.newArrayList("2015", "2014"));
	}
    };

    public static List<String> getOtherYears(String viewYear) {
	return VIEW_YEARS.get(viewYear);
    }
}
