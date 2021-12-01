package com.diaz.models;

import com.diaz.exceptions.MapException;
import com.diaz.exceptions.RobotException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a rectangular map containing a list of robots. provide methods to add, move and rotate the robots,
 * as well as checking for a robots existence and generating a robot's report.
 */
public abstract class AbstractMap {
  protected List<AbstractRobot> robots;
  protected int xMin;
  protected int yMin;
  protected int xMax;
  protected int yMax;
  
  public AbstractMap(int xMin, int yMin, int xMax, int yMax) {
    this.robots = new ArrayList<>();
    this.xMin = xMin;
    this.yMin = yMin;
    this.xMax = xMax;
    this.yMax = yMax;
  }
  
  /**
   * Places a robot at coordinates {@code x} and {@code y} on the map and {@code facing} north, south, east or west.
   *
   * @param x      the x-axis coordinate
   * @param y      the y-axis coordinate
   * @param facing the orientation
   * @return the robot id
   * @throws MapException if coordinates fall outside the map or coordinates are in use by another robot
   */
  public int addRobot(int x, int y, Facing facing) throws MapException {
    int[] newRobotCoordinates = new int[]{x, y};
    if (newRobotCoordinates[0] > this.xMax || newRobotCoordinates[0] < this.xMin ||
            newRobotCoordinates[1] > this.yMax || newRobotCoordinates[1] < this.yMin)
      throw new MapException(String.format(
              "The robot coordinates are outside of the map. Map's min and max coordinates are (%d,%d) and (%d,%d)",
              this.xMin, this.yMin, this.xMax, this.yMax));
    for (AbstractRobot robot : this.robots) {
      if (Arrays.equals(newRobotCoordinates, robot.getCoordinates()))
        throw new MapException(String.format("Location (%d,%d) is in use", newRobotCoordinates[0], newRobotCoordinates[1]));
    }
    RobotImp newRobot = new RobotImp(x, y, facing);
    robots.add(newRobot);
    return newRobot.getId();
  }
  
  /**
   * get the robot object with the given {@code id}
   *
   * @param id the robot id
   * @return robot object with id == {@code id}
   * @throws RobotException if there is no robot with the given {@code id}
   */
  public AbstractRobot getRobotById(int id) throws RobotException {
    for (AbstractRobot robot : this.robots)
      if (robot.getId() == id)
        return robot;
    
    throw new RobotException(String.format("Robot with id: %d does not exist", id));
  }
  
  /**
   * Moves the robot once
   *
   * @param id the robot id
   * @return the robot's new coordinates
   * @throws RobotException if there is no robot with the given {@code id}
   * @throws MapException   if the robot's new coordinates would fall outside the map or on an occupied coordinates
   */
  public int[] moveRobot(int id) throws MapException, RobotException {
    AbstractRobot robot = getRobotById(id);
    
    List<int[]> occupiedCoordinates = new ArrayList<>();
    this.robots.forEach(x -> occupiedCoordinates.add(x.getCoordinates()));
    int[] nextMove = robot.getNextMove();
    
    if (nextMove[0] > this.xMax || nextMove[0] < this.xMin || nextMove[1] > this.yMax || nextMove[1] < this.yMin)
      throw new MapException(String.format(
              "The robot coordinates are outside of the map. Map's min and max coordinates are (%d,%d) and (%d,%d)",
              this.xMin, this.yMin, this.xMax, this.yMax));
    
    for (int[] coordinate : occupiedCoordinates) {
      if (Arrays.equals(coordinate, nextMove))
        throw new MapException(String.format("Coordinates (%d,%d) are occupied!", nextMove[0], nextMove[1]));
    }
    robot.move();
    return robot.getCoordinates();
  }
  
  /**
   * @param id the robot id
   * @return the robot's new orientation
   * @throws RobotException if there is no robot with the given {@code id}
   */
  public Facing robotTurnRight(int id) throws RobotException {
    AbstractRobot robot = getRobotById(id);
    robot.turnRight();
    return robot.getOrientation();
  }
  
  /**
   * @param id the robot id
   * @return the robot's new orientation
   * @throws RobotException if there is no robot with the given {@code id}
   */
  public Facing robotTurnLeft(int id) throws RobotException {
    AbstractRobot robot = getRobotById(id);
    robot.turnLeft();
    return robot.getOrientation();
  }
  
  /**
   * @param id the robot id
   * @return a formatted string with the robot's id, coordinates and orientation.
   * e.g.
   * ID             Robot 1
   * Coordinates    (1,1)
   * Facing         NORTH
   * @throws RobotException if there is no robot with the given {@code id}
   */
  public String getRobotReport(int id) throws RobotException {
    AbstractRobot robot = getRobotById(id);
    return robot.report();
  }
  
  /**
   * check if a robot with the given {@code id} exists
   *
   * @param id the robot id
   * @return true if there is a robot with the given {@code id}, otherwise, false
   */
  public boolean robotExist(int id) {
    try {
      getRobotById(id);
      return true;
    } catch (RobotException e) {
      return false;
    }
  }
}