package com.diaz.tests;

import com.diaz.models.AbstractRobot;
import com.diaz.models.Facing;
import com.diaz.models.RobotImp;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RobotImpTest {

    private RobotImp robot1;
    private RobotImp robot2;

    @Test
    @Order(1)
    void getId() {
        AbstractRobot robot = new RobotImp(0, 0, Facing.EAST);
        assertEquals(3, robot.getId());
    }

    @BeforeEach
    void setUp() {
        robot1 = new RobotImp(1, 2, Facing.NORTH);
        robot2 = new RobotImp(1, 2, Facing.NORTH);
    }

    @Test
    void getCoordinates() {
        assertArrayEquals(new int[]{1, 2}, robot1.getCoordinates());
    }

    @Test
    void getOrientation() {
        assertEquals(Facing.NORTH, robot1.getOrientation());
    }

    @Test
    void getNextMove() {
        assertArrayEquals(new int[]{1, 3}, robot2.getNextMove());
        robot2.turnLeft();
        assertArrayEquals(new int[]{0, 2}, robot2.getNextMove());
        robot2.turnRigth();
        robot2.turnRigth();
        assertArrayEquals(new int[]{2, 2}, robot2.getNextMove());
    }

    @Test
    void move() {
        assertArrayEquals(new int[]{1, 2}, robot2.getCoordinates());
        assertEquals(Facing.NORTH, robot2.getOrientation());
        robot2.move();
        assertArrayEquals(new int[]{1, 3}, robot2.getCoordinates());
        assertEquals(Facing.NORTH, robot2.getOrientation());
    }

    @Test
    void turnLeft() {
        assertEquals(Facing.NORTH, robot2.getOrientation());
        robot2.turnLeft();
        assertEquals(Facing.WEST, robot2.getOrientation());
        robot2.turnLeft();
        assertEquals(Facing.SOUTH, robot2.getOrientation());
        robot2.turnLeft();
        assertEquals(Facing.EAST, robot2.getOrientation());
        robot2.turnLeft();
        assertEquals(Facing.NORTH, robot2.getOrientation());
    }

    @Test
    void turnRigth() {
        assertEquals(Facing.NORTH, robot2.getOrientation());
        robot2.turnRigth();
        assertEquals(Facing.EAST, robot2.getOrientation());
        robot2.turnRigth();
        assertEquals(Facing.SOUTH, robot2.getOrientation());
        robot2.turnRigth();
        assertEquals(Facing.WEST, robot2.getOrientation());
        robot2.turnRigth();
        assertEquals(Facing.NORTH, robot2.getOrientation());
    }

    @Test
    void report() {
        int id = robot1.getId();

        String expected = String.format("ID             Robot %d\nCoordinates    (1,2)\nFacing         NORTH", id);
        assertEquals(expected, robot1.report());

        robot1.turnRigth();
        expected = String.format("ID             Robot %d\nCoordinates    (1,2)\nFacing         EAST", id);
        assertEquals(expected, robot1.report());

        robot1.move();
        expected = String.format("ID             Robot %d\nCoordinates    (2,2)\nFacing         EAST", id);
        assertEquals(expected, robot1.report());

        robot1.turnLeft();
        robot1.turnLeft();
        expected = String.format("ID             Robot %d\nCoordinates    (2,2)\nFacing         WEST", id);
        assertEquals(expected, robot1.report());
    }

}