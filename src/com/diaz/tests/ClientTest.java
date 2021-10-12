package com.diaz.tests;

import com.diaz.client.Client;
import com.diaz.models.Facing;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientTest {

    Client client;
    Scanner scanner;

    final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        outContent.reset();
        System.setOut(new PrintStream(outContent));
        client = new Client(scanner);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        client = null;
    }

    @Test
    @Order(1)
    void placeRobot() {

        client.placeRobot(1, 2, Facing.EAST);
        assertEquals("ROBOT 1 placed on the table at (1,2) facing EAST\n", outContent.toString());
        outContent.reset();

        client.placeRobot(1, 2, Facing.NORTH);
        assertEquals("Location (1,2) is in use\n", outContent.toString());
        outContent.reset();

        client.placeRobot(0, 0, Facing.NORTH);
        assertEquals("ROBOT 2 placed on the table at (0,0) facing NORTH\n", outContent.toString());
        outContent.reset();

        client.placeRobot(5, 5, Facing.NORTH);
        assertEquals("ROBOT 3 placed on the table at (5,5) facing NORTH\n", outContent.toString());
        outContent.reset();

        client.placeRobot(15, 15, Facing.NORTH);
        assertEquals("The robot coordinates are outside of the map. " +
                "Map's min and max coordinates are (0,0) and (5,5)\n", outContent.toString());
    }

    @Test
    @Order(7)
    void run() {
    }

    @Test
    @Order(2)
    void writeActiveRobotReport() {
        client.placeRobot(1, 2, Facing.EAST);
        client.placeRobot(0, 0, Facing.NORTH);

        outContent.reset();
        client.activateRobot(4);
        client.writeActiveRobotReport();
        assertEquals("ID             Robot 4\nCoordinates    (1,2)\nFacing         EAST\n", outContent.toString());

        outContent.reset();
        client.activateRobot(5);
        client.writeActiveRobotReport();
        assertEquals("ID             Robot 5\nCoordinates    (0,0)\nFacing         NORTH\n", outContent.toString());
    }

    @Test
    @Order(3)
    void turnActiveRobotLeft() {
        client.placeRobot(1, 2, Facing.EAST);
        outContent.reset();
        String expected = "ID             Robot %d\nCoordinates    (%d,%d)\nFacing         %s\n";

        client.activateRobot(6);

        client.turnActiveRobotLeft();
        client.writeActiveRobotReport();
        assertEquals(String.format(expected, 6, 1, 2, "NORTH"), outContent.toString());
        outContent.reset();

        client.turnActiveRobotLeft();
        client.writeActiveRobotReport();
        assertEquals(String.format(expected, 6, 1, 2, "WEST"), outContent.toString());
        outContent.reset();

        client.turnActiveRobotLeft();
        client.writeActiveRobotReport();
        assertEquals(String.format(expected, 6, 1, 2, "SOUTH"), outContent.toString());
        outContent.reset();

        client.turnActiveRobotLeft();
        client.writeActiveRobotReport();
        assertEquals(String.format(expected, 6, 1, 2, "EAST"), outContent.toString());
        outContent.reset();
    }

    @Test
    @Order(4)
    void turnActiveRobotRight() {
        client.placeRobot(1, 2, Facing.WEST);
        outContent.reset();
        String expected = "ID             Robot %d\nCoordinates    (%d,%d)\nFacing         %s\n";

        client.activateRobot(7);

        client.turnActiveRobotRight();
        client.writeActiveRobotReport();
        assertEquals(String.format(expected, 7, 1, 2, "NORTH"), outContent.toString());
        outContent.reset();

        client.turnActiveRobotRight();
        client.writeActiveRobotReport();
        assertEquals(String.format(expected, 7, 1, 2, "EAST"), outContent.toString());
        outContent.reset();

        client.turnActiveRobotRight();
        client.writeActiveRobotReport();
        assertEquals(String.format(expected, 7, 1, 2, "SOUTH"), outContent.toString());
        outContent.reset();

        client.turnActiveRobotRight();
        client.writeActiveRobotReport();
        assertEquals(String.format(expected, 7, 1, 2, "WEST"), outContent.toString());
        outContent.reset();
    }

    @Test
    @Order(5)
    void moveActiveRobot() {
        client.placeRobot(1, 2, Facing.WEST);
        outContent.reset();
        String expected = "ID             Robot %d\nCoordinates    (%d,%d)\nFacing         %s\n";

        client.activateRobot(8);

        client.moveActiveRobot();
        client.writeActiveRobotReport();
        assertEquals(String.format(expected, 8, 0, 2, "WEST"), outContent.toString());
        outContent.reset();

        client.moveActiveRobot();
        assertEquals("The robot coordinates are outside of the map. " +
                "Map's min and max coordinates are (0,0) and (5,5)\n", outContent.toString());
        outContent.reset();

        client.turnActiveRobotRight();
        client.moveActiveRobot();
        client.moveActiveRobot();
        client.writeActiveRobotReport();
        assertEquals(String.format(expected, 8, 0, 4, "NORTH"), outContent.toString());
        outContent.reset();
    }


    @Test
    @Order(6)
    void activateRobot() {
        String expected = "ID             Robot %d\nCoordinates    (%d,%d)\nFacing         %s\n";
        client.placeRobot(0, 0, Facing.WEST); //id= 9
        client.placeRobot(1, 2, Facing.NORTH); //id= 10
        client.placeRobot(3, 3, Facing.SOUTH); //id= 11
        client.placeRobot(5, 5, Facing.EAST); //id= 12
        outContent.reset();

        client.activateRobot(9);
        client.writeActiveRobotReport();
        assertEquals(String.format(expected, 9, 0, 0, "WEST"), outContent.toString());
        outContent.reset();

        client.activateRobot(10);
        client.writeActiveRobotReport();
        assertEquals(String.format(expected, 10, 1, 2, "NORTH"), outContent.toString());
        outContent.reset();

        client.activateRobot(11);
        client.writeActiveRobotReport();
        assertEquals(String.format(expected, 11, 3, 3, "SOUTH"), outContent.toString());
        outContent.reset();

        client.activateRobot(12);
        client.writeActiveRobotReport();
        assertEquals(String.format(expected, 12, 5, 5, "EAST"), outContent.toString());
        outContent.reset();
    }
}