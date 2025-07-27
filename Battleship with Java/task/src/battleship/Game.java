package battleship;

public class Game {
    Board player1;
    Board player2;
    static boolean gameEnded = false;

    Game(int boardSize){
        player1 = new Board(boardSize, "Player 1");
        player2 = new Board(boardSize, "Player 2");
    }

    void createShips() {
        player1.createShips();
        player2.createShips();
    }

    void playerTurn(Board player) {
        Board enemyPlayer;
        if (player == player1)
            enemyPlayer = player2;
        else
            enemyPlayer = player1;

        enemyPlayer.printBoard(Board.BoardViewMode.FOG);
        System.out.println("---------------------");
        player.printBoard();
        System.out.printf("\n%s, it's your turn:\n", player.getName());
        player.attack(enemyPlayer);

        System.out.println("\nPress Enter and pass the move to another player");
        Main.scanner.nextLine();
        Helper.clearScreen();
        System.out.println("...");
    }

    void gameLoop() {
        System.out.println("The game starts!");
        while (!gameEnded) {
            playerTurn(player1);
            playerTurn(player2);
        }
        System.out.println("You sank the last ship. You won. Congratulations!");
    }


}
