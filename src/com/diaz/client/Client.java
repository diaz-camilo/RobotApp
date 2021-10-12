package com.diaz.client;

import com.diaz.exceptions.MapException;
import com.diaz.exceptions.RobotException;
import com.diaz.models.Facing;
import com.diaz.models.MapImp;

import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {

    private final Scanner scanner;
    private MapImp table;
    private int active = 1;

    public Client() {
        this.scanner = new Scanner(System.in);
        try {
            this.table = new MapImp(0, 0, 5, 5);
        } catch (MapException e) {
            write(e.getMessage());
            return;
        }
        run();
    }

    public Client(Scanner scanner) {
        this.scanner = scanner;
        try {
            this.table = new MapImp(0, 0, 5, 5);
        } catch (MapException e) {
            write(e.getMessage());
        }
    }

    public void run() {
        String userInput;
        do {
            Pattern pattern;
            Matcher matcher;
            MatchResult matchResult;

            System.out.print("> ");
            userInput = this.scanner.nextLine().strip().toUpperCase();
            if (!userInput.matches("^EXIT$")) {
                if (userInput.matches("^ROBOT (\\d+)$")) {

                    pattern = Pattern.compile("^ROBOT (\\d+)$");
                    pattern.matcher(userInput)
                            .results()
                            .map(x -> x.group(1))
                            .forEach(x -> activateRobot(Integer.parseInt(x)));

                } else if (userInput.matches("^PLACE (-?\\d+),(-?\\d+),(EAST|WEST|NORTH|SOUTH)$")) {

                    pattern = Pattern.compile("PLACE (-?\\d+),(-?\\d+),(EAST|WEST|NORTH|SOUTH)");
                    matcher = pattern.matcher(userInput);
                    matcher.find();
                    matchResult = matcher.toMatchResult();

                    int x = Integer.parseInt(matchResult.group(1));
                    int y = Integer.parseInt(matchResult.group(2));
                    Facing facing = Facing.valueOf(matchResult.group(3));

                    placeRobot(x, y, facing);
                } else if (userInput.matches("^MOVE$"))
                    moveActiveRobot();
                else if (userInput.matches("^RIGHT$"))
                    turnActiveRobotRight();
                else if (userInput.matches("^LEFT$"))
                    turnActiveRobotLeft();
                else if (userInput.matches("^REPORT$"))
                    writeActiveRobotReport();
                else
                    write("Invalid command");
            }
        } while (!userInput.equalsIgnoreCase("exit"));
    }

    private void write(Object o) {
        System.out.println(o);
    }

    public void writeActiveRobotReport() {
        try {
            write(this.table.getRobotReport(this.active));
        } catch (RobotException e) {
            write(e.getMessage());
        }
    }

    public void turnActiveRobotLeft() {
        try {
            this.table.robotTurnLeft(this.active);
        } catch (RobotException e) {
            write(e.getMessage());
        }
    }

    public void turnActiveRobotRight() {
        try {
            this.table.robotTurnRight(this.active);
        } catch (RobotException e) {
            write(e.getMessage());
        }
    }

    public void moveActiveRobot() {
        try {
            this.table.moveRobot(this.active);
        } catch (RobotException | MapException e) {
            write(e.getMessage());
        }
    }

    public void placeRobot(int x, int y, Facing facing) {
        int id;
        try {
            id = this.table.addRobot(x, y, facing);
        } catch (MapException e) {
            write(e.getMessage());
            return;
        }
        this.active = this.active == -1 ? 1 : this.active;
        write(String.format("ROBOT %d placed on the table at (%d,%d) facing %s", id, x, y, facing.toString()));
    }

    public void activateRobot(int newActive) {
        if (this.table.robotExist(newActive))
            this.active = newActive;
        else
            write(String.format("Robot with id: ROBOT %d, does not exist", newActive));
    }
}
