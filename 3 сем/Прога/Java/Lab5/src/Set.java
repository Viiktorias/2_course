public class Set {
    public static void sumOfSet(String[] args) {
        if (args.length != 2) {
            System.err.println("Invalid number of arguments");
            System.exit(1);
        }
        try {
            double x = Double.parseDouble(args[0]);
            if (x >= 1 || x <= -1) {
                System.err.println("Invalid argument: " + x);
                System.exit(1);
            }
            double epsilon = Double.parseDouble(args[1]);
            if (epsilon >= 1 || epsilon <= 0) {
                System.err.println("Invalid argument: " + epsilon);
                System.exit(1);
            }
            double sum = 0.0;
            int k = 1;
            double member = 1;
            for (int i = 0; i < 3; i++) {
                member *= x;
            }
            while (Math.abs(member) >= epsilon) {
                sum += member;
                for (int i = 0; i < 6 * k + 3; i++) {
                    member *= x;
                }
                k++;
            }
            System.out.println(sum);
        } catch (NumberFormatException e) {
            System.err.println("Invalid arguments");
            System.exit(1);
        }
    }
}
