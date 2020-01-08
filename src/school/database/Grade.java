package school.database;

import java.time.LocalDate;

public class Grade {

    private int id;
    private Subject subject;
    private String teacher;
    private int value;
    private LocalDate date;

    public Grade(Subject subject, String teacher, int value, LocalDate date) {
        this.subject = subject;
        this.teacher = teacher;
        this.value = value;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public int getValue() {
        return value;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
