package com.diaz.models;

public interface Chargable {
  /**
   * moves the robot to the edge of the map in the direction it is facing, or won't move if it is already at the edge
   */
  void charge(int x, int y);
}