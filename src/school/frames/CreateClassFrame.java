package school.frames;

import school.Main;
import school.database.SchoolClass;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.swing.*;

public class CreateClassFrame extends MyFrame {

    private static Integer[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    private static Character[] letters = {'А', 'Б', 'В', 'Г'};

    public CreateClassFrame(Main main) {
        super("Создать класс", main, 250, 150);
    }

    public void showFrame() {
        JLabel numLabel = new JLabel("Номер класса: ");
        numLabel.setBounds(10, 10, 150, 25);
        JComboBox<Integer> numsBox = new JComboBox<>(nums);
        numsBox.setBounds(150, 10, 90, 25);

        JLabel letterLabel = new JLabel("Буква класса: ");
        letterLabel.setBounds(10, 45, 150, 25);
        JComboBox<Character> lettersBox = new JComboBox<>(letters);
        lettersBox.setBounds(150, 45, 90, 25);

        JButton cancel = new JButton("Отмена");
        cancel.setBounds(10, 90, 110, 25);
        cancel.addActionListener(actionEvent -> dispose());

        JButton done = new JButton("Готово");
        done.setBounds(130, 90, 110, 25);
        done.addActionListener(actionEvent -> {
            try {
                if (numsBox.getSelectedItem() != null && lettersBox.getSelectedItem() != null) {
                    main.addClass(new SchoolClass((Integer) numsBox.getSelectedItem(), (Character) lettersBox.getSelectedItem()));
                    dispose();
                }
            } catch (KeyAlreadyExistsException e) {
                JOptionPane.showMessageDialog(this, "Класс " + e.getMessage() + " уже существует");
            }
        });

        add(numLabel);
        add(numsBox);
        add(letterLabel);
        add(lettersBox);
        add(cancel);
        add(done);

        setVisible(true);
    }

}