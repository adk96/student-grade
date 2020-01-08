package school.database;

import school.Main;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Student {

    private int id;
    private String name, surname, patron;
    private LocalDate birthDate;
    private ArrayList<Grade> grades;
    private static final DatabaseDAO DAO = DatabaseDAO.getInstance();

    public Student(String name, String surname, String patron, LocalDate birthDate) {
        this.name = name;
        this.surname = surname;
        this.patron = patron;
        this.birthDate = birthDate;
    }

    public void addGrade(Grade grade) {
        DAO.insertGradeIntoDB(this, grade);
    }

    public void addGrade(Subject subject, String teacher, int value, LocalDate date) {
        addGrade(new Grade(subject, teacher, value, date));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatron() {
        return patron;
    }

    public String getShortName() {
        return surname + " " + name.charAt(0) + "." + (patron == null ? "" : " " + patron.charAt(0) + ".");
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Grade[] getGradesAsArray() {
        grades = DAO.readGradesFromDB(id);
        return grades.toArray(new Grade[0]);
    }

    @Override
    public String toString() {
        return getShortName();
    }
}
