public class fibonacci {
    public static void main(String[] args) {
        fibonacci(30);
    }

    private static void fibonacci(int number) {
        System.out.print("The fibonacci number is: ");
        System.out.print(fibonacciR(number));
        System.out.print("\n");
    }

    private static int fibonacciR(int n) {
        if (n == 0 || n == 1){
            return n;
        }
        return fibonacciR(n - 1) + fibonacciR(n - 2);
    }
}
