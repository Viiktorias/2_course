import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        try{
            CarsContainer<Car> container = new CarsContainer<>();
            CarsFileReader.read(container, "input1.txt");
            container.println();
            System.out.println("-------------------");

            Car car = new Car("BMW", "белый", "дизель", "кожа");
            System.out.print("Количество машин ");
            car.print();
            System.out.println(": " + container.count(car));
            CarsContainer<Car> sortedCar = container.sorted();
            sortedCar.println();
            System.out.println("-------------------");

            car = new Car("BMW", "чёрный", "бензин", "кожзам");
            sortedCar.binarySearch(car).print();
            System.out.println();
            System.out.println("-------------------");

            car = new Car("BMW", "чёрный", "дизель", "кожзам");
            sortedCar.binarySearch(car).print();
            System.out.println();
            System.out.println("-------------------");

            System.out.print("Максимум: ");
            sortedCar.max().print();
            System.out.println();
        }
        catch (FileNotFoundException | IncorrectInputDataException e)
        {
            System.err.println(e.getMessage());
        }
        System.out.println("---------------------------------------");
        try{
            CarsContainer<Bus> container = new CarsContainer<>();
            CarsFileReader.read(container, "input2.txt");
            container.println();
            System.out.println("-------------------");

            Bus bus = new Bus("МАЗ", "зелёный", "дизель", 4, 104);
            System.out.print("Количество автобусов ");
            bus.print();
            System.out.println(": " + container.count(bus));
            CarsContainer<Bus> sortedBus = container.sorted();
            sortedBus.println();
            System.out.println("-------------------");

            bus = new Bus("МАЗ", "зелёный", "дизель", 3, 77);
            sortedBus.binarySearch(bus).print();
            System.out.println();
            System.out.println("-------------------");

            bus = new Bus("МАЗ", "жёлтый", "дизель", 3, 77);
            sortedBus.binarySearch(bus).print();
            System.out.println();
            System.out.println("-------------------");

            System.out.print("Максимум: ");
            sortedBus.max().print();
        }
        catch (FileNotFoundException | IncorrectInputDataException e)
        {
            System.err.println(e.getMessage());
        }
    }
}
