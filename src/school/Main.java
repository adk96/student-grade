package school;

import school.database.DatabaseDAO;
import school.database.Grade;
import school.database.SchoolClass;
import school.database.Student;
import school.frames.*;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class Main {

    private ArrayList<SchoolClass> classes;

    private JFrame mainFrame;
    private JList<SchoolClass> classList;
    private JList<Student> studentList;
    private JTable table;
    private JScrollPane classListPane;
    private JScrollPane studentListPane;
    private JScrollPane tablePane;

    public DatabaseDAO databaseDAO = DatabaseDAO.getInstance();

    public JList<SchoolClass> getClassList() {
        return classList;
    }

    public JList<Student> getStudentList() {
        return studentList;
    }

    public Container getFrame() {
        return mainFrame;
    }

    public SchoolClass[] getClassesAsArray() {
        classes = databaseDAO.readAllClassesFromDB();
        return classes.toArray(new SchoolClass[0]);
    }

    public void addClass(SchoolClass schoolClass) throws KeyAlreadyExistsException {
        for (SchoolClass c : classes) {
            if (c.getName().equals(schoolClass.getName()))
                throw new KeyAlreadyExistsException(schoolClass.getName());
        }
        databaseDAO.insertClassIntoDB(schoolClass);
        classList.setListData(getClassesAsArray());
    }

    public void deleteClass(SchoolClass schoolClass) {
        databaseDAO.deleteClassFromDB(schoolClass);
        classList.setListData(getClassesAsArray());
        updateStudentsList();
    }

    public void selectStudent(SchoolClass schoolClass, Student student) {
        try {
            classList.setSelectedIndex(classes.indexOf(schoolClass));
            updateStudentsList();
            studentList.setSelectedIndex(schoolClass.getStudents().indexOf(student));
            updateTable();
        } catch (Exception ignored) {
        }
    }

    public void updateStudentsList() {
        if (classList.getSelectedValue() != null) {
            studentList.setListData(classList.getSelectedValue().getStudentsAsArray());
            updateTable();
        }
    }

    public JTable createTable(Student student) {
        Grade[] grades = student.getGradesAsArray();
        int rowsCount = grades.length;
        String[] columns = {"ФИО", "Дата", "Предмет", "Оценки", "Учитель"};

        String[][] data = new String[rowsCount][columns.length + 1];

        int row = 0;
        for (int tRow = row; row < tRow + rowsCount; row++) {
            if (row == tRow)
                data[row][0] = student.getShortName();
            else
                data[row][0] = "";

            Grade g = grades[row - tRow];
            data[row][1] = g.getDate().toString();
            data[row][2] = g.getSubject().getName();
            data[row][3] = String.valueOf(g.getValue());
            data[row][4] = g.getTeacher();
            data[row][5] = String.valueOf(g.getId());
        }

        TableModel dataModel = new AbstractTableModel() {

            @Override
            public int getRowCount() {
                return grades.length;
            }

            @Override
            public int getColumnCount() {
                return columns.length;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return data[rowIndex][columnIndex];
            }

            @Override
            public String getColumnName(int column) {
                return columns[column];
            }
        };

        return new JTable(dataModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public void updateTable() {
        if (tablePane != null)
            mainFrame.remove(tablePane);

        Student selectedValue = studentList.getSelectedValue();
        if (selectedValue != null) {
            table = createTable(selectedValue);
            tablePane = new JScrollPane(table);
            tablePane.setBounds(300, 0, mainFrame.getWidth() - 300, mainFrame.getHeight() - 50);
            mainFrame.add(tablePane);
        } else {
            tablePane = null;
        }

        mainFrame.validate();
        mainFrame.repaint();
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu classMenu = new JMenu("Классы");
        JMenuItem createClassItem = new JMenuItem("Создать");
        createClassItem.addActionListener(actionEvent -> new CreateClassFrame(this));
        JMenuItem deleteClassItem = new JMenuItem("Удалить");
        deleteClassItem.addActionListener(actionEvent -> new DeleteClassFrame(this));
        classMenu.add(createClassItem);
        classMenu.add(deleteClassItem);

        JMenu studentMenu = new JMenu("Ученики");
        JMenuItem findStudentItem = new JMenuItem("Найти");
        findStudentItem.addActionListener(actionEvent -> new FindStudentFrame(this));
        JMenuItem addStudentItem = new JMenuItem("Добавить");
        addStudentItem.addActionListener(actionEvent -> new AddStudentFrame(this));
        JMenuItem showAllStudentItem = new JMenuItem("Вывести всех учеников");
        showAllStudentItem.addActionListener(actionEvent -> new AllStudentFrame(this));
        JMenuItem deleteStudentItem = new JMenuItem("Удалить");
        deleteStudentItem.addActionListener(actionEvent -> new DeleteStudentFrame(this));

        studentMenu.add(findStudentItem);
        studentMenu.add(addStudentItem);
        studentMenu.add(showAllStudentItem);
        studentMenu.add(deleteStudentItem);

        menuBar.add(classMenu);
        menuBar.add(studentMenu);

        return menuBar;
    }

    private void createMainFrame() {
        mainFrame = new JFrame("Журнал");
        mainFrame.setMinimumSize(new Dimension(960, 720));
        mainFrame.setSize(mainFrame.getMinimumSize());

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLayout(null);

        classList = new JList<>(getClassesAsArray());
        classList.setBorder(new TitledBorder("Классы"));
        classList.addListSelectionListener(e -> updateStudentsList());
        classListPane = new JScrollPane(classList);
        classListPane.setBounds(0, 0, 100, mainFrame.getHeight());

        studentList = new JList<>();
        studentList.setBorder(new TitledBorder("Ученики"));
        studentList.addListSelectionListener(e -> updateTable());
        studentListPane = new JScrollPane(studentList);
        studentListPane.setBounds(100, 0, 200, mainFrame.getHeight());

        JButton addGrade = new JButton("Добавить оценку");
        addGrade.setBounds(350, 600, 150, 25);
        addGrade.addActionListener(e -> {
            if (classList.getSelectedValue() != null && studentList.getSelectedValue() != null) {
                new AddGradeFrame(this);
            }
        });

        JButton deleteGrade = new JButton("Удалить оценку");
        deleteGrade.setBounds(700, 600, 150, 25);
        deleteGrade.addActionListener(e -> {
            if (table != null) {
                int tableSelectedRow = table.getSelectedRow();
                if (tableSelectedRow != -1) {
                    int id = Integer.parseInt((String) table.getModel().getValueAt(tableSelectedRow, 5));
                    databaseDAO.deleteGrade(id);
                    updateTable();
                }
            }
        });

        mainFrame.add(classListPane);
        mainFrame.add(studentListPane);
        mainFrame.setJMenuBar(createMenuBar());
        mainFrame.add(addGrade);
        mainFrame.add(deleteGrade);


        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                classListPane.setBounds(0, 0, 100, mainFrame.getHeight());
                studentListPane.setBounds(100, 0, 200, mainFrame.getHeight());
                if (tablePane != null)
                    tablePane.setBounds(300, 0, mainFrame.getWidth() - 300, mainFrame.getHeight() - 50);
            }
        });

        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Main myApp = new Main();
        SwingUtilities.invokeLater(myApp::createMainFrame);
    }
}