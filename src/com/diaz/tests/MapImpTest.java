package com.diaz.tests;

import com.diaz.exceptions.MapException;
import com.diaz.exceptions.RobotException;
import com.diaz.models.AbstractRobot;
import com.diaz.models.Facing;
import com.diaz.models.MapImp;
import com.diaz.models.RobotImp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapImpTest {

    private MapImp map;
    private AbstractRobot robot;
    private int id;

    @BeforeAll
    static void beforeAll() {
        new RobotImp(1, 2, Facing.SOUTH); // id=1
        new RobotImp(0, 0, Facing.SOUTH); // id=2
        new RobotImp(-3, -2, Facing.NORTH); //id=3
    }

    @BeforeEach
    void setUp() throws MapException, RobotException {
        map = new MapImp(-10, -10, 10, 10);
        id = map.addRobot(8, 0, Facing.EAST);
        robot = map.getRobotById(id);
    }

    @AfterEach
    void tearDown() {
        map = null;
        robot = null;
        id = -1;
    }

    @Test
    void getRobotById() throws RobotException, MapException {

        int id1 = map.addRobot(1, 8, Facing.NORTH);
        int id2 = map.addRobot(2, 7, Facing.NORTH);
        int id3 = map.addRobot(3, 6, Facing.NORTH);
        int id4 = map.addRobot(4, 5, Facing.NORTH);

        assertEquals(id1, map.getRobotById(id1).getId());
        assertEquals(id2, map.getRobotById(id2).getId());
        assertEquals(id3, map.getRobotById(id3).getId());
        assertEquals(id4, map.getRobotById(id4).getId());

        assertThrows(RobotException.class, () -> map.getRobotById(0));
        assertThrows(RobotException.class, () -> map.getRobotById(-1));
        assertThrows(RobotException.class, () -> map.getRobotById(1));
        assertThrows(RobotException.class, () -> map.getRobotById(2));
        assertThrows(RobotException.class, () -> map.getRobotById(3));
    }

    @Test
    void addRobot() {
        // Boundary testing
        assertDoesNotThrow(() -> map.addRobot(10, 10, Facing.NORTH));
        assertDoesNotThrow(() -> map.addRobot(-10, -10, Facing.NORTH));

        assertThrows(MapException.class, () -> map.addRobot(10, 11, Facing.NORTH));
        assertThrows(MapException.class, () -> map.addRobot(11, 10, Facing.NORTH));
        assertThrows(MapException.class, () -> map.addRobot(11, 11, Facing.NORTH));
        assertThrows(MapException.class, () -> map.addRobot(-10, -11, Facing.NORTH));
        assertThrows(MapException.class, () -> map.addRobot(-11, -10, Facing.NORTH));
        assertThrows(MapException.class, () -> map.addRobot(-11, -11, Facing.NORTH));

        assertDoesNotThrow(() -> map.addRobot(5, 0, Facing.NORTH));
        assertThrows(MapException.class, () -> map.addRobot(5, 0, Facing.WEST));
    }


    @Test
    void moveRobot() throws MapException, RobotException {

        map.addRobot(10, 1, Facing.EAST);

        map.moveRobot(id);
        assertArrayEquals(new int[]{9, 0}, robot.getCoordinates());
        map.moveRobot(id);
        assertArrayEquals(new int[]{10, 0}, robot.getCoordinates());
        assertThrows(MapException.class, () -> map.moveRobot(id));
        map.robotTurnLeft(id);
        assertThrows(MapException.class, () -> map.moveRobot(id));

        assertThrows(RobotException.class, () -> map.moveRobot(1));
        assertThrows(RobotException.class, () -> map.moveRobot(2));
        assertThrows(RobotException.class, () -> map.moveRobot(3));
    }

    @Test
    void robotTurnRight() throws RobotException {

        map.robotTurnRight(id);
        assertEquals(Facing.SOUTH, robot.getOrientation());
        map.robotTurnRight(id);
        assertEquals(Facing.WEST, robot.getOrientation());
        map.robotTurnRight(id);
        assertEquals(Facing.NORTH, robot.getOrientation());
        map.robotTurnRight(id);
        assertEquals(Facing.EAST, robot.getOrientation());

        assertThrows(RobotException.class, () -> map.robotTurnRight(1));
        assertThrows(RobotException.class, () -> map.robotTurnRight(2));
        assertThrows(RobotException.class, () -> map.robotTurnRight(3));
    }

    @Test
    void robotTurnLeft() throws RobotException {

        map.robotTurnLeft(id);
        assertEquals(Facing.NORTH, robot.getOrientation());
        map.robotTurnLeft(id);
        assertEquals(Facing.WEST, robot.getOrientation());
        map.robotTurnLeft(id);
        assertEquals(Facing.SOUTH, robot.getOrientation());
        map.robotTurnLeft(id);
        assertEquals(Facing.EAST, robot.getOrientation());

        assertThrows(RobotException.class, () -> map.robotTurnLeft(1));
        assertThrows(RobotException.class, () -> map.robotTurnLeft(2));
        assertThrows(RobotException.class, () -> map.robotTurnLeft(3));
    }

    @Test
    void getRobotReport() throws RobotException {

        String expected = String.format("ID             Robot %d\nCoordinates    (8,0)\nFacing         EAST", id);
        assertEquals(expected, map.getRobotReport(id));

        assertThrows(RobotException.class, () -> map.getRobotReport(1));
        assertThrows(RobotException.class, () -> map.getRobotReport(2));
        assertThrows(RobotException.class, () -> map.getRobotReport(3));
    }

    @Test
    void robotExist() {

        assertTrue(map.robotExist(id));
        assertFalse(map.robotExist(1));
        assertFalse(map.robotExist(2));
        assertFalse(map.robotExist(3));
    }
}