package com.diaz.models;

import com.diaz.exceptions.MapException;
import com.diaz.exceptions.RobotException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public AbstractRobot getRobotById(int id) throws RobotException {
        for (AbstractRobot robot : this.robots)
            if (robot.getId() == id)
                return robot;

        throw new RobotException(String.format("Robot with id: %d does not exist", id));
    }

    public int[] moveRobot(int id) throws RobotException, MapException {
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

    public Facing robotTurnRight(int id) throws RobotException {
        AbstractRobot robot = getRobotById(id);
        robot.turnRigth();
        return robot.getOrientation();
    }

    public Facing robotTurnLeft(int id) throws RobotException {
        AbstractRobot robot = getRobotById(id);
        robot.turnLeft();
        return robot.getOrientation();
    }

    public String getRobotReport(int id) throws RobotException {
        AbstractRobot robot = getRobotById(id);
        return robot.report();
    }

    public boolean robotExist(int id) {
        try {
            getRobotById(id);
            return true;
        } catch (RobotException e) {
            return false;
        }
    }
}
