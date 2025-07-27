package battleship;

class Ship {
    private int length;
    private int[] coordStart;
    private int[] coordEnd;
    private String name;
    private int[][] coordPlacement;

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

        this.coordPlacement = coordPlacement;

//        System.out.printf("Length: %d\n", this.getLength());
//        System.out.printf("Parts: %s\n", this.getPlacedCoords());
    }

    public int getLength() {
        return (length);
    }

    public int[][] getCoords() {
        return (coordPlacement);
    }

    @Deprecated
    private void placeShip() {
        coordPlacement = new int[length][2];

        // For vertical placement
        if (coordStart[0] == coordEnd[0]) {
            // Vertical
            int x = coordStart[0];
            int yStart = Math.min(coordStart[1], coordEnd[1]);
            int yEnd = Math.max(coordStart[1], coordEnd[1]);
            for (int i = 0; i < length; i++) {
                coordPlacement[i][0] = x;
                coordPlacement[i][1] = yStart + i;
            }
            // For horizontal placement
        } else if (coordStart[1] == coordEnd[1]) {
            int y = coordStart[1];
            int xStart = Math.min(coordStart[0], coordEnd[0]);
            int xEnd = Math.max(coordStart[0], coordEnd[0]);
            for (int i = 0; i < length; i++) {
                coordPlacement[i][0] = xStart + i;
                coordPlacement[i][1] = y;
            }
        }
    }

    @Deprecated
    public String getPlacedCoords() {
        StringBuilder sb = new StringBuilder();
        // Add +1 to the array index so the board game numbers don't start from 0
        if (coordStart[0] == coordEnd[0]) {
            if (coordStart[1] > coordEnd[1]) {
                for (int i = coordStart[1]; i >= coordEnd[1]; i--) {
                    sb.append(String.format("%c%d ", coordStart[0] + 65, i + 1));
                }
            } else {
                for (int i = coordStart[1]; i <= coordEnd[1]; i++) {
                    sb.append(String.format("%c%d ", coordStart[0] + 65, i + 1));
                }
            }
        } else {
            if (coordStart[0] > coordEnd[0]) {
                for (int i = coordStart[0]; i >= coordEnd[0]; i--) {
                    sb.append(String.format("%c%d ", i + 65, coordStart[1] + 1));
                }
            } else {
                for (int i = coordStart[0]; i <= coordEnd[0]; i++) {
                    sb.append(String.format("%c%d ", i + 65, coordStart[1] + 1));
                }
            }
        }
        return (sb.toString());
    }
}
