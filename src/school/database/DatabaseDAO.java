/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package school.database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DatabaseDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/school_db_newnew?useTimezone=true&serverTimezone=GMT";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    private static final String GET_ALL_CLASSES_QUERY =
            "SELECT id, name FROM class ORDER BY name;";

    private static final String GET_STUDENTS_IN_CLASS_QUERY =
            "SELECT * FROM student WHERE class_id = ?;";

    private static final String GET_STUDENT_GRADES_QUERY =
            "SELECT g.id, s.id subject_id, s.name, gv.values, g.grade_date, t.fullname\n" +
                    "FROM grade g\n" +
                    "JOIN subject s\n" +
                    "ON g.subject_id = s.id\n" +
                    "JOIN student st\n" +
                    "ON g.student_id = st.id\n" +
                    "JOIN grade_value gv\n" +
                    "ON g.grade_id = gv.id\n" +
                    "JOIN teacher t\n" +
                    "ON g.teacher_id = t.id\n" +
                    "WHERE st.id = ?\n" +
                    "ORDER BY grade_date;";

    private static final String INSERT_CLASS_QUERY = "INSERT INTO class (name) VALUES (?);";

    private static final String DELETE_CLASS_QUERY = "DELETE FROM class WHERE id = ?;";

    private static final String INSERT_STUDENT_QUERY =
            "INSERT INTO student\n" +
            "(class_id, name, surname, patron, birthday, phone)\n" +
            "VALUES(?, ?, ?, ?, ?, ?);";

    public static final String DELETE_STUDENT_QUERY = "DELETE FROM student WHERE id = ?;";

    private static final String GET_GRADE_VALUES_QUERY = "SELECT * FROM grade_value ORDER BY `values`;";

    private static final String GET_SUBJECTS_FOR_CLASS_QUERY =
            "SELECT *\n" +
            "FROM subject s\n" +
            "JOIN class_subject_connection csc\n" +
            "ON csc.subject_id = s.id\n" +
            "WHERE csc.class_id = ?\n" +
            "ORDER BY s.name;";

    private static final String INSERT_GRADE_QUERY =
            "INSERT INTO grade\n" +
            "(grade_id, subject_id, teacher_id, student_id, grade_date)\n" +
            "VALUES\n" +
            "((SELECT id FROM grade_value WHERE `values` = ?),\n" +
            " ?,\n" +
            " (SELECT id FROM teacher WHERE fullname = ?),\n" +
            " ?, ?);";

    private static final String GET_TEACHERS_FOR_SUBJECT_QUERY =
            "SELECT t.fullname\n" +
                    "FROM teacher t\n" +
                    "JOIN teacher_subject_connection tsc\n" +
                    "ON tsc.teacher_id = t.id\n" +
                    "WHERE tsc.subject_id = ?;";

    private static final String DELETE_GRADE = "DELETE FROM grade WHERE id = ?;";

    private Connection conn;

    private static final class Holder {
        static final DatabaseDAO INSTANCE = new DatabaseDAO();
    }

    public static DatabaseDAO getInstance() {
        return Holder.INSTANCE;
    }

    private DatabaseDAO() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<SchoolClass> readAllClassesFromDB() {
        ArrayList<SchoolClass> result = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(GET_ALL_CLASSES_QUERY)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                SchoolClass schoolClass = new SchoolClass(name);
                schoolClass.setId(id);
                result.add(schoolClass);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public ArrayList<Student> readStudentsInClassFromDB(int classID) {
        ArrayList<Student> result = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(GET_STUDENTS_IN_CLASS_QUERY)) {
            stmt.setInt(1, classID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("name");
                String lastName = rs.getString("surname");
                String patron = rs.getString("patron");
                LocalDate birthday = rs.getDate("birthday").toLocalDate();
                Student student = new Student(firstName, lastName, patron, birthday);
                student.setId(id);
                result.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public ArrayList<Grade> readGradesFromDB(int studentID) {
        ArrayList<Grade> result = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(GET_STUDENT_GRADES_QUERY)) {
            stmt.setInt(1, studentID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int subject_id = rs.getInt("subject_id");
                String subjectName = rs.getString("name");
                int value = rs.getInt("values");
                LocalDate date = rs.getDate("grade_date").toLocalDate();
                String teacher = rs.getString("fullname");
                Subject subject = new Subject(subject_id, subjectName);
                Grade grade = new Grade(subject, teacher, value, date);
                grade.setId(id);
                result.add(grade);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public void insertClassIntoDB(SchoolClass schoolClass) {
        try (PreparedStatement stmt =
                conn.prepareStatement(INSERT_CLASS_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, String.valueOf(schoolClass.getNumber()) + schoolClass.getLetter());

            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();

            if (keys.next()) {
                schoolClass.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteClassFromDB(SchoolClass schoolClass) {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_CLASS_QUERY)) {
            stmt.setInt(1, schoolClass.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertStudentIntoDB(Student student, SchoolClass schoolClass) {
        try (PreparedStatement stmt =
                     conn.prepareStatement(INSERT_STUDENT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, schoolClass.getId());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getSurname());
            stmt.setString(4, student.getPatron());
            stmt.setDate(5, Date.valueOf(student.getBirthDate()));
            stmt.setString(6, null);

            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();

            if (keys.next()) {
                student.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteStudentFromDB(Student student) {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_STUDENT_QUERY)) {
            stmt.setInt(1, student.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Integer> readGradeValuesFromDB() {
        List<Integer> result = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(GET_GRADE_VALUES_QUERY)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int value = rs.getInt("values");
                result.add(value);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public List<Subject> readSubjectsForClassFromDB(SchoolClass schoolClass) {
        List<Subject> result = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(GET_SUBJECTS_FOR_CLASS_QUERY)) {
            stmt.setInt(1, schoolClass.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String subjectName = rs.getString("name");
                Subject subject = new Subject(id, subjectName);
                result.add(subject);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public void insertGradeIntoDB(Student student, Grade grade) {
        try (PreparedStatement stmt =
                     conn.prepareStatement(INSERT_GRADE_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, grade.getValue());
            stmt.setInt(2, grade.getSubject().getId());
            stmt.setString(3, grade.getTeacher());
            stmt.setInt(4, student.getId());
            stmt.setDate(5, Date.valueOf(grade.getDate()));

            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();

            if (keys.next()) {
                grade.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> readTeachersForSubjectFromDB(Subject subject) {
        List<String> result = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(GET_TEACHERS_FOR_SUBJECT_QUERY)) {
            stmt.setInt(1, subject.getId());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String fullname = rs.getString("fullname");
                result.add(fullname);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public void deleteGrade(int id) {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_GRADE)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}