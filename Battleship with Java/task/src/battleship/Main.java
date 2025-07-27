package battleship;

import java.util.Scanner;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Board board = new Board(10);
        board.printBoard();
        board.createShips();
        board.beginGame();

        scanner.close();
    }
}