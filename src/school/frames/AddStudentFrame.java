package school.frames;

import org.jdatepicker.JDatePicker;
import school.Main;
import school.database.SchoolClass;
import school.database.Student;

import javax.swing.*;
import java.time.LocalDate;

public class AddStudentFrame extends MyFrame {

    public AddStudentFrame(Main main) {
        super("Добавить ученика", main, 250, 400);
    }

    public void showFrame() {
        JTextField name = new JTextField();
        name.setBounds(10, 10, 230, 25);
        name.setText("Имя");

        JTextField surname = new JTextField();
        surname.setBounds(10, 45, 230, 25);
        surname.setText("Фамилия");

        JTextField patron = new JTextField();
        patron.setBounds(10, 80, 230, 25);
        patron.setText("Отчество");

        JLabel classLabel = new JLabel("Класс: ");
        classLabel.setBounds(10, 115, 150, 25);
        JComboBox classBox = new JComboBox<>(main.getClassesAsArray());
        classBox.setBounds(150, 115, 90, 25);

        JDatePicker bdPicker = new JDatePicker();
        bdPicker.setBounds(10, 150, 230, 25);

        JButton cancel = new JButton("Отмена");
        cancel.setBounds(10, 185, 110, 25);
        cancel.addActionListener(actionEvent -> dispose());

        JButton done = new JButton("Готово");
        done.setBounds(130, 185, 110, 25);
        done.addActionListener(actionEvent -> {
            SchoolClass schoolClass = (SchoolClass) classBox.getSelectedItem();
            if (surname.getText().length() > 0 && name.getText().length() > 0 && patron.getText().length() > 0 && schoolClass != null) {
                LocalDate date = LocalDate.of(
                        bdPicker.getModel().getYear(),
                        bdPicker.getModel().getMonth() + 1,
                        bdPicker.getModel().getDay());

                schoolClass.addStudent(new Student(name.getText(), surname.getText(), patron.getText(), date));

                JOptionPane.showMessageDialog(this,
                        "Ученик " + name.getText() + " " + surname.getText() + " добавлен в класс " + schoolClass.getName());

                main.updateStudentsList();
                dispose();
            }
        });

        add(name);
        add(surname);
        add(patron);
        add(classLabel);
        add(classBox);
        add(bdPicker);
        add(cancel);
        add(done);

        setVisible(true);
    }

}