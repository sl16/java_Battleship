package battleship;

class Board {
    private CellState[][] board;
    private int shipsPlaced = 0;

    String[] shipNames = {"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};
    int[] shipLengths = {5, 4, 3, 3, 2};
    Ship[] ships = new Ship[5];

    Board(int size) {
        this.board = new CellState[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = CellState.FOG;
            }
        }
    }

    void createShips() {
        while (shipsPlaced < ships.length) {
            System.out.printf("Enter the coordinates of the %s (%d cells): \n\n", shipNames[shipsPlaced], shipLengths[shipsPlaced]);
            String coordinatesStr = Main.scanner.nextLine();
            String[] coordinatesSplit = coordinatesStr.split(" ");

            int[] coord1 = Helper.translateCoords(coordinatesSplit[0]);
            int[] coord2 = Helper.translateCoords(coordinatesSplit[1]);
            int[][] coordPlacement = Helper.calculateCoords(coord1, coord2, shipLengths[shipsPlaced]);
            if (!isValidPlacement(coord1, coord2, coordPlacement))
                continue;

            try {
                ships[shipsPlaced] = new Ship(coord1, coord2, coordPlacement, shipNames[shipsPlaced], shipLengths[shipsPlaced]);
            } catch (IllegalArgumentException e) {
                System.out.println("Error! " + e.getMessage() + " Try again:");
                continue;
            }

            for (int[] coord : coordPlacement) {
                setBoardElem(coord[0], coord[1], CellState.SHIP);
            }
            printBoard();
            shipsPlaced++;
        }
    }

    public void setBoardElem(int x, int y, CellState changeTo) {
        board[x][y] = changeTo;
    }

    boolean isValidPlacement(int[] coords1, int[] coords2, int[][] coordPlacement) {
        // Check if ship is in a straight line
        if (coords1[0] != coords2[0] && coords1[1] != coords2[1]) {
            System.out.println("Error! The ship must be in a straight line.");
            return false;
        }
        // Check if coordinates are in bounds
        if (!isInBounds(coords1, board.length) || !isInBounds(coords2, board.length)) {
            System.out.println("Error! The coordinates must be inside the board.");
            return false;
        }
        // Check if coordinates touch another ship (up, down, left, right, middle)
        if (isPlacedTooClose(coordPlacement)) {
            System.out.println("Error! You placed it too close to another one.");
            return false;
        }
        return true;
    }

    boolean isPlacedTooClose(int[][] coordPlacement) {
        for (int[] coord : coordPlacement) {
            int x = coord[0];
            int y = coord[1];

            // Current cell
            if (board[x][y] == CellState.SHIP)
                return true;
            // Up
            if (x - 1 >= 0 && board[x - 1][y] == CellState.SHIP)
                return true;
            // Down
            if (x + 1 < board.length && board[x + 1][y] == CellState.SHIP)
                return true;
            // Left
            if (y - 1 >= 0 && board[x][y - 1] == CellState.SHIP)
                return true;
            // Right
            if (y + 1 < board[0].length && board[x][y + 1] == CellState.SHIP)
                return true;
        }
        return false;
    }

    boolean isInBounds(int[] coord, int size) {
        return (coord[0] >= 0 && coord[0] < size && coord[1] >= 0 && coord[1] < size);
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
                System.out.printf("%c ", board[i][j].getSymbol());
            }
            System.out.println();
        }
        System.out.println();
    }
}
