import java.util.List;

public class Main {

    public static void main(String[] args) {
        BinaryTree<Integer> intTree = new BinaryTree<>();
        Reader.readInt(intTree, "intIn.txt");
        System.out.println(intTree);
        List<Integer> list;
        list = intTree.roundRTL();
        System.out.println("Обход дерева «Правый-Корень-Левый»");
        System.out.println(list);
        list = intTree.roundLTR();
        System.out.println("Обход дерева «Левый-Корень-Правый»");
        System.out.println(list);
        list = intTree.roundTLR();
        System.out.println("Обход дерева «Корень-Левый-Правый»");
        System.out.println(list);
        list = intTree.roundLRT();
        System.out.println("Обход дерева «Левый-Правый-Корень»");
        System.out.println(list);
        Reader.delInt(intTree, "intDel.txt");
        System.out.println(intTree);
        System.out.println("---------------------------------------------------------");
        BinaryTree<Student> studentTree = new BinaryTree<>();
        Reader.readStud(studentTree, "studIn.txt");
        System.out.println("Обход дерева «Правый-Корень-Левый»");
        System.out.println(studentTree.roundRTL());
        System.out.print("Поиск студента ");
        Student student = new Student("Владимир", "Ульяницкий", 1, 1);
        System.out.print(student);
        System.out.print(": ");
        Student founded = studentTree.find(student);
        if (founded != null)
            System.out.println(founded);
        else System.out.println("Не найден");
        Reader.delStud(studentTree, "studDel.txt");
        System.out.print("Поиск студента ");
        student = new Student("Владислав", "Кизенков", 1, 1);
        System.out.print(student);
        System.out.print(": ");
        founded = studentTree.find(student);
        if (founded != null)
            System.out.println(founded);
        else System.out.println("Не найден");
    }
}
