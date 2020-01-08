package school.frames;

import school.Main;
import school.database.SchoolClass;

import javax.swing.*;

public class DeleteClassFrame extends MyFrame {

    public DeleteClassFrame(Main main) {
        super("Удалить класс", main, 250, 375);
    }

    public void showFrame() {
        JList<Object> list = new JList<>(main.getClassesAsArray());
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        listScroller.setBounds(0, 0, 250, 300);
        add(listScroller);

        JButton close = new JButton("Закрыть");
        close.setBounds(10, 310, 110, 25);
        close.addActionListener(actionEvent -> dispose());

        JButton delete = new JButton("Удалить");
        delete.setBounds(130, 310, 110, 25);
        delete.addActionListener(actionEvent -> {
            main.deleteClass((SchoolClass) list.getSelectedValue());
            JOptionPane.showMessageDialog(this, "Класс удален");
            dispose();
        });

        add(close);
        add(delete);

        setVisible(true);
    }

}
