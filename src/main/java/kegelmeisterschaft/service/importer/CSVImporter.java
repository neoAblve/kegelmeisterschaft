package kegelmeisterschaft.service.importer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kegelmeisterschaft.entities.ClubBean;
import kegelmeisterschaft.entities.EventBean;
import kegelmeisterschaft.entities.PlayerBean;
import kegelmeisterschaft.entities.ResultBean;

import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public class CSVImporter implements Importer {

    @Override
    public List<ResultBean> getResultsByClub(ClubBean club) {
	try {
	    Map<String, String> columnMapping = new HashMap<String, String>();
	    columnMapping.put("firstName", "firstName");
	    columnMapping.put("lastName", "lastName");
	    columnMapping.put("round", "round");
	    columnMapping.put("specialType", "specialType");
	    columnMapping.put("v1", "v1");
	    columnMapping.put("r1", "r1");
	    columnMapping.put("v2", "v2");
	    columnMapping.put("r2", "r2");
	    columnMapping.put("9er", "ninerCount");
	    columnMapping.put("released", "released");

	    HeaderColumnNameTranslateMappingStrategy<ResultBean> strategy = new HeaderColumnNameTranslateMappingStrategy<ResultBean>();
	    strategy.setType(ResultBean.class);
	    strategy.setColumnMapping(columnMapping);

	    Resource resource = new ClassRelativeResourceLoader(Importer.class).getResource(club.getYear()
		    + "/results/" + club.getName() + ".txt");
	    CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream(), "UTF-16"), '\t');
	    CsvToBean<ResultBean> bean = new CsvToBean<ResultBean>();
	    return bean.parse(strategy, reader);
	} catch (Exception e) {
	    System.out.println("Keine Results für " + club.getName() + "gefunden");
	    e.printStackTrace();
	    return new ArrayList<ResultBean>();
	}
    }

    @Override
    public List<PlayerBean> getPlayerByClub(ClubBean club) throws IOException {
	Map<String, String> columnMapping = new HashMap<String, String>();
	columnMapping.put("firstName", "firstName");
	columnMapping.put("lastName", "lastName");
	columnMapping.put("gender", "gender");
	columnMapping.put("singleRelevant", "singleRelevant");

	HeaderColumnNameTranslateMappingStrategy<PlayerBean> strategy = new HeaderColumnNameTranslateMappingStrategy<PlayerBean>();
	strategy.setType(PlayerBean.class);
	strategy.setColumnMapping(columnMapping);

	Resource resource = new ClassRelativeResourceLoader(Importer.class).getResource(club.getYear() + "/player/"
		+ club.getName() + ".txt");
	CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream(), "UTF-16"), '\t');
	CsvToBean<PlayerBean> bean = new CsvToBean<PlayerBean>();
	return bean.parse(strategy, reader);
    }

    @Override
    public List<EventBean> getEventsByYear(String year) throws IOException {
	Map<String, String> columnMapping = new HashMap<String, String>();
	columnMapping.put("date", "dateImport");
	columnMapping.put("clubName", "clubName");
	columnMapping.put("round", "round");
	columnMapping.put("location", "location");
	columnMapping.put("checker1Club", "checker1ClubImport");
	columnMapping.put("checker2Club", "checker2ClubImport");

	HeaderColumnNameTranslateMappingStrategy<EventBean> strategy = new HeaderColumnNameTranslateMappingStrategy<EventBean>();
	strategy.setType(EventBean.class);
	strategy.setColumnMapping(columnMapping);

	Resource resource = new ClassRelativeResourceLoader(Importer.class).getResource(year + "/events.txt");
	CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream(), "UTF-16"), '\t');
	CsvToBean<EventBean> bean = new CsvToBean<EventBean>();
	return bean.parse(strategy, reader);
    }

    @Override
    public List<ClubBean> getClubsByYear(String year) throws IOException {
	Map<String, String> columnMapping = new HashMap<String, String>();
	columnMapping.put("name", "name");
	columnMapping.put("type", "typefromString");
	columnMapping.put("foundingYear", "foundingYear");
	columnMapping.put("memberCount", "memberCount");
	columnMapping.put("location", "location");

	HeaderColumnNameTranslateMappingStrategy<ClubBean> strategy = new HeaderColumnNameTranslateMappingStrategy<ClubBean>();
	strategy.setType(ClubBean.class);
	strategy.setColumnMapping(columnMapping);

	Resource resource = new ClassRelativeResourceLoader(Importer.class).getResource(year + "/clubs.txt");
	CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream(), "UTF-16"), '\t');
	CsvToBean<ClubBean> bean = new CsvToBean<ClubBean>();
	return bean.parse(strategy, reader);
    }
}
