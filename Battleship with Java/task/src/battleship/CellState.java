package battleship;

enum CellState{
    FOG('~'),
    SHIP('O'),
    HIT('X'),
    MISS('M');

    private final char symbol;

    CellState(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    //        @Override
    //        public String toString() {
    //            return Character.toString(symbol);
    //        }
}