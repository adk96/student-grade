package school.frames;

import org.jdatepicker.JDatePicker;
import school.Main;
import school.database.DatabaseDAO;
import school.database.SchoolClass;
import school.database.Student;
import school.database.Subject;

import javax.swing.*;
import java.time.LocalDate;

public class AddGradeFrame extends MyFrame {

    private static final DatabaseDAO DAO = DatabaseDAO.getInstance();
    private Subject[] subjectsArr;

    public AddGradeFrame(Main main) {
        super("Добавить оценку", main, 250, 250);
    }

    public void showFrame() {
        JLabel classLabel = new JLabel("Класс: ");
        classLabel.setBounds(10, 10, 160, 25);

        JComboBox<SchoolClass> classBox = createClassBox();
        if (classBox.getSelectedItem() != null) {
            int selectedClassIndex = main.getClassList().getSelectedIndex();
            classBox.setSelectedIndex(selectedClassIndex);
            classBox.setEnabled(false);
        }

        classBox.addItemListener(e -> {
            SchoolClass classBoxSelectedItem = (SchoolClass) classBox.getSelectedItem();
            subjectsArr = DAO.readSubjectsForClassFromDB(classBoxSelectedItem).toArray(new Subject[0]);
            DefaultComboBoxModel<Subject> subjectsModel = new DefaultComboBoxModel<>(subjectsArr);
            subjectBox.setModel(subjectsModel);

            Student[] studentsArr = classBoxSelectedItem.getStudentsAsArray();
            DefaultComboBoxModel<Student> studentsModel = new DefaultComboBoxModel<>(studentsArr);
            studentBox.setModel(studentsModel);

            updateTeachers();

            validate();
            repaint();
        });

        JLabel studentLabel = new JLabel("Ученик: ");
        studentLabel.setBounds(10, 45, 90, 25);
        SchoolClass classBoxSelectedItem = (SchoolClass) classBox.getSelectedItem();
        if (classBoxSelectedItem != null) {
            studentBox = new JComboBox<>(classBoxSelectedItem.getStudentsAsArray());
            int selectedStudentIndex = main.getStudentList().getSelectedIndex();
            studentBox.setSelectedIndex(selectedStudentIndex);
            studentBox.setEnabled(false);
        } else {
            studentBox = new JComboBox<>();
        }
        studentBox.setBounds(90, 45, 150, 25);

        subjectsArr = DAO.readSubjectsForClassFromDB(classBoxSelectedItem).toArray(new Subject[0]);
        subjectBox = new JComboBox<>(subjectsArr);
        subjectBox.setBounds(10, 80, 180, 25);
        subjectBox.addItemListener(e -> {
            updateTeachers();
        });
        Integer[] gradesArr = DAO.readGradeValuesFromDB().toArray(new Integer[0]);
        JComboBox<Integer> gradeBox = new JComboBox<>(gradesArr);
        gradeBox.setBounds(190, 80, 50, 25);

        Subject selectedSubject = (Subject) subjectBox.getSelectedItem();
        String[] teachersArr = DAO.readTeachersForSubjectFromDB(selectedSubject).toArray(new String[0]);
        teacherBox = new JComboBox<>(teachersArr);
        teacherBox.setBounds(10, 115, 230,25);

        JDatePicker datePicker = new JDatePicker();
        datePicker.setBounds(10, 150, 230,25);

        JButton cancel = new JButton("Отмена");
        cancel.setBounds(10, 185, 110, 25);
        cancel.addActionListener(actionEvent -> dispose());

        JButton done = new JButton("Готово");
        done.setBounds(130, 185, 110, 25);
        done.addActionListener(actionEvent -> {
            Student student = (Student) studentBox.getSelectedItem();
            if (student != null) {
                LocalDate date = LocalDate.of(
                        datePicker.getModel().getYear(),
                        datePicker.getModel().getMonth() + 1,
                        datePicker.getModel().getDay());

                String selectedTeacher = (String) teacherBox.getSelectedItem();
                student.addGrade((Subject) subjectBox.getSelectedItem(),
                        selectedTeacher, (Integer) gradeBox.getSelectedItem(), date);
                main.updateTable();
                dispose();
            }
        });

        add(classLabel);
        add(classBox);
        add(studentLabel);
        add(studentBox);
        add(subjectBox);
        add(gradeBox);
        add(teacherBox);
        add(datePicker);
        add(cancel);
        add(done);

        setVisible(true);
    }

    private void updateTeachers() {
        Subject selectedSubject = (Subject) subjectBox.getSelectedItem();
        String[] teachersArr = DAO.readTeachersForSubjectFromDB(selectedSubject).toArray(new String[0]);
        DefaultComboBoxModel<String> teachersModel = new DefaultComboBoxModel<>(teachersArr);
        teacherBox.setModel(teachersModel);
    }

}
