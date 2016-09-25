/**
 * Created by Alberto Toro on 24/09/16.
 */
package dev.toro.marsrover.entity;

public class Grid {

    /* The number of columns for the grid */
    private int col;
    /* The number of rows for the grid */
    private int row;
    /* This is the grid itself, based on boolean value to mark an occupied position */
    private boolean[][] grid;

    public Grid(final int col, final int row) {
        // Because we receive the upper-right position, we need to add one to get
        // the actual grid size
        this.col = col + 1;
        this.row = row + 1;
        grid = new boolean[this.col][this.row];
    }

    /**
     * This method returns if the new position is outside of the Grid
     *
     * @param col
     * @param row
     * @return
     */
    public boolean outSideGrid(final int col, final int row) {
        return (col >= this.col) || (col < 0) || (row >= this.row) || (row < 0);
    }

    /**
     * This method returns if the new position is occupied by another rover
     *
     * @param col
     * @param row
     * @return
     */
    public boolean positionOccupied(final int col, final int row) {
        return grid[col][row];
    }

    /**
     * This method updates the position for a rover
     *
     * @param oldCol
     * @param oldRow
     * @param newCol
     * @param newRow
     */
    public void updatePositionOnGrid(final int oldCol, final int oldRow,
                                     final int newCol, final int newRow) {
        grid[newCol][newRow] = true;
        grid[oldCol][oldRow] = false;
    }
}
