package com.diaz.models;

import com.diaz.exceptions.RobotException;

public interface Moveable {
  
  /**
   * Moves the object 1 step in the direction it is oriented
   *
   * @return the new object's coordinate on the map
   * @throws RobotException if the move is illegal
   */
  int[] move() throws RobotException;
}