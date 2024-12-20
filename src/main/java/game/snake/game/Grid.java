package game.snake.game;

import game.snake.utility.Vector2;

public class Grid<A> {
    private A[][] grid;

    public Grid(int cells) {grid = (A[][])new Object[cells][cells];}

    /**Returns {@code true} if the cell is occupied and false if it is not
     * @param row the row to check
     * @param column the column to check
     * @return A boolean indicating if the given cell is occupied
     */
    public boolean isOccupied(int row, int column) {return grid[row][column] != null;}

    /**Gets an object on the grid if it exist
     * @param row the row to set the item on
     * @param column the column to set the item on
     * @return an object if one is at the location or null
     */
    public A getItem(int row, int column) {return grid[row][column];}

    /**Sets an item on the grid
     * @param item the item to set on the grid
     * @param row the row to set the item on
     * @param column the column to set the item on
     */
    public void setItem(A item, int row, int column) {grid[row][column] = item;}

    /**Adds an item to the grid
     * @param item the item to add to the grid
     * @param row the row to add to item on
     * @param column the column to add the item on
     */
    public boolean addItem(A item, int row, int column) {
        boolean occupied = isOccupied(row, column);

        if (!occupied) {grid[row][column] = item;}
        return occupied;
    }

    /**Removes all found instances of {@code item} from the grid
     * @param item the item to remove from the grid
     */
    public void removeItem(A item) {
        for (A[] row : grid) {
            for (int column=0; column<row.length; column++) {
                if (row[column] == item) {row[column] = null;}
            }
        }
    }
    /**Removes the item at the given row and column
     * @param row the row to remove an item from, if occupied
     * @param column the column to remove an item from, if occupied
     */
    public void removeItem(int row, int column) {
        if (isOccupied(row, column)) {grid[row][column] = null;}
    }

    public Vector2 getPosition(A object) {
        for (int row=0; row<grid.length; row++) {
            for (int column=0; column<grid[row].length; column++) {
                A other = grid[row][column];
                if ((other != null && (other instanceof Object && object instanceof Object)) ? other.equals(object) : grid[row][column] == object) {return new Vector2(column, row);}
            }
        }
        return null;
    }

    public int getSize() {return grid.length;}
}
