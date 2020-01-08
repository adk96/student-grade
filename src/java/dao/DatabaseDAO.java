package dao;

import entity.Student;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DatabaseDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/school_db_newnew?useTimezone=true&serverTimezone=GMT";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    private static final String GET_ALL_STUDENTS_QUERY
            = "SELECT * FROM student;";

    private static final String INSERT_STUDENT_QUERY
            = "INSERT INTO student (name, surname) VALUES(?,?);";

    private static final String DELETE_STUDENT_QUERY = "DELETE FROM student WHERE id = ?;";

    private static final String UPDATE_STUDENT_QUERY = "UPDATE student SET image = ? WHERE id = ?;";

    private String GET_STUDENT_IMAGE_QUERY = "SELECT image FROM student WHERE id = ?;";

    private Connection conn;

    public DatabaseDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Student> readAllStudentsFromDB() {
        ArrayList<Student> result = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(GET_ALL_STUDENTS_QUERY)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("name");
                String lastName = rs.getString("surname");
                String patron = rs.getString("patron");
                Date date = rs.getDate("birthday");
                LocalDate birthday = null;
                if (date != null) {
                    birthday = date.toLocalDate();
                }
                Student student = new Student(firstName, lastName, patron, birthday);
                student.setId(id);
                student.setImage(rs.getString("image"));
                result.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public int insertStudentIntoDB(String name, String surname) {
        try (PreparedStatement stmt
                = conn.prepareStatement(INSERT_STUDENT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            stmt.setString(2, surname);

            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return -1;
    }

    public boolean deleteStudentFromDB(int id) {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_STUDENT_QUERY)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStudentImage(int id, String fullPath) {
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_STUDENT_QUERY)) {
            stmt.setString(1, fullPath);
            stmt.setInt(2, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String readStudentImagePath(int id) {
        try (PreparedStatement stmt = conn.prepareStatement(GET_STUDENT_IMAGE_QUERY)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("image");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        return null;
    }
}
