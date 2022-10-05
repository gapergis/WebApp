import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Servlet implementation class app.GovServlet
 */
@WebServlet("/GovServlet")
public class GovServlet extends HttpServlet {
    private static final long serialVersionUID = 102831973239L;
    private LocalDateTime dataDate= LocalDateTime.now();
    private ArrayList<Organization> org;
    private ArrayList<Category> cat;
    private ArrayList<Subcategory> sub;
    private ArrayList<Useful> usefuls;
    private ArrayList<Service> services;



//    public GovServlet() {
//    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocalDateTime now= LocalDateTime.now();
        if (this.services == null || now.isAfter(dataDate.plusHours(5))) {
            request.setAttribute("data", "data");
            this.org = Controller.getOrgData();
            this.cat = Controller.getCatData();
            this.sub = Controller.getGegData();
            this.usefuls = new ArrayList<>();
            this.services = Controller.getAPIServiceData(org, cat, sub, usefuls);
            this.usefuls.addAll(Controller.getAPIUsefulData(services));
            dataDate=now;
        }
        Controller controller = new Controller();
        CheckController chController = new CheckController();
        CrossCheck cross = new CrossCheck();
        File result = null;
        //ApiData
        if (request.getParameter("get_data") != null) {
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
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

        if (result != null) {
            // Set the content type and header of the response.
            request.setAttribute("url", result.getAbsolutePath());
            request.setAttribute("filename", result.getName());
        }
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        try {
            doGet(request, response);
        } catch (ServletException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
}
