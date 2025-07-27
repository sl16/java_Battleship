package battleship;

class Helper {
    static int[] translateCoords(String coordsStr) {
        int[] coordinates = new int[2];
        // First character is the row (A-J)
        coordinates[0] = coordsStr.charAt(0) - 'A';
        // Remaining substring is the column (1-10), adjust for 0-indexing
        coordinates[1] = Integer.parseInt(coordsStr.substring(1)) - 1;
        return (coordinates);
    }

    static int[][] calculateCoords(int[] coordStart, int[] coordEnd, int length) {
        int[][] coordPlacement = new int[length][2];

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
        return (coordPlacement);
    }

    static void clearScreen() {
        for (int i = 0; i < 20; i++)
            System.out.println();
    }

}