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
    private ArrayList<Ministry> min;
    private ArrayList<Organization> org;
    private ArrayList<Category> cat;
    private ArrayList<Subcategory> sub;
    private ArrayList<Useful> usefuls;
    private ArrayList<Service> services;
    private Data data;
    private LocalDateTime dataTime= LocalDateTime.now();

    /**
     * Default constructor.
     */
    public GovServlet() {

    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        Controller controller = new Controller();
        CheckController chController = new CheckController();
        CrossCheck cross = new CrossCheck();
        LocalDateTime now = LocalDateTime.now();
        if (this.data == null || dataTime.plusHours(5).isBefore(now)) {
            this.min = Controller.getMinData();
            this.org = Controller.getOrgData();
            this.cat = Controller.getCatData();
            this.sub = Controller.getGegData();
            this.services = Controller.getAPIServiceData();
            this.usefuls = Controller.getAPIUsefulData();
            this.data = new Data(this.min, this.org, this.cat, this.sub, this.services, this.usefuls);
        }

        File result = null;
        //ApiData
        if (request.getParameter("get_data") != null) {
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
        if (request.getParameter("monthly_report") != null) {
            result = controller.ServiceWriter(this.data);
        }
        if (request.getParameter("check_links_services") != null) {
            result = chController.checkServices(this.data);
        }
        if (request.getParameter("check_links_usefuls") != null) {
            result = chController.checkUsefuls(this.data);
        }
        if (request.getParameter("check_Changes_Without") != null) {
            //crossCheck data
            result = cross.checkGov(this.data);
        }
        if (request.getParameter("check_Changes_With") != null) {
            //crossCheck data
            cross.updateJson(this.data);
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
        try {
            doGet(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
	}
}
