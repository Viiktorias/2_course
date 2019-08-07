import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Controller {
    private Map<Integer, Map<Integer, List<Student>>> database;
    private DefaultTreeModel model;
    private View view;

    public Controller() {
        database = new HashMap<>(4, 1);
        try (Scanner scanner = new Scanner(new File("src\\students.txt"))) {
            scanner.useDelimiter("[|]|[\r\n]+");
            int course, group;
            String surname, name;
            List<Student> groupList;
            Map<Integer, List<Student>> groupsMap;
            while (scanner.hasNext()) {
                surname = scanner.next();
                name = scanner.next();
                course = scanner.nextInt();
                group = scanner.nextInt();
                if (database.containsKey(course)) {
                    if (database.get(course).containsKey(group)) {
                        groupList = database.get(course).get(group);
                        groupList.add(new Student(course, group, surname, name));
                    } else {
                        groupsMap = database.get(course);
                        groupList = new ArrayList<>();
                        groupList.add(new Student(course, group, surname, name));
                        groupsMap.put(group, groupList);
                    }
                } else {
                    groupsMap = new HashMap<>(12, 1);
                    groupList = new ArrayList<>();
                    groupList.add(new Student(course, group, surname, name));
                    groupsMap.put(group, groupList);
                    database.put(course, groupsMap);
                }
            }
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Студенты");
        DefaultMutableTreeNode courseNode, groupNode, studentNode;
        for (int course : database.keySet()) {
            courseNode = new DefaultMutableTreeNode(course + " курс");
            for (int group : database.get(course).keySet()) {
                groupNode = new DefaultMutableTreeNode(group + " группа");
                Collections.sort(database.get(course).get(group));
                for (Student student : database.get(course).get(group)) {
                    studentNode = new DefaultMutableTreeNode(student);
                    groupNode.add(studentNode);
                }
                courseNode.add(groupNode);
            }
            root.add(courseNode);
        }
        model = new DefaultTreeModel(root);
        view = new View(this, model);
    }

    public boolean add(DefaultMutableTreeNode node) {
        JTextField surnameField = new JTextField(15);
        JTextField nameField = new JTextField(15);
        surnameField.setText("Иванов");
        nameField.setText("Иван");
        JTextField courseField = new JTextField(1);
        JTextField groupField = new JTextField(2);
        JPanel inputPanel = createInput(null, false, surnameField, nameField,
                courseField, groupField);
        if (node != null) {
            while (!node.isLeaf())
                node = node.getFirstLeaf();
            Student student = (Student) node.getUserObject();
            courseField.setText(String.valueOf(student.getCourse()));
            groupField.setText(String.valueOf(student.getGroup()));
        }
        else {
            courseField.setText("1");
            groupField.setText("1");
        }
        if (JOptionPane.showConfirmDialog(view, inputPanel, "Добавить студента",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                new ImageIcon("src\\add.png")) == JOptionPane.OK_OPTION) {
            String surname, name;
            int course, group;
            try {
                surname = surnameField.getText();
                name = nameField.getText();
                course = Integer.parseInt(courseField.getText());
                group = Integer.parseInt(groupField.getText());
                if ((group > 12) || (group < 1))
                    throw new NumberFormatException();
                if ((course > 4) || (course < 1))
                    throw new NumberFormatException();
                addStudent(course, group, surname, name);
                return true;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Некорректый ввод", "Добавить студента",
                        JOptionPane.ERROR_MESSAGE, new ImageIcon("src\\add.png"));
            }
        }
        return false;
    }

    public boolean edit(DefaultMutableTreeNode node) {
        if ((node != null) && (node.isLeaf())) {
            Student student = (Student) node.getUserObject();
            JTextField surnameField = new JTextField(15);
            JTextField nameField = new JTextField(15);
            JTextField courseField = new JTextField(1);
            JTextField groupField = new JTextField(2);
            JPanel inputPanel = createInput(student, false, surnameField, nameField, courseField, groupField);
            if (JOptionPane.showConfirmDialog(view, inputPanel, "Редактировать студента",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                    new ImageIcon("src\\edit.png")) == JOptionPane.OK_OPTION) {
                String surname, name;
                int course, group;
                try {
                    surname = surnameField.getText();
                    name = nameField.getText();
                    course = Integer.parseInt(courseField.getText());
                    if ((course > 4) || (course < 1))
                        throw new NumberFormatException();
                    group = Integer.parseInt(groupField.getText());
                    if ((group > 12) || (group < 1))
                        throw new NumberFormatException();
                    addStudent(course, group, surname, name);
                    deleteStudent(student, node);
                    return true;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Некорректый ввод", "Редактировать студента",
                            JOptionPane.ERROR_MESSAGE, new ImageIcon("src\\edit.png"));
                }
            }
        } else {
            JOptionPane.showMessageDialog(view, "Выберите студента", "Редактировать студента",
                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src\\edit.png"));
        }
        return false;
    }

    public boolean delete(DefaultMutableTreeNode node) {
        if ((node != null) && (node.isLeaf())) {
            Student student = (Student) node.getUserObject();
            JPanel inputPanel = createInput(student, true, new JTextField(15),
                    new JTextField(15), new JTextField(1), new JTextField(12));
            if (JOptionPane.showConfirmDialog(view, inputPanel, "Удалить студента",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                    new ImageIcon("src\\delete.png")) == JOptionPane.OK_OPTION) {
                deleteStudent(student, node);
                return true;
            }
        } else {
            JOptionPane.showMessageDialog(view, "Выберите студента", "Удалить студента",
                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src\\delete.png"));
        }
        return false;
    }

    private JPanel createInput(Student student, Boolean disable, JTextField surnameField,
                               JTextField nameField, JTextField courseField,
                               JTextField groupField) {
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Surname"));
        inputPanel.add(surnameField);
        inputPanel.add(Box.createHorizontalStrut(5));
        inputPanel.add(new JLabel("Name"));
        inputPanel.add(nameField);
        inputPanel.add(Box.createHorizontalStrut(5));
        inputPanel.add(new JLabel("Course"));
        inputPanel.add(courseField);
        inputPanel.add(Box.createHorizontalStrut(5));
        inputPanel.add(new JLabel("Group"));
        inputPanel.add(groupField);
        if (student != null) {
            surnameField.setText(student.getSurname());
            nameField.setText(student.getName());
            courseField.setText(String.valueOf(student.getCourse()));
            groupField.setText(String.valueOf(student.getGroup()));
        }
        if (disable) {
            surnameField.setEditable(false);
            nameField.setEditable(false);
            courseField.setEditable(false);
            groupField.setEditable(false);
        }
        return inputPanel;
    }

    private void insert(DefaultMutableTreeNode parent, DefaultMutableTreeNode child, Iterator<TreeNode> iterator, int s) {
        DefaultMutableTreeNode temp;
        do {
            temp = (DefaultMutableTreeNode) iterator.next();
            if (Integer.parseInt(((String) temp.getUserObject()).replaceAll("\\D+", "")) > s)
                break;
        } while (iterator.hasNext());
        if (Integer.parseInt(((String) temp.getUserObject()).replaceAll("\\D+", "")) > s) {
            parent.insert(child, parent.getIndex(temp));
        } else {
            parent.add(child);
        }
    }

    private DefaultMutableTreeNode find(DefaultMutableTreeNode root, String s) {
        Iterator<TreeNode> iterator;
        DefaultMutableTreeNode courseNode;
        iterator = root.children().asIterator();
        do {
            courseNode = (DefaultMutableTreeNode) iterator.next();
            if (courseNode.getUserObject().equals(s))
                break;
        } while (iterator.hasNext());
        return courseNode;
    }

    private void addStudent(int course, int group, String surname, String name) {
        List<Student> groupList;
        Map<Integer, List<Student>> groupsMap;
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        DefaultMutableTreeNode courseNode, groupNode, studentNode, temp;
        Student student = new Student(course, group, surname, name);
        Iterator<TreeNode> iterator;
        String sCourse = course + " курс";
        String sGroup = group + " группа";
        if (database.containsKey(course)) {
            if (database.get(course).containsKey(group)) {
                groupList = database.get(course).get(group);
                courseNode = find(root, sCourse);
                groupNode = find(courseNode, sGroup);
                studentNode = new DefaultMutableTreeNode(student);
                iterator = groupNode.children().asIterator();
                do {
                    temp = (DefaultMutableTreeNode) iterator.next();
                    if (((Student) temp.getUserObject()).compareTo(student) > 0)
                        break;
                } while (iterator.hasNext());
                if (((Student) temp.getUserObject()).compareTo(student) > 0) {
                    groupNode.insert(studentNode, groupNode.getIndex(temp));
                    groupList.add(groupNode.getIndex(temp), student);
                } else {
                    groupNode.add(studentNode);
                    groupList.add(student);
                }
            } else {
                groupsMap = database.get(course);
                groupList = new ArrayList<>();
                groupList.add(student);
                groupsMap.put(group, groupList);
                courseNode = find(root, sCourse);
                groupNode = new DefaultMutableTreeNode(sGroup);
                studentNode = new DefaultMutableTreeNode(student);
                groupNode.add(studentNode);
                iterator = courseNode.children().asIterator();
                insert(courseNode, groupNode, iterator, group);
            }
        } else {
            groupsMap = new HashMap<>();
            groupList = new ArrayList<>();
            groupList.add(student);
            groupsMap.put(group, groupList);
            database.put(course, groupsMap);
            courseNode = new DefaultMutableTreeNode(sCourse);
            groupNode = new DefaultMutableTreeNode(sGroup);
            studentNode = new DefaultMutableTreeNode(student);
            groupNode.add(studentNode);
            courseNode.add(groupNode);
            iterator = root.children().asIterator();
            if (iterator.hasNext()) {
                insert(root, courseNode, iterator, course);
            } else {
                root.add(courseNode);
            }
        }
    }

    private void deleteStudent(Student student, DefaultMutableTreeNode studentNode) {
        database.get(student.getCourse()).get(student.getGroup()).remove(student);
        DefaultMutableTreeNode groupNode = (DefaultMutableTreeNode) studentNode.getParent();
        groupNode.remove(studentNode);
        if (groupNode.isLeaf()) {
            database.get(student.getCourse()).remove(student.getGroup());
            DefaultMutableTreeNode courseNode = (DefaultMutableTreeNode) groupNode.getParent();
            courseNode.remove(groupNode);
            if (courseNode.isLeaf()) {
                database.remove(student.getCourse());
                ((DefaultMutableTreeNode) model.getRoot()).remove(courseNode);
            }
        }
    }
}
