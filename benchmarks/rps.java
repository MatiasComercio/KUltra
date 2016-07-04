public class rps {
    public static void main(String[] args) {
        rps(new int[]{1, 2, 3, 1, 2, 3, 1, 2, 3});
    }

    private static int rand3(int old) {
        return (old + 1) % 3 + 1;
    }

    private static int result(int op1, int op2) {
        if (op1 == op2) {
            return 0;
        }
        if (op1 == 1) {
            if (op2 == 2) {
                return -1;
            } else {
                return 1;
            }
        }
        if (op1 == 2) {
            if (op2 == 3) {
                return -1;
            } else {
                return 1;
            }
        }
        if (op1 == 3) {
            if (op2 == 1) {
                return -1;
            } else {
                return 1;
            }
        }
        return 0;
    }

    private static void rps(int[] ops) {
        int op;
        int rand;
        rand = -1;
        int ret;
        int keep;
        keep = 1;
        int i = 0;
        while(i < ops.length) {
            System.out.print("~~ Options ~~");
            System.out.print("\n\t1. Rock");
            System.out.print("\n\t2. Paper");
            System.out.print("\n\t3. Scissors");
            System.out.print("\n\t0. }");
            System.out.print("\nChoose option: ");
            op = ops[i++];
            System.out.print("\n");
            if (op < 0 || op>3) {
                System.out.print("Invalid option!\n");
            } else {
                if (op == 0) {
                    keep = 0;
                } else {
                    System.out.print("*********************\n");
                    rand = rand3(rand);
                    ret = result(op, rand);
                    if (ret == 0) {
                        System.out.print("It's a draw!\n");
                    } else {
                        if (ret == 1) {
                            System.out.print("You won!\n");
                        } else{
                            System.out.print("You lost!\n");
                        }
                    }
                    System.out.print("*********************\n\n");
                }
            }
        }
    }
}
