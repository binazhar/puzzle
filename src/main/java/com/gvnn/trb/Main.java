package com.gvnn.trb;

import com.gvnn.trb.exception.ToyRobotException;
import com.gvnn.trb.simulator.SquareBoard;
import com.gvnn.trb.simulator.ToyRobot;
import com.gvnn.trb.simulator.Obstruction;

import java.io.Console;

public class Main {

    public static void main(String[] args) {

        Console console = System.console();


        if (console == null) {
            System.err.println("No console.");
            System.exit(1);
        }

        SquareBoard squareBoard = new SquareBoard(5, 5);
        ToyRobot robot = new ToyRobot();
        Obstruction block = new Obstruction();
        Game game = new Game(squareBoard, robot, block);

        System.out.println("Toy Robot Simulator");
        System.out.println("Enter a command, Valid commands are:");
        System.out.println("\'PLACE X,Y\', \'BLOCK X,Y\', REPORT, \'EXPLORER X,Y\' or EXIT");

        boolean keepRunning = true;
        while (keepRunning) {
            String inputString = console.readLine(": ");
            if ("EXIT".equals(inputString)) {
                keepRunning = false;
            } else {
                try {
                    String outputVal = game.eval(inputString);
                    System.out.println(outputVal);
                } catch (ToyRobotException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}