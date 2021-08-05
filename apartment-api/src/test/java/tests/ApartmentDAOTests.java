package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import beans.Apartment;
import data.ApartmentDAO;
import java.sql.*;
import java.util.List;

public class ApartmentDAOTests {
	private static final String url = "jdbc:mysql://localhost:3306/apartment_rental";
	private static final String user = "root";
	private static final String pass = "root";
	// load driver
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Before
	public void beforeTest() {
		try (Connection conn = DriverManager.getConnection(url, user, pass)) {
			String sql = "CREATE TABLE `apartment_rental`.`apartments_test` (\r\n"
					+ "  `apartment_id` INT NOT NULL AUTO_INCREMENT,\r\n" + "  `location` VARCHAR(45) NOT NULL,\r\n"
					+ "  `sqFt` INT NOT NULL,\r\n" + "  `beds` INT NOT NULL,\r\n" + "  `baths` INT NOT NULL,\r\n"
					+ "  `price` INT NOT NULL,\r\n" + "  PRIMARY KEY (`apartment_id`),\r\n"
					+ "  UNIQUE INDEX `apartment_id_UNIQUE` (`apartment_id` ASC) VISIBLE)";
			String sql2 = "INSERT INTO apartments_test(location, sqft,beds,baths,price) VALUES(\"firstLoc\",1,2,3,4)";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			Statement stmt2 = conn.createStatement();
			stmt2.executeUpdate(sql2);
			ApartmentDAO.setTable("apartments_test");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void creationTest() {
		ApartmentDAO a = new ApartmentDAO();
		int x = (int) (Math.round(1 + 100 * Math.random()));
		int y = (int) (Math.round(1 + 100 * Math.random()));
		int z = (int) (Math.round(1 + 100 * Math.random()));
		int u = (int) (Math.round(1 + 100 * Math.random()));
		Apartment testApt = new Apartment("testLocation", x, y, z, u);
		Apartment dbApt = a.createApartment(testApt);
		a.deleteAptByID(dbApt.getId());
		assertTrue(dbApt.getId() >= 0 && dbApt.getBaths() > 0 && dbApt.getBeds() > 0 && dbApt.getSqft() > 0
				&& dbApt.getLocation().equals("testLocation"));
	}
	@Test
	public void retrievalTest() {
		ApartmentDAO a = new ApartmentDAO();
		Apartment dbApt = a.retrieveAllApt().get(0);
		assertTrue(a.retrieveAptByID(dbApt.getId()).getId() == dbApt.getId());
	}
	@Test
	public void updateTest() {
		ApartmentDAO a = new ApartmentDAO();
		Apartment changedApt = new Apartment("updatedLocation", 5 , 6 , 7 , 8);
		Apartment inTableBefore = a.retrieveAllApt().get(0);
		int id = inTableBefore.getId();
		Apartment inTableAfter = a.updateAptByID(id, changedApt);
		changedApt.setId(id);
		assertTrue(changedApt.equals(inTableAfter));
	}
	@Test
	public void deleteTest() {
		ApartmentDAO a = new ApartmentDAO();
		Apartment dbApt = a.retrieveAllApt().get(0);
		a.deleteAptByID(dbApt.getId());
		List<Apartment> aptList = a.retrieveAllApt();
		assertTrue(aptList.size() == 0);
	}
	@After
	public void afterTest() {
		try (Connection conn = DriverManager.getConnection(url, user, pass)) {
			String sql = "DROP TABLE apartments_test";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			ApartmentDAO.setTable("apartments");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
