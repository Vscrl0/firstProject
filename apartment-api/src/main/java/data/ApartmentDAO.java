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

	// create apt
	public Apartment createApartment(Apartment newApt) {
		try (Connection conn = DriverManager.getConnection(url, user, pass)) {
			String sql = "INSERT INTO apartments(location,sqft,beds,baths,price) VALUES(?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, newApt.getLocation());
			stmt.setInt(2, newApt.getSqft());
			stmt.setInt(3, newApt.getBeds());
			stmt.setInt(4, newApt.getBaths());
			stmt.setInt(5, newApt.getPrice());
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
			stmt.setString(1, apt.getLocation());
			stmt.setInt(2, apt.getSqft());
			stmt.setInt(3, apt.getBeds());
			stmt.setInt(4, apt.getBaths());
			stmt.setInt(5, apt.getPrice());
			stmt.setInt(6, apt.getId());
			stmt.executeUpdate();
			return new Apartment(apt.getLocation(),apt.getId(), apt.getSqft(), apt.getBeds(),apt.getBaths(),apt.getPrice());
		} catch (Exception e) {
			e.getStackTrace();
		}
		return new Apartment();
	}

	// delete apartment by id
	public boolean deleteAptByID(int id) {
		
		try(Connection conn = DriverManager.getConnection(url, user, pass)) {
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
			String sql = "SELECT apartment_id, location, sqft, beds, baths, price FROM apartments WHERE apartment_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet aptRS = stmt.executeQuery();
			aptRS.next();
			String loc = aptRS.getString(2);
			int sqft = aptRS.getInt(3);
			int beds = aptRS.getInt(4);
			int baths = aptRS.getInt(5);
			int price = aptRS.getInt(6);
			return new Apartment(loc, id, sqft, beds, baths, price);

		} catch (Exception e) {
			e.getStackTrace();
		}
		return new Apartment();

	}

	// retrieve all
	public ArrayList<Apartment> retrieveAllApt()  {

		try (Connection conn = DriverManager.getConnection(url, user, pass)) {
			String sql = "SELECT apartment_id, location, sqft, beds, baths, price FROM apartments";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet aptRS = stmt.executeQuery();
			ArrayList<Apartment> aptsReturn = new ArrayList<>();
			while (aptRS.next()) {
				int id = aptRS.getInt(1);
				String loc = aptRS.getString(2);
				int sqft = aptRS.getInt(3);
				int beds = aptRS.getInt(4);
				int baths = aptRS.getInt(5);
				int price = aptRS.getInt(6);
				aptsReturn.add(new Apartment(loc, id, sqft, beds, baths, price));
				
			}
			return aptsReturn;
		} catch (Exception e) {
			e.getStackTrace();
			return new ArrayList<Apartment>();
		}
	}
	
}
