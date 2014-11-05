/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import ejb.MRSBeanRemote;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jon
 */
public class MSSServlet extends HttpServlet {

    @EJB
    private MRSBeanRemote mrsb;
    private String loggedInAs = "";
    private String matricNumber = "";
    private List list;

    public void init() {
        System.out.println("MSSServlet: init()");
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("MSSServlet: processRequest()");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            RequestDispatcher dispatcher;
            ServletContext servletContext = getServletContext();
            String page = request.getPathInfo();
            page = page.substring(1);
            System.out.println("[MSSServlet] startPage: " + page + ", logged in as: " + loggedInAs);
            // DONE
            if (page.equals("login")) {
                if (isLoggedIn()) {
                    request.setAttribute("studentInfo", getStudentInfo(matricNumber));
                    page = "profile";
                }
            } // DONE 
            else if (page.equals("loginUser")) {
                if (checkStudentLogin(request)) {
                    request.setAttribute("studentInfo", getStudentInfo(matricNumber));
                    page = "profile";
                } else {
                    request.setAttribute("message", "Incorrect matriculation number or password!");
                    page = "login";
                }
            } else if (page.equals("logout")) {
                loggedInAs = "";
                page = "index";
            } else if (!isLoggedIn()) {
                page = "login";
            } //DONE
            else if (page.equals("profile")) {
                if (isLoggedIn()) {
                    request.setAttribute("studentInfo", getStudentInfo(matricNumber));
                    page = "profile";
                } else {
                    page = "login";
                }
                page = "profile";
            } // DONE
            else if (page.equals("updateEmail")) {
                if (updateEmail(request)) {
                    System.out.println("MSSServlet: Successfully updated email");
                    request.setAttribute("studentInfo", getStudentInfo(matricNumber));
                    request.setAttribute("message", "Email changed successfully!");
                }
                if (isLoggedIn()) {
                    request.setAttribute("studentData", getStudentInfo(matricNumber));
                }
                page = "profile";
            } // DONE
            else if (page.equals("updatePassword")) {
                if (updatePassword(request)) {
                    System.out.println("MSSServlet: Successfully changed password");
                    request.setAttribute("studentInfo", getStudentInfo(matricNumber));
                    request.setAttribute("message", "Password changed successfully!");
                }
                if (isLoggedIn()) {
                    request.setAttribute("studentData", getStudentInfo(matricNumber));
                }
                page = "profile";
            } // DONE
            else if (page.equals("modules")) {
                System.out.println("MSS: modules page");
            } else if (page.equals("search")) {
                String keyword = request.getParameter("keyword");
                list = mrsb.moduleSearch(keyword);
                request.setAttribute("data", list);
                request.setAttribute("keyword", keyword);
                page = "modResults";
            } // DONE
            else if (page.equals("registerModule")) {
                registerStudentToModule(request);
                page = "modules";
            } //DONE
            else if (page.equals("tutorials")) {
                list = mrsb.getRegisteredModules(matricNumber);
                // System.out.println(list);
                request.setAttribute("data", list);
            } //DONE
            else if (page.equals("registerTutorial")) {
                registerStudentToTutorial(request);
                page = "modules";
            } //DONE
            else if (page.equals("timetable")) {
                request.setAttribute("data", dataForTimetable());
                page = "timetable";
            }
            else if (page.equals("appeals")) {
                getAppeals();
                request.setAttribute("data", list);
                page = "appeals";
            }
            dispatcher = servletContext.getNamedDispatcher(page);
            System.out.println("[MSSServlet] Dispatched: " + page + ", logged in as: " + loggedInAs);
            if (dispatcher == null) {
                dispatcher = servletContext.getNamedDispatcher("error");
            }
            dispatcher.forward(request, response);
        } catch (Exception ex) {
            log("Exception in MSSServlet.processRequest()");
            ex.printStackTrace();
            response.sendRedirect("/MSS/");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("MSSServlet: doGet()");
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("MSSServlet: doPost()");
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "MSS Servlet";
    }

