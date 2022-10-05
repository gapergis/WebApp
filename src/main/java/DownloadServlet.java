import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "DownloadServlet", value = "/DownloadServlet")
public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        // File name

        String filePath = request.getParameter("url");
        String fileName = request.getParameter("filename");
        System.out.println(filePath + ",\n" + fileName);
        // Set the content type and header of the response.
        response.setHeader("Content-Disposition",
                "attachment; filename=\""
                        + fileName + "\"");

        FileInputStream is = new FileInputStream(filePath);
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader buffReader = new BufferedReader(isr);
        // Loop through the document and write into the
        // output.
        int in;
        while ((in = buffReader.read()) != -1) {
            out.write(in);
        }
        // Close FileInputStream and PrintWriter object
        is.close();
        isr.close();
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            doGet(request, response);
        } catch (IOException | ServletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
