package com.diaz.models;

import org.jetbrains.annotations.NotNull;

public abstract class AbstractRobot implements Moveable, Chargable {
    
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
    
    /**
     * gets the coordinates the robot would occupy if it was made to move. this would be to the current coordinates plus 1 in the direction the robot is facing.
     *
     * @return an int array where the first element [0] is the x coordinate and the second element [1] is the y coordinate.
     */
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
    
    /**
     * moves the robot one step in the direction it is facing.
     *
     * @return the new coordinates
     */
    @Override
    public int[] move() {
        int[] newCoordinates = getNextMove();
        this.x = newCoordinates[0];
        this.y = newCoordinates[1];
        return getCoordinates();
    }
    
    @Override
    public void charge(int x, int y) {
        
        this.x = x;
        this.y = y;
        
    }
    
    
    /**
     * Turns robot anti-clockwise
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
    public void turnRight() {
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
     * generates a formatted string containing the robot's essential information.
     *
     * @return a string indicating the robot's id, x and y coordinates and orientation
     */
    public String report() {
        return String.format("%-15sRobot %d\n%-15s(%d,%d)\n%-15s%s", "ID", this.id, "Coordinates", this.x, this.y, "Facing", this.orientation);
    }
    
    @Override
    public String toString() {
        return report();
    }
}