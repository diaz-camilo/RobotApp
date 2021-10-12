package com.diaz.models;

import com.diaz.exceptions.RobotException;

public interface Moveable {
    /**
     * Moves the robot n steps in the direction it is oriented
     *
     * @return
     */
    int[] move() throws RobotException;
}
