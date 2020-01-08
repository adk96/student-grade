package school.frames;

import school.Main;
import school.database.SchoolClass;
import school.database.Student;

import javax.swing.*;

public class DeleteStudentFrame extends MyFrame {

    public DeleteStudentFrame(Main main) {
        super("Удалить ученика", main, 250, 200);
    }

    public void showFrame() {
        JLabel classLabel = new JLabel("Класс: ");
        classLabel.setBounds(10, 10, 150, 25);
        JComboBox<SchoolClass> classBox = createClassBox();

        classBox.addItemListener(e -> {
            SchoolClass classBoxSelectedItem = (SchoolClass) classBox.getSelectedItem();

            Student[] studentsArr = classBoxSelectedItem.getStudentsAsArray();
            DefaultComboBoxModel<Student> studentsModel = new DefaultComboBoxModel<>(studentsArr);
            studentBox.setModel(studentsModel);

            validate();
            repaint();
        });

        JLabel studentLabel = new JLabel("Ученик: ");
        studentLabel.setBounds(10, 45, 90, 25);
        if (classBox.getSelectedItem() != null) {
            SchoolClass selectedClass = (SchoolClass) classBox.getSelectedItem();
            studentBox = new JComboBox<>(selectedClass.getStudentsAsArray());
        } else {
            studentBox = new JComboBox<>();
        }
        studentBox.setBounds(90, 45, 150, 25);

        JTextField reason = new JTextField("Причина удаления");
        reason.setBounds(10, 80, 230, 25);

        JButton cancel = new JButton("Отмена");
        cancel.setBounds(10, 115, 110, 25);
        cancel.addActionListener(actionEvent -> dispose());

        JButton done = new JButton("Удалить");
        done.setBounds(130, 115, 110, 25);
        done.addActionListener(actionEvent -> {
            SchoolClass schoolClass = (SchoolClass) classBox.getSelectedItem();
            schoolClass.deleteStudent((Student) studentBox.getSelectedItem());
            main.updateStudentsList();
            dispose();
        });

        add(classLabel);
        add(classBox);
        add(studentLabel);
        add(studentBox);
        add(reason);
        add(cancel);
        add(done);

        setVisible(true);
    }
}
