public class tictactoe {
    public static void main(String[] args) {
        tictac(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
    }

    private static void printRow(int n1, int n2, int n3) {
        if (n1 == -10) {
            System.out.print("-");
        } else {
            if (n1 == 10) {
                System.out.print("X");
            } else {
                System.out.print(n1);
            }
        }
        System.out.print("|");
        if (n2 == -10) {
            System.out.print("-");
        } else {
            if (n2 == 10) {
                System.out.print("X");
            } else {
                System.out.print(n2);
            }
        }
        System.out.print("|");
        if (n3 == -10) {
            System.out.print("-");
        } else {
            if (n3 == 10) {
                System.out.print("X");
            } else {
                System.out.print(n3);
            }
        }
        System.out.print("\n");
        return;
    }

    private static int evaluate(int board_1x1, int board_1x2, int board_1x3,
                                int board_2x1, int board_2x2, int board_2x3,
                                int board_3x1, int board_3x2, int board_3x3){
        if (board_1x1 == board_1x2 && board_1x2 == board_1x3){
            return board_1x1;
        }

        if (board_2x1 == board_2x2 && board_2x2 == board_2x3){
            return board_2x1;
        }

        if (board_3x1 == board_3x2 && board_3x2 == board_3x3){
            return board_3x1;
        }

        if (board_1x1 == board_2x1 && board_2x1 == board_3x1){
            return board_1x1;
        }

        if (board_1x2 == board_2x2 && board_2x2 == board_3x2){
            return board_1x2;
        }

        if (board_1x3 == board_2x3 && board_2x3 == board_3x3){
            return board_1x3;
        }

        if (board_1x1 == board_2x2 && board_2x2 == board_3x3){
            return board_1x1;
        }

        if (board_1x3 == board_2x2 && board_2x2 == board_3x1){
            return board_1x3;
        }

        if ((board_1x1 == 10 || board_1x1 == -10)
                &&
                (board_1x2 == 10 || board_1x2 == -10)
                &&
                (board_1x3 == 10 || board_1x3 == -10)
                &&
                (board_2x1 == 10 || board_2x1 == -10)
                &&
                (board_2x2 == 10 || board_2x2 == -10)
                &&
                (board_2x3 == 10 || board_2x3 == -10)
                &&
                (board_3x1 == 10 || board_3x1 == -10)
                &&
                (board_3x2 == 10 || board_3x2 == -10)
                &&
                (board_3x3 == 10 || board_3x3 == -10)
                ){
            return 1;
        }

        return 0;
    }

    private static void tictac(int[] ops) {
        int board_1x1;
        board_1x1 = 1;
        int board_1x2;
        board_1x2 = 2;
        int board_1x3;
        board_1x3 = 3;
        int board_2x1;
        board_2x1 = 4;
        int board_2x2;
        board_2x2 = 5;
        int board_2x3;
        board_2x3 = 6;
        int board_3x1;
        board_3x1 = 7;
        int board_3x2;
        board_3x2 = 8;
        int board_3x3;
        board_3x3 = 9;
        int player;
        player = 10;
        int position;
        int keep;
        keep = 1;
        int finished;
        finished = 0;
        int i = 0;
        while(i < ops.length) {
            printRow(board_1x1, board_1x2, board_1x3);
            printRow(board_2x1, board_2x2, board_2x3);
            printRow(board_3x1, board_3x2, board_3x3);

            if (finished != 0) {
                if (finished == 1) {
                    System.out.print ("**********************\nDRAW\n**********************\n");
                    return;
                } else {
                    if (finished == 10) {
                        System.out.print ("**********************\nPlayer 'X' WINS\n**********************\n");
                        return;
                    } else {
                        System.out.print ("**********************\nPlayer '-' WINS\n**********************\n");
                        return;
                    }
                }
            }

            if (player == 10) {
                System.out.print("Player 'X' plays\n");
            } else {
                System.out.print("Player '-' plays\n");
            }System.out.print("Enter position (from 1 to 9) or '10' to  end the game: ");
            position = ops[i++];
            System.out.print("\n");
            if (position == 10) {
                keep = 0;
            } else {
                keep = -1;
                if (position == 1) {
                    if (board_1x1 == 1) {
                        board_1x1 = player;
                        keep = 1;
                    }
                } else {
                    if (position == 2) {
                        if (board_1x2 == 2) {
                            board_1x2 = player;
                            keep = 1;
                        }
                    } else {
                        if (position == 3) {
                            if (board_1x3 == 3) {
                                board_1x3 = player;
                                keep = 1;
                            }
                        } else {
                            if (position == 4) {
                                if (board_2x1 == 4) {
                                    board_2x1 = player;
                                    keep = 1;
                                }
                            } else {
                                if (position == 5) {
                                    if (board_2x2 == 5) {
                                        board_2x2 = player;
                                        keep = 1;
                                    }
                                } else {
                                    if (position == 6) {
                                        if (board_2x3 == 6) {
                                            board_2x3 = player;
                                            keep = 1;
                                        }
                                    } else {
                                        if (position == 7) {
                                            if (board_3x1 == 7) {
                                                board_3x1 = player;
                                                keep = 1;
                                            }
                                        } else {
                                            if (position == 8) {
                                                if (board_3x2 == 8) {
                                                    board_3x2 = player;
                                                    keep = 1;
                                                }
                                            } else {
                                                if (position == 9) {
                                                    if (board_3x3 == 9) {
                                                        board_3x3 = player;
                                                        keep = 1;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (keep == 1) {
                    finished = evaluate(board_1x1, board_1x2, board_1x3,
                            board_2x1, board_2x2, board_2x3,
                            board_3x1, board_3x2, board_3x3);
                    player = -player;
                }
            }
        }
    }
}