    @Override
    public void destroy() {
        System.out.println("MSSServlet: destroy()");
    }

    private boolean checkStudentLogin(HttpServletRequest request) {
        System.out.println("MSS: checkStudentLogin()");
        String matricNum = request.getParameter("matricNum");
        String password = request.getParameter("password");
        Vector student = mrsb.getStudent(matricNum);
        //System.out.println(student);
        if (student != null) {
            if (mrsb.checkPassword((String) student.get(2), password)) {
                loggedInAs = (String) student.get(1);
                matricNumber = (String) student.get(0);
                return true;
            }
        }
        return false;
    }

    private boolean isLoggedIn() {
        System.out.println("MSSServlet: isLoggedIn() -- login check");
        if (loggedInAs.equals("")) {
            return false;
        }
        return true;
    }

    private ArrayList<String> getStudentInfo(String matricNum) {
        System.out.println("MSSServlet: getStudentInfo()");
        ArrayList<String> info = new ArrayList();
        Vector student = mrsb.getStudent(matricNum);
        info.add((String) student.get(0)); // matricNum
        info.add((String) student.get(1)); // name
        info.add((String) student.get(2)); // password
        info.add((String) student.get(3)); // email
        return info;
    }

    private boolean updatePassword(HttpServletRequest request) {
        System.out.println("MSS: updatePassword()");
        String password = "";
        String oldPwd = request.getParameter("oldPwd");
        String newPwd = request.getParameter("newPwd");
        String confirmNewPwd = request.getParameter("cfmPwd");
        Vector student = mrsb.getStudent(matricNumber);
        if (student.get(1).equals(loggedInAs)) {
            password = (String) student.get(2);
        }
        if (!mrsb.checkPassword(password, oldPwd) || !newPwd.equals(confirmNewPwd)) {
            return false;
        }
        return mrsb.changePassword(matricNumber, newPwd);
    }

    private boolean updateEmail(HttpServletRequest request) {
        System.out.println("MSS: updateEmail()");
        String email = request.getParameter("email");
        // String matricNum = request.getParameter("matricNum");
        Vector student = mrsb.getStudent(matricNumber);
        /* if (student.get(1).equals(loggedInAs)) {
         matricNum = (String) student.get(0);
         } */
        return mrsb.updateStudentEmail(matricNumber, email);
    }

    private void registerStudentToModule(HttpServletRequest request) {
        System.out.println("MSSServlet: registerStudentToModule()");
        String moduleCode = request.getParameter("modCode");
        String outcome = mrsb.webAssignMod(moduleCode, matricNumber);
        System.out.println("MSSServlet: " + outcome);
        request.setAttribute("message", outcome);
    }

    private void registerStudentToTutorial(HttpServletRequest request) {
        System.out.println("MSSServlet: registerStudentToTutorial()");
        String moduleCode = request.getParameter("modCode");
        String tutGrp = request.getParameter("tutGrp");
        System.out.println("MSSServlet: tutGrp = " + tutGrp);
        String outcome = mrsb.webAssignTutorial(moduleCode, tutGrp, matricNumber);
        System.out.println("MSSServlet: " + outcome);
        request.setAttribute("message", outcome);
    }

    private ArrayList<String> dataForTimetable() {
        List<Vector> lessons = mrsb.getStudentLessons(matricNumber);
        ArrayList<String> info = new ArrayList<String>();
        
        for (Vector lesson : lessons) {
            String title = (String) lesson.get(0);
            String time = (String) lesson.get(1);
            String[] timeSplit = time.split(" ");
            info.add(time + " " + title);
        }
        return info;
    }
    
    private void getAppeals() {
        System.out.println("MSSServlet: getAppeals()");
        list = mrsb.getAppeals(matricNumber);
    }
}
