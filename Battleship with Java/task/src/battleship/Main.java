package battleship;

import java.util.Scanner;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Board board = new Board(10);
        board.printBoard();
        board.placeShips();


    }
}

class Ship {
    private int length;
    private int[] xyStart;
    private int[] xyEnd;

    Ship(int[] xyStart, int[] xyEnd) {
        this.xyStart = xyStart;
        this.xyEnd = xyEnd;
        if (xyStart[0] == xyEnd[0])
            this.length = Math.abs(xyStart[1] - xyEnd[1]) + 1;
        else
            this.length = Math.abs(xyStart[0] - xyEnd[0]) + 1;

        System.out.printf("Length: %d\n", this.getLength());
        System.out.printf("Parts: %s\n", this.getPlacedCoords());
    }

    public int getLength() {
        return (length);
    }

    public String getPlacedCoords() {
        StringBuilder sb = new StringBuilder();
        // Add +1 to the array index so the board game numbers don't start from 0
        if (xyStart[0] == xyEnd[0]) {
            if (xyStart[1] > xyEnd[1]) {
                for (int i = xyStart[1]; i >= xyEnd[1]; i--) {
                    sb.append(String.format("%c%d ", xyStart[0] + 65, i + 1));
                }
            } else {
                for (int i = xyStart[1]; i <= xyEnd[1]; i++) {
                    sb.append(String.format("%c%d ", xyStart[0] + 65, i + 1));
                }
            }
        } else {
            if (xyStart[0] > xyEnd[0]) {
                for (int i = xyStart[0]; i >= xyEnd[0]; i--) {
                    sb.append(String.format("%c%d ", i + 65, xyStart[1] + 1));
                }
            } else {
                for (int i = xyStart[0]; i <= xyEnd[0]; i++) {
                    sb.append(String.format("%c%d ", i + 65, xyStart[1] + 1));
                }
            }
        }
        return (sb.toString());
    }
}

class Board {
    private char[][] board;
    private int shipsPlaced = 0;
    Ship[] ships = new Ship[5];

    Board(int size) {
        this.board = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = '~';
            }
        }
    }

    void placeShips() {
        while (shipsPlaced < ships.length) {
            System.out.println("Enter the coordinates of the ship: ");
            String coordinatesStr = Main.scanner.nextLine();
            String[] coordinatesSplit = coordinatesStr.split(" ");
            // TODO: Add validation before storing ship!
            int[] coords1 = Helper.translateCoords(coordinatesSplit[0]);
            int[] coords2 = Helper.translateCoords(coordinatesSplit[1]);
            if (!isValidPlacement(coords1, coords2))
                continue;
            ships[shipsPlaced] = new Ship(coords1, coords2);
            shipsPlaced++;
        }
    }

    boolean isValidPlacement(int[] coords1, int[] coords2) {
        boolean result = true;
        // Check if ship is in a straight line
        if (coords1[0] != coords2[0] && coords1[1] != coords2[1]) {
            result = false;
            System.out.println("Error!");
        }
        // Check if coordinates are in bounds
        if (!Helper.isInBounds(coords1, board.length) || !Helper.isInBounds(coords2, board.length)) {
            result = false;
            System.out.println("Error!");
        }
        return (result);
    }

    void printBoard() {
        StringBuilder sb = new StringBuilder();

        // First line
        sb.append("  ");
        for (int i = 1; i <= board.length; i++) {
            sb.append(i).append(" ");
        }
        System.out.println(sb.toString());

        // Rest of board
        for (int i = 0; i < board.length; i++) {
            System.out.printf("%c ", 65 + i);
            for (int j = 0; j < board.length; j++) {
                System.out.printf("%c ", board[i][j]);
            }
            System.out.print("\n");
        }
    }
}

class Helper {
    static int[] translateCoords(String coordsStr) {
        int[] coordinates = new int[2];
        // First character is the row (A-J)
        coordinates[0] = coordsStr.charAt(0) - 'A';
        // Remaining substring is the column (1-10), adjust for 0-indexing
        coordinates[1] = Integer.parseInt(coordsStr.substring(1)) - 1;
        return (coordinates);
    }

    static boolean isInBounds(int[] coord, int size) {
        return coord[0] >= 0 && coord[0] < size && coord[1] >= 0 && coord[1] < size;
    }

}