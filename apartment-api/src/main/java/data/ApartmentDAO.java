package data;

import java.sql.*;
import java.util.ArrayList;

import beans.Apartment;

public class ApartmentDAO {
	private static final String url = "jdbc:mysql://localhost:3306/apartment_rental";
	private static final String user = "root";
	private static final String pass = "root";
	// load the driver
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void aptSetPreparedStatement(PreparedStatement p, Apartment a) throws SQLException {
		p.setString(1, a.getLocation());
		p.setInt(2, a.getSqft());
		p.setInt(3, a.getBeds());
		p.setInt(4, a.getBaths());
		p.setInt(5, a.getPrice());
	}

	public Apartment rsFetchApt(ResultSet rs) throws SQLException {

		return new Apartment(rs.getString(2), rs.getInt(1), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
	}

	// create apt
	public Apartment createApartment(Apartment newApt) {
		try (Connection conn = DriverManager.getConnection(url, user, pass)) {
			String sql = "INSERT INTO apartments(location,sqft,beds,baths,price) VALUES(?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			aptSetPreparedStatement(stmt, newApt);
			stmt.executeUpdate();
			ResultSet keys = stmt.getGeneratedKeys();
			keys.next();
			int id = keys.getInt(1);
			newApt.setId(id);
			return newApt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Apartment();
	}

	// update apartment by id
	public Apartment updateAptByID(int id, Apartment apt) {
		try (Connection conn = DriverManager.getConnection(url, user, pass)) {
			String sql = "UPDATE apartments SET location = ?, sqft = ?, beds = ?, baths = ?, price = ? WHERE apartment_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			aptSetPreparedStatement(stmt, apt);
			stmt.setInt(6, apt.getId());
			stmt.executeUpdate();
			apt.setId(id);
			return apt;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return new Apartment();
	}

	// delete apartment by id
	public boolean deleteAptByID(int id) {

		try (Connection conn = DriverManager.getConnection(url, user, pass)) {
			String sql = "DELETE FROM apartments WHERE apartment_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			return stmt.executeUpdate() == 1;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return false;
	}

	// retrieve apartments by id
	public Apartment retrieveAptByID(int id) {
		try (Connection conn = DriverManager.getConnection(url, user, pass)) {
			String sql = "SELECT apartment_id, location, sqft, beds, baths, price "
					+ "FROM apartments WHERE apartment_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet aptRS = stmt.executeQuery();
			aptRS.next();
			return rsFetchApt(aptRS);

		} catch (Exception e) {
			e.getStackTrace();
		}
		return new Apartment();

	}

	// retrieve all
	public ArrayList<Apartment> retrieveAllApt() {

		try (Connection conn = DriverManager.getConnection(url, user, pass)) {
			String sql = "SELECT apartment_id, location, sqft, beds, baths, price FROM apartments";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet aptRS = stmt.executeQuery(); // ResultSet object
			ArrayList<Apartment> aptsReturn = new ArrayList<>();
			while (aptRS.next()) {
				aptsReturn.add(rsFetchApt(aptRS));
			}

			return aptsReturn;
		} catch (Exception e) {
			e.getStackTrace();
			return new ArrayList<Apartment>();
		}
	}

}
