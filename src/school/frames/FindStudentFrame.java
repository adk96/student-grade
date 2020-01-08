package school.frames;

import school.Main;
import school.database.SchoolClass;
import school.database.Student;

import javax.swing.*;

public class FindStudentFrame extends MyFrame {

    public FindStudentFrame(Main main) {
        super("Найти ученика", main, 250, 200);
    }

    public void showFrame() {
        JLabel classLabel = new JLabel("Класс: ");
        classLabel.setBounds(10, 10, 150, 25);
        JComboBox<SchoolClass> classBox = createClassBox();

        JLabel studentLabel = new JLabel("Ученик: ");
        studentLabel.setBounds(10, 45, 90, 25);
        if (classBox.getSelectedItem() != null) {
            SchoolClass selectedClass = (SchoolClass) classBox.getSelectedItem();
            studentBox = new JComboBox<>(selectedClass.getStudentsAsArray());
        } else {
            studentBox = new JComboBox<>();
        }
        studentBox.setBounds(90, 45, 150, 25);

        JButton cancel = new JButton("Отмена");
        cancel.setBounds(10, 115, 110, 25);
        cancel.addActionListener(actionEvent -> dispose());
        JButton done = new JButton("Найти");
        done.setBounds(130, 115, 110, 25);
        done.addActionListener(actionEvent -> {
            main.selectStudent((SchoolClass) classBox.getSelectedItem(), (Student) studentBox.getSelectedItem());
            dispose();
        });

        add(classLabel);
        add(classBox);
        add(studentLabel);
        add(studentBox);
        add(cancel);
        add(done);

        setVisible(true);
    }

}
