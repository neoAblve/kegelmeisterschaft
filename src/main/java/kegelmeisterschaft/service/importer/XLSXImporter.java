package kegelmeisterschaft.service.importer;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kegelmeisterschaft.entities.ClubBean;
import kegelmeisterschaft.entities.EventBean;
import kegelmeisterschaft.entities.PlayerBean;
import kegelmeisterschaft.entities.ResultBean;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;

public class XLSXImporter implements Importer {

    @Override
    public List<ResultBean> getResultsByClub(ClubBean club) {
	try {
	    Resource resource = new ClassRelativeResourceLoader(Importer.class).getResource(club.getYear()
		    + "/results/" + club.getName() + ".txt");
	    XSSFWorkbook workbook = new XSSFWorkbook(resource.getInputStream());
	    XSSFSheet sheet = workbook.getSheetAt(0);

	    List<ResultBean> results = new ArrayList<ResultBean>();
	    boolean first = true;
	    Iterator<Row> rowIterator = sheet.iterator();
	    while (rowIterator.hasNext()) {
		Row row = rowIterator.next();
		if (first) {
		    first = false;
		    continue; // skip header
		}

		ResultBean result = new ResultBean();
		Iterator<Cell> cellIterator = row.cellIterator();
		while (cellIterator.hasNext()) {
		    Cell cell = cellIterator.next();

		    switch (cell.getColumnIndex()) {
		    case 0:
			result.setFirstName(cell.getStringCellValue());
			break;
		    case 1:
			result.setLastName(cell.getStringCellValue());
			break;
		    case 2:
			result.setRound(Double.valueOf(cell.getNumericCellValue()).intValue());
			break;
		    case 3:
			result.setSpecialType(cell.getStringCellValue());
			break;
		    case 4:
			result.setV1(Double.valueOf(cell.getNumericCellValue()).intValue());
			break;
		    case 5:
			result.setR1(Double.valueOf(cell.getNumericCellValue()).intValue());
			break;
		    case 6:
			result.setV2(Double.valueOf(cell.getNumericCellValue()).intValue());
			break;
		    case 7:
			result.setR2(Double.valueOf(cell.getNumericCellValue()).intValue());
			break;
		    case 8:
			result.setNinerCount(Double.valueOf(cell.getNumericCellValue()).intValue());
			break;
		    case 9:
			result.setReleased(cell.getStringCellValue());
			break;
		    }
		}
		results.add(result);
	    }
	    workbook.close();

	    return results;
	} catch (Exception e) {
	    System.out.println("Keine Results für " + club.getName() + "gefunden");
	    e.printStackTrace();
	    return new ArrayList<ResultBean>();
	}
    }

    @Override
    public List<PlayerBean> getPlayerByClub(ClubBean club) throws IOException {
	Resource resource = new ClassRelativeResourceLoader(Importer.class).getResource(club.getYear() + "/player/"
		+ club.getName() + ".xlsx");
	XSSFWorkbook workbook = new XSSFWorkbook(resource.getInputStream());
	XSSFSheet sheet = workbook.getSheetAt(0);

	List<PlayerBean> players = new ArrayList<PlayerBean>();
	boolean first = true;
	Iterator<Row> rowIterator = sheet.iterator();
	while (rowIterator.hasNext()) {
	    Row row = rowIterator.next();
	    if (first) {
		first = false;
		continue; // skip header
	    }

	    PlayerBean player = new PlayerBean();
	    Iterator<Cell> cellIterator = row.cellIterator();
	    while (cellIterator.hasNext()) {
		Cell cell = cellIterator.next();

		switch (cell.getColumnIndex()) {
		case 0:
		    player.setFirstName(cell.getStringCellValue());
		    break;
		case 1:
		    player.setLastName(cell.getStringCellValue());
		    break;
		case 2:
		    player.setGender(cell.getStringCellValue());
		    break;
		case 3:
		    player.setSingleRelevant(cell.getBooleanCellValue());
		    break;
		}
	    }
	    players.add(player);
	}
	workbook.close();

	return players;
    }

    @Override
    public List<EventBean> getEventsByYear(String year) throws IOException, ParseException {
	Resource resource = new ClassRelativeResourceLoader(Importer.class).getResource(year + "/events.xlsx");
	XSSFWorkbook workbook = new XSSFWorkbook(resource.getInputStream());
	XSSFSheet sheet = workbook.getSheetAt(0);

	List<EventBean> events = new ArrayList<EventBean>();
	boolean first = true;
	Iterator<Row> rowIterator = sheet.iterator();
	while (rowIterator.hasNext()) {
	    Row row = rowIterator.next();
	    if (first) {
		first = false;
		continue; // skip header
	    }

	    EventBean event = new EventBean();
	    Iterator<Cell> cellIterator = row.cellIterator();
	    while (cellIterator.hasNext()) {
		Cell cell = cellIterator.next();

		switch (cell.getColumnIndex()) {
		case 0:
		    event.setDateImport(cell.getStringCellValue());
		    break;
		case 1:
		    event.setClubName(cell.getStringCellValue());
		    break;
		case 2:
		    event.setRound(Double.valueOf(cell.getNumericCellValue()).intValue());
		    break;
		case 3:
		    event.setLocation(cell.getStringCellValue());
		    break;
		case 4:
		    event.setChecker1ClubImport(cell.getStringCellValue());
		    break;
		case 5:
		    event.setChecker2ClubImport(cell.getStringCellValue());
		    break;
		}
	    }
	    events.add(event);
	}
	workbook.close();

	return events;
    }

    @Override
    public List<ClubBean> getClubsByYear(String year) throws IOException {
	Resource resource = new ClassRelativeResourceLoader(Importer.class).getResource(year + "/clubs.xlsx");
	XSSFWorkbook workbook = new XSSFWorkbook(resource.getInputStream());
	XSSFSheet sheet = workbook.getSheetAt(0);

	List<ClubBean> clubs = new ArrayList<ClubBean>();
	boolean first = true;
	Iterator<Row> rowIterator = sheet.iterator();
	while (rowIterator.hasNext()) {
	    Row row = rowIterator.next();
	    if (first) {
		first = false;
		continue; // skip header
	    }

	    ClubBean club = new ClubBean();
	    Iterator<Cell> cellIterator = row.cellIterator();
	    while (cellIterator.hasNext()) {
		Cell cell = cellIterator.next();

		switch (cell.getColumnIndex()) {
		case 0:
		    club.setName(cell.getStringCellValue());
		    break;
		case 1:
		    club.setTypeFromString(cell.getStringCellValue());
		    break;
		case 2:
		    club.setFoundingYear(String.valueOf(Double.valueOf(cell.getNumericCellValue()).intValue()));
		    break;
		case 3:
		    club.setMemberCount(Double.valueOf(cell.getNumericCellValue()).intValue());
		    break;
		case 4:
		    club.setLocation(cell.getStringCellValue());
		    break;
		}
	    }
	    clubs.add(club);
	}
	workbook.close();

	return clubs;
    }

}
