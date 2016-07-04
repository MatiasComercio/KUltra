public class factorial {
    public static void main(String[] args) {
        factorial(10);
    }

    private static void factorial(int number) {
        System.out.print("The factorial number is: ");
        System.out.print(factorialR(number));
        System.out.print("\n");
    }

    private static int factorialR(int n) {
        if (n <= 1) {
            return 1;
        }
        return n * factorialR(n - 1);
    }
}
