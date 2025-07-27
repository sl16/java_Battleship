package battleship;

class Board {
    private CellState[][] board;
    private String playerName;
    private int shipsPlaced = 0;
    private int shipsSunk = 0;

    String[] shipNames = {"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};
    int[] shipLengths = {5, 4, 3, 3, 2};
    Ship[] ships = new Ship[5];

    Board(int size, String playerName) {
        this.board = new CellState[size][size];
        this.playerName = playerName;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = CellState.FOG;
            }
        }
    }

    void createShips() {
        System.out.printf("\n%s, place your ships on the game board\n", playerName);
        printBoard();
        while (shipsPlaced < ships.length) {
            System.out.printf("\nEnter the coordinates of the %s (%d cells): \n\n", shipNames[shipsPlaced], shipLengths[shipsPlaced]);
            String coordinatesStr = Main.scanner.nextLine();
            if (coordinatesStr.isEmpty())
                continue;
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
        System.out.println("\nPress Enter and pass the move to another player");
        Main.scanner.nextLine();
        Helper.clearScreen();
        System.out.println("...");
    }

    void attack(Board target) {
//        System.out.println("Take a shot!");
        while (true) {
            String coordinatesStr = Main.scanner.nextLine();
            if (coordinatesStr.isEmpty())
                continue;
            int[] coord = Helper.translateCoords(coordinatesStr);
            if (!isInBounds(coord, board.length)) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                continue;
            }
            CellState result = shoot(coord, target);
            switch (result) {
                case HIT -> {
                    System.out.println("You hit a ship!");
                    registerShipHit(target, coord);
                    break;
                }
                case MISS -> {
                    System.out.println("You missed!");
                    break;
                }
                default -> System.out.println("You already shot here. Try again:");
            }
        }
    }

    void registerShipHit(Board target, int[] coord) {
        for (Ship ship: target.ships) {
            for (int[] shipCoords : ship.getCoords()) {
                if (coord[0] == shipCoords[0] && coord[1] == shipCoords[1]) {
                    ship.decreaseHealth();
                    if (ship.getHealth() == 0) {
                        System.out.println("You sank a ship!");
                        target.shipsSunk++;
                    }
                }
            }
        }
        if (target.shipsSunk == 5)
            Game.gameEnded = true;
    }

    public void setBoardElem(int x, int y, CellState changeTo) {
        board[x][y] = changeTo;
    }

    CellState shoot(int[] coord, Board target) {
        CellState cell = board[coord[0]][coord[1]];

        switch (cell) {
            case SHIP -> {
                target.setBoardElem(coord[0], coord[1], CellState.HIT);
                return CellState.HIT;
            }
            case FOG -> {
                target.setBoardElem(coord[0], coord[1], CellState.MISS);
                return CellState.MISS;
            }
            default -> {
                return cell;
            }
        }
    }

    boolean isValidPlacement(int[] coords1, int[] coords2, int[][] coordPlacement) {
        // Check if ship is in a straight line
        if (!isStraightLine(coords1, coords2)) {
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

    boolean isStraightLine(int[] coords1, int[] coords2) {
        if (coords1[0] != coords2[0] && coords1[1] != coords2[1]) {
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

    boolean isInBounds(int[] coord, int boardSize) {
        return (coord[0] >= 0 && coord[0] < boardSize && coord[1] >= 0 && coord[1] < boardSize);
    }

    void printBoard() {
        printBoard(BoardViewMode.REVEALED);
    }

    void printBoard(BoardViewMode mode) {
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
                CellState cell = board[i][j];
                if (mode == BoardViewMode.FOG && cell == CellState.SHIP) {
                    System.out.print("~ ");
                } else {
                    System.out.print(cell.getSymbol() + " ");
                }
            }
            System.out.println();
        }
    }

    enum BoardViewMode {
        REVEALED, FOG
    }

    public String getName() {
        return playerName;
    }
}
