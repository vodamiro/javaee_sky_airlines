
package cz.cvut.fel.javaee.batch;

import cz.cvut.fel.javaee.db.DataHolder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.JobInstance;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author simo
 */
@WebServlet(urlPatterns = {"/ResOverview"})
public class ResOverviewServlet extends HttpServlet {
    
@Inject
    private DataHolder dataHolder;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        try {
            pw.println("<html>");
            pw.println("<head>");
            pw.println("<title>Sample Batch application demonstrating JobOperator API</title>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<h1>Sample Batch application demonstrating JobOperator API</h1>");

             submitJobFromXML("resOverview", "payed");
             displayReservations(pw);
            displayJobDetails(pw);

            pw.println("</body>");
            pw.println("</html>");
        } catch (Exception ex) {
            throw new ServletException(ex);
        } finally {
            pw.close();
        }
    }

    private void displayReservations(PrintWriter pw) {
        
        
    }

    private void displayJobDetails(PrintWriter pw) {
        pw.println("<table>");
        pw.println("<tr><td>Status of Submitted Jobs</td></tr>");
        pw.println("<table border=\"yes\">");
        pw.println("<tr><td>Job Name</td><td>ExecutionID</td>"
                + "<td>Batch Status</td><td>Exit Status</td>"
                + "<td>Start Time Status</td><td>End Time</td>"
                    + "</tr>");

        JobOperator jobOperator = BatchRuntime.getJobOperator();
        try {
            for (JobInstance jobInstance : jobOperator.getJobInstances("resOverview", 0, Integer.MAX_VALUE-1)) {
                for (JobExecution jobExecution : jobOperator.getJobExecutions(jobInstance)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("<tr>");
                    sb.append("<td>").append(jobExecution.getJobName()).append("</td>");
                    sb.append("<td>").append(jobExecution.getExecutionId()).append("</td>");
                    sb.append("<td>").append(jobExecution.getBatchStatus()).append("</td>");
                    sb.append("<td>").append(jobExecution.getExitStatus()).append("</td>");
                    sb.append("<td>").append(jobExecution.getStartTime()).append("</td>");
                    sb.append("<td>").append(jobExecution.getEndTime()).append("</td>");
                    pw.println(sb.toString());
                }
            }
        } catch (Exception ex) {

        }
        pw.println("</table>");
        pw.println("</table>");
    }

    private long submitJobFromXML(String jobName, String state)
            throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();

        Properties props = new Properties();
        props.setProperty("state", state);

        return jobOperator.start(jobName, props);
    }
    
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
