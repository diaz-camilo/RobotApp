package com.diaz.models;

import org.jetbrains.annotations.NotNull;

public abstract class AbstractRobot implements Moveable {

    private static int idConsecutive = 1;
    private final int id;
    private int x;
    private int y;
    private Facing orientation;

    public AbstractRobot(int x, int y, @NotNull Facing orientation) {
        this.id = idConsecutive++;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    public int getId() {
        return id;
    }

    public int[] getCoordinates() {
        return new int[]{x, y};
    }

    public Facing getOrientation() {
        return orientation;
    }

    public int[] getNextMove() {
        int[] nextMove = getCoordinates();
        switch (this.orientation) {
            case NORTH:
                nextMove[1]++;
                break;
            case EAST:
                nextMove[0]++;
                break;
            case SOUTH:
                nextMove[1]--;
                break;
            case WEST:
                nextMove[0]--;
                break;
        }
        return nextMove;
    }


    @Override
    public int[] move() {
        int[] newCoordinates = getNextMove();
        this.x = newCoordinates[0];
        this.y = newCoordinates[1];
        return getCoordinates();
    }


    /**
     * Turns robot to the anti-clockwise
     */
    public void turnLeft() {
        switch (this.orientation) {
            case NORTH:
                this.orientation = Facing.WEST;
                break;
            case EAST:
                this.orientation = Facing.NORTH;
                break;
            case SOUTH:
                this.orientation = Facing.EAST;
                break;
            case WEST:
                this.orientation = Facing.SOUTH;
                break;
        }

    }

    /**
     * Turns robot clockwise
     */
    public void turnRigth() {
        switch (this.orientation) {
            case NORTH:
                this.orientation = Facing.EAST;
                break;
            case EAST:
                this.orientation = Facing.SOUTH;
                break;
            case SOUTH:
                this.orientation = Facing.WEST;
                break;
            case WEST:
                this.orientation = Facing.NORTH;
                break;
        }
    }

    /**
     * Returns a string indicating the 'x' and 'y' coordinates of the robot along with its orientation
     *
     * @return
     */
    public String report() {
        return String.format("%-15sRobot %d\n%-15s(%d,%d)\n%-15s%s", "ID", this.id, "Coordinates", this.x, this.y, "Facing", this.orientation);
    }

    @Override
    public String toString() {
        return report();
    }
}
