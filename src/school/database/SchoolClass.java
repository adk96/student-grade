package school.database;

import school.Main;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SchoolClass {

    private int id;
    private int number;
    private char letter;
    private ArrayList<Student> students;
    private static final DatabaseDAO DAO = DatabaseDAO.getInstance();

    public SchoolClass(int number, char letter) {
        this.number = number;
        this.letter = letter;
    }

    public SchoolClass(String name) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(name);
        if (m.find()) {
            try {
                number = Integer.parseInt(m.group());
            } catch (NumberFormatException ignored) {
            }
        }

        p = Pattern.compile("\\D+");
        m = p.matcher(name);
        if (m.find()) {
            letter = m.group().charAt(0);
        }
    }

    public void addStudent(Student student) {
        DAO.insertStudentIntoDB(student, this);
    }

    public void deleteStudent(Student student) {
        DAO.deleteStudentFromDB(student);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return number + "-" + letter;
    }

    public int getNumber() {
        return number;
    }

    public char getLetter() {
        return letter;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public Student[] getStudentsAsArray() {
        students = DAO.readStudentsInClassFromDB(id);
        return students.toArray(new Student[0]);
    }

    @Override
    public String toString() {
        return getName();
    }
}
