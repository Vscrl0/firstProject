package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Apartment;
import data.ApartmentDAO;

@WebServlet(name = "aptServlet", urlPatterns = "/apartments")
public class aptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ApartmentDAO a = new ApartmentDAO();

	public aptServlet() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(201); // "return"
        response.setContentType("application/json");
		ArrayList<Apartment> allApt = a.retrieveAllApt();
		String json = new ObjectMapper().writeValueAsString(allApt);
		response.getWriter().print(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        response.setStatus(201); // "return"
        response.setContentType("application/json");
		Apartment aptNoId = new ObjectMapper().readValue(request.getReader(), Apartment.class);
		Apartment newApt = a.createApartment(aptNoId);
		String json = new ObjectMapper().writeValueAsString(newApt);
		response.getWriter().print(json);

	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        response.setStatus(201); // "return"
        response.setContentType("application/json");
		Apartment aptBefore = new ObjectMapper().readValue(request.getReader(), Apartment.class);
		Apartment aptAfter = a.updateAptByID(aptBefore.getId(), aptBefore);
		String json = new ObjectMapper().writeValueAsString(aptAfter);
		response.getWriter().print(json);

	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Apartment aptToDel = new ObjectMapper().readValue(request.getReader(), Apartment.class);
		a.deleteAptByID(aptToDel.getId());
	}
}
