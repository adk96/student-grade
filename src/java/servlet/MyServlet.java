package servlet;

import dao.DatabaseDAO;
import entity.Student;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.Integer.parseInt;

import java.util.List;

public class MyServlet extends HttpServlet {

    private DatabaseDAO dao;

    
    
    
    @Override
    public void init() throws ServletException {
        dao = new DatabaseDAO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("actionname");

        if (action.equals("RemoveStudent")) {
            int id = parseInt(request.getParameter("id"));
            boolean deleted = dao.deleteStudentFromDB(id);

            String message;
            if (deleted) {
                message = "Студент удален.";
            } else {
                message = "Студент не существует";
            }
            request.setAttribute("message", message);

            List<Student> students = dao.readAllStudentsFromDB();
            request.setAttribute("students", students);

            request.getRequestDispatcher("ShowAllStudent.jsp").forward(request, response);
        }

        if (action.equals("ShowAllStudent")) {
            List<Student> students = dao.readAllStudentsFromDB();
            request.setAttribute("students", students);
            request.getRequestDispatcher("ShowAllStudent.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }
}
