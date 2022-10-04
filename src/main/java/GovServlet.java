import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import jakarta.servlet.annotation.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import okhttp3.Request;

/**
 * Servlet implementation class app.GovServlet
 */
@WebServlet("/GovServlet")
public class GovServlet extends HttpServlet {
	private static final long serialVersionUID = 102831973239L;

	ArrayList<Organization> org;
	ArrayList<Category> cat;
	ArrayList<Subcategory> sub;
	ArrayList<Useful> usefuls;
	ArrayList<Service> services;


    /**
     * Default constructor. 
     */
    public GovServlet() {
		if (this.services == null) {
			this.org = Controller.getOrgData();
			this.cat = Controller.getCatData();
			this.sub = Controller.getGegData();
			this.usefuls = new ArrayList<>();
			this.services = Controller.getAPIServiceData(org, cat, sub, usefuls);
			this.usefuls.addAll(Controller.getAPIUsefulData(services));
		}
        // TODO Auto-generated constructor stub
	  }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Controller controller = new Controller();
		CheckController chController = new CheckController();
		CrossCheck cross = new CrossCheck();
		File result = null;
        //ApiData
		if (request.getParameter("get_data") != null) {
			getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
		}
		if (request.getParameter("monthly_report") != null) {
			result = controller.ServiceWriter(services);
        } 
		
		if (request.getParameter("check_links_services") != null) {
            result = chController.checkServices(services);
		}
		
		if (request.getParameter("check_links_usefuls") != null) {
            result = chController.checkUsefuls(usefuls);
		}
		
		if (request.getParameter("check_Changes_Without") != null) {
        	//crossCheck data
            result = cross.checkGov(org, cat, sub, services);
        }
		
		if (request.getParameter("check_Changes_With") != null) {
        	//crossCheck data
			cross.updateJson(org, cat, sub, services);
		}
		if (result!= null) {
			// Get PrintWriter object
			PrintWriter out = response.getWriter();
			// File name
			String fileName = result.getName();
			// File path
			String filePath = result.getAbsolutePath();
			// Set the content type and header of the response.
			request.setAttribute("url", filePath);
			request.setAttribute("filename", fileName);

		}
		getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		try {
			doGet(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
