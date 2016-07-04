public class prime {
    public static void main(String[] args) {
        prime(53235189);
    }

    private static void prime(int number) {
        System.out.print("Enter a number: ");
        if (isPrime(number) != 0) {
            System.out.print("Is prime!\n");
        } else {
            System.out.print("Is not prime!\n");
        }
    }

    private static int isPrime(int n) {
        if (n <= 1) {
            return 0;
        } else {
            if (n <= 3) {
                return 1;
            } else {
                if (n % 2 == 0 || n % 3 == 0){
                    return 0;
                }
                int i;
                i = 5;
                while ( i*i < n ) {
                    if (n % i == 0 || n % (i + 2) == 0) {
                        return 0;
                    }
                    i = i + 6;
                }
                return 1;
            }
        }
    }
}
