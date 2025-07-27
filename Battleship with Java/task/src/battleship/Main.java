package battleship;

import java.util.Scanner;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Game game = new Game(10);

        game.createShips();
        game.gameLoop();

        scanner.close();
    }
}