/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dao.DatabaseDAO;
import entity.Student;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class AddStudentServlet extends HttpServlet {

    /**
     * Name of the directory where uploaded files will be saved, relative to the
     * web application directory.
     */
    private static final String SAVE_DIR = "images";

    private DatabaseDAO dao;

    @Override
    public void init() throws ServletException {
        dao = new DatabaseDAO();
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

        // creates the save directory if it does not exists
        String savePath = "C:\\Pictures";
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String fileName;

        for (Part part : request.getParts()) {
            String contentDisp = part.getHeader("content-disposition");
            String[] items = contentDisp.split(";");
            for (String item : items) {
                String ss = item.trim();
                if (ss.startsWith("filename")) {
                    try {
                        int id = dao.insertStudentIntoDB(name, surname);
                        fileName = String.valueOf(id);
                        String fileExt = ss.substring(ss.lastIndexOf("."), ss.length() - 1); // расширение включая точку
                        String fullPath = savePath + File.separator + fileName + fileExt;
                        part.write(fullPath);
                        dao.updateStudentImage(id, fullPath);
                        request.setAttribute("message", "Студент добавлен.");
                    } catch (IOException e) {
                        request.setAttribute("message", "Ошибка при добавлении студента!");
                    }
                }
            }
        }

        List<Student> students = dao.readAllStudentsFromDB();
        request.setAttribute("students", students);
        request.getRequestDispatcher("ShowAllStudent.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
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
        processRequest(request, response);
    }
}
