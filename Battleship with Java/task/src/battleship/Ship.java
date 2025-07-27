package battleship;

class Ship {
    private int length;
    private int[] coordStart;
    private int[] coordEnd;
    private String name;
    private int[][] coordPlacement;
    private int health;

    Ship(int[] coordStart, int[] coordEnd, int[][] coordPlacement, String name, int expectedLength) {
        this.coordStart = coordStart;
        this.coordEnd = coordEnd;
        this.name = name;
        if (coordStart[0] == coordEnd[0])
            this.length = Math.abs(coordStart[1] - coordEnd[1]) + 1;
        else
            this.length = Math.abs(coordStart[0] - coordEnd[0]) + 1;
        if (length != expectedLength)
            throw new IllegalArgumentException("Wrong length of the " + name + "!");
        this.health = this.length;
        this.coordPlacement = coordPlacement;
    }

    public void decreaseHealth() {
        health--;
    }

    public int getLength() {
        return (length);
    }

    public int[][] getCoords() {
        return (coordPlacement);
    }

    public int getHealth() {
        return health;
    }

}
