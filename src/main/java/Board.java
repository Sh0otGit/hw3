import java.util.Objects;

public class Board {

    private final int size;
    private final Place[][] boardMatrix;
    private Place[] winningRow;

    /** Create a new board of the default size. */
    public Board() {
        this.size = 10;
        this.boardMatrix = new Place[size][size];
        arrayFill();
    }

    /** Create a new board of the specified size. */
    public Board(int size) {
        this.size = size;
        this.boardMatrix = new Place[size][size];
        arrayFill();
    }

    /** Return the size of this board. */
    public int size() {
        return this.size;
    }

    /** Return the board. */
    public Place[][] boardMatrix(){return this.boardMatrix;}

    /** Removes all the stones placed on the board, effectively
     * resetting the board to its original state.
     */
    public void clear() {
        arrayFill();
    }

    /** Return a boolean value indicating whether all the places
     * on the board are occupied or not.
     */
    public boolean isFull() {
        for(int i = 0; i < this.size; i++) {
            for(int j = 0; j < this.size; j++) {
                if (this.boardMatrix[i][j].stoneOwner.name() == "N/A"){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Place a stone for the specified player at a specified
     * intersection (x, y) on the board.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     * @param player Player whose stone is to be placed
     */
    public void placeStone(int x, int y, Player player) {
        this.boardMatrix[x][y].stoneOwner = player;
    }

    /**
     * Return a boolean value indicating whether the specified
     * intersection (x, y) on the board is empty or not.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isEmpty(int x, int y) {
        if (this.boardMatrix[x][y].stoneOwner.name() == "N/A"){
            return true;
        }
        return false;
    }

    /**
     * Is the specified place on the board occupied?
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isOccupied(int x, int y) {
        if (this.boardMatrix[x][y].stoneOwner.name() == "N/A"){
            return false;
        }
        return true;
    }

    /**
     * Return a boolean value indicating whether the specified
     * intersection (x, y) on the board is occupied by the given
     * player or not.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isOccupiedBy(int x, int y, Player player) {
        if (this.boardMatrix[x][y].stoneOwner == player){
            return true;
        }
        return false;
    }

    /**
     * Return the player who occupies the specified intersection (x, y)
     * on the board. If the place is empty, this method returns null.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public Player playerAt(int x, int y) {
        return this.boardMatrix[x][y].stoneOwner;
    }

    /**
     * Return a boolean value indicating whether the given player
     * has a winning row on the board. A winning row is a consecutive
     * sequence of five or more stones placed by the same player in
     * a horizontal, vertical, or diagonal direction.
     */
    public boolean isWonBy(Player player) {
        for(int x = 0; x < this.size; x++) {
            for(int y = 0; y < this.size; y++) {
                if (isOccupiedBy(x, y, player)){
                    boolean[] inARow =
                        {
                                inaRow_UpDown(player, x, y),
                                inaRow_LeftRight(player, x, y),
                                inaRow_DiagLeft(player, x, y),
                                inaRow_DiagRight(player, x, y)
                        };
                    //System.out.println(x+", "+y+": "+inARow[0] + " " + inARow[1] + " " + inARow[2] + " " + inARow[3]);
                    boolean contains = false;
                    for (boolean b : inARow) {
                        if (b) {
                          contains = true;
                          break;
                      }
                    }
                    if(contains){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Iterate through this.boardMatrix and set every value to a "N/A"
     * valye to represent an empty intersection (x, y).
     */
    public void arrayFill(){
        for(int i = 0; i < this.size; i++) {
            for(int j = 0; j < this.size; j++) {
                this.boardMatrix[i][j] = new Place(i, j, new Player("N/A"));
            }
        }
    }

    /**
     * Recursively checks for stones placed in a row and returns the number of
     * stones in a row in all directions/
     *
     * @param current Player to check for stones in a row.
     * @param orientation The direction to check for stones in a row.
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    int inARow_Recursive(Player current, String orientation, int x, int y){
        if(Objects.equals(orientation, "down")){x--;}
        else if (Objects.equals(orientation, "up")){x++;}
        else if (Objects.equals(orientation, "left")){y--;}
        else if (Objects.equals(orientation, "right")){y++;}
        else if(Objects.equals(orientation, "upleft")){x--;y--;}
        else if (Objects.equals(orientation, "upright")){x--;y++;}
        else if (Objects.equals(orientation, "downleft")){x++;y--;}
        else if (Objects.equals(orientation, "downright")){x++;y++;}
        if (x < 0 || x >= this.size){
            return 0;
        }
        if(y < 0 || y >= this.size){
            return 0;
        }
        if (this.boardMatrix[x][y].stoneOwner != current){
            return 0;
        }
        return 1 + inARow_Recursive(current, orientation, x, y);
    }

    /**
     * Used in the inARow_Recursive() method to check for a winning
     * vertical row of stones.
     *
     * @param current Player to check for stones in a row.
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    boolean inaRow_UpDown(Player current, int x, int y){
        int check = 1 + inARow_Recursive(current, "down", x, y) + inARow_Recursive(current, "up", x, y);
        return (check>=5);
    }

    /**
     * Used in the inARow_Recursive() method to check for a winning
     * vertical row of stones.
     *
     * @param current Player to check for stones in a row.
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    boolean inaRow_LeftRight(Player current, int x, int y){
        int check = 1 + inARow_Recursive(current, "left", x, y) + inARow_Recursive(current, "right", x, y);
        return (check>=5);
    }

    /**
     * Used in the inARow_Recursive() method to check for a winning
     * vertical row of stones.
     *
     * @param current Player to check for stones in a row.
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    boolean inaRow_DiagLeft(Player current, int x, int y){
        int check = 1 + inARow_Recursive(current, "upleft", x, y) + inARow_Recursive(current, "downright", x, y);
        return (check>=5);
    }

    /**
     * Used in the inARow_Recursive() method to check for a winning
     * vertical row of stones.
     *
     * @param current Player to check for stones in a row.
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    boolean inaRow_DiagRight(Player current, int x, int y){
        int check = 1 + inARow_Recursive(current, "downleft", x, y) + inARow_Recursive(current, "upright", x, y);
        return (check>=5);
    }

    /**
     * An intersection on an Omok board identified by its 0-based column
     * index (x) and row index (y). The indices determine the position
     * of a place on the board, with (0, 0) denoting the top-left
     * corner and (n-1, n-1) denoting the bottom-right corner,
     * where n is the size of the board.
     */

    public static class Place {
        /** 0-based column index of this place. */
        public final int x;

        /** 0-based row index of this place. */
        public final int y;

        public Player stoneOwner;

        /** Create a new place of the given indices.
         *  @param x 0-based column (vertical) index
         * @param y 0-based row (horizontal) index
         * @param stoneOwner
         */
        public Place(int x, int y, Player stoneOwner) {
            this.x = x;
            this.y = y;
            this.stoneOwner = stoneOwner;
        }

        // other methods if needed ...
    }

}

