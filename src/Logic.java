package xogame;

import java.util.Random;
import java.util.Scanner;

public class Logic {
    static int SIZE_X = 3;
    static int SIZE_Y = 3;
    static int DOTS_TO_WIN = 3;

    static final char DOT_X = 'X';
    static final char DOT_O = 'O';
    static final char DOT_EMPTY = '.';

    static char[][] map;

    static boolean ended;
    static String winner;
    static int[] winnerLine;

    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();

    public static void go() {
        printMap();
        if (checkWinLines(DOT_X)) {
            System.out.println("You winner!!!");
            ended = true;
            winner = "Your win!";
            return;
        }
        if (isFull()) {
            System.out.println("Ќичь€ !");
            ended = true;
            winner = "Draw";
            winnerLine = new int[0];
            return;
        }

        aiTurn();

        printMap();
        if (checkWinLines(DOT_O)) {
            System.out.println("Computer winner!!!");
            ended = true;
            winner = "Computer win!";
            return;
        }
        if (isFull()) {
            System.out.println("Ќичь€ !");
            ended = true;
            winner = "Draw";
            winnerLine = new int[0];
            return;
        }
    }

    static void initMap() {
        map = new char[SIZE_Y][SIZE_X];
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    static void printMap() {
        System.out.print("  ");
        for (int i = 1; i <= SIZE_X; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < SIZE_Y; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < SIZE_X; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void humanTurn(int x, int y) {
        if (isCellValid(y, x)) {
            map[y][x] = DOT_X;
            go();
        }
    }

    static boolean isCellValid(int y, int x) {
        if (x < 0 || y < 0 || x >= SIZE_X || y >= SIZE_Y) {
            return false;
        }
        return map[y][x] == DOT_EMPTY;
    }

    static void aiTurn() {
        int x = -1;
        int y = -1;

//попытка победить самому
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                if (isCellValid(i, j)) {
                    map[i][j] = DOT_O;
                    if (checkWinLines(DOT_O)) {
                        y = i;
                        x = j;
                    }
                    map[i][j] = DOT_EMPTY;
                }
            }
        }
//                     попытка перекрыть победный ход игроку
        if (x == -1 || y == -1) {
            for (int i = 0; i < SIZE_Y; i++) {
                for (int j = 0; j < SIZE_X; j++) {
                    if (isCellValid(i, j)) {
                        map[i][j] = DOT_X;
                        if (checkWinLines(DOT_X)) {
                            y = i;
                            x = j;
                        }
                        map[i][j] = DOT_EMPTY;
                    }
                }
            }
        }

        if (x == -1 || y == -1) {
            do {
                x = random.nextInt(SIZE_X);
                y = random.nextInt(SIZE_Y);
            } while (!isCellValid(y, x));
        }

        map[y][x] = DOT_O;
    }

    static boolean isFull() {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    static boolean checkLine(int cy, int cx, int vy, int vx, char dot) {
        if (cx + vx * DOTS_TO_WIN > SIZE_X || cy + vy * DOTS_TO_WIN > SIZE_Y ||
                cx + vx * DOTS_TO_WIN + 1 < 0 ||
                cy + vy * DOTS_TO_WIN + 1 < 0) {
            return false;
        }

        for (int i = 0; i < DOTS_TO_WIN; i++) {
            if (map[cy + i * vy][cx + i * vx] != dot) {
                return false;
            }
        }

        winnerLine = new int[5];
        winnerLine[0] = cx;
        winnerLine[1] = cy;
        winnerLine[2] = vx;
        winnerLine[3] = vy;
        winnerLine[4] = DOTS_TO_WIN;

        return true;
    }

    static boolean checkWinLines(char dot) {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                if (checkLine(i, j, 0, 1, dot) || checkLine(i, j, 1, 0, dot) ||
                        checkLine(i, j, 1, 1, dot) || checkLine(i, j, -1, 1, dot)) {
                    return true;
                }
            }
        }
        return false;
    }
}
