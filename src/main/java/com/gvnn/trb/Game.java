package com.gvnn.trb;

import com.gvnn.trb.exception.ToyRobotException;
import com.gvnn.trb.simulator.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.Point;

public class Game {

    Board squareBoard;
    ToyRobot robot;
    Obstruction block;
    int[][] map = new int[5][5];

    public Game(Board squareBoard, ToyRobot robot, Obstruction block) {
        this.squareBoard = squareBoard;
        this.robot = robot;
        this.block = block;
    }

    /**
     * Places the robot on the squareBoard  in position X,Y and facing NORTH, SOUTH, EAST or WEST
     *
     * @param position Robot position
     * @return true if placed successfully
     * @throws ToyRobotException
     */
    public boolean placeToyRobot(Position position) throws ToyRobotException {

        if (squareBoard == null)
            throw new ToyRobotException("Invalid squareBoard object");

        if (position == null)
            throw new ToyRobotException("Invalid position object");

        // validate the position
        if (!squareBoard.isValidPosition(position))
            return false;


        // if position is valid then assign values to fields
        robot.setPosition(position);
        block.clearPosition();

        //clear all obstructions

        return true;
    }

    /**
     * Places the obstruction on the squareBoard in position X,Y
     *
     * @param position Block position
     * @return true if placed successfully
     * @throws ToyRobotException
     */
    public boolean placeObstruction(Position position) throws ToyRobotException {

        if (squareBoard == null)
            throw new ToyRobotException("Invalid squareBoard object");

        if (position == null)
            throw new ToyRobotException("Invalid position object");

        // validate the position

        if (!squareBoard.isValidPosition(position))
            return false;

        // if position is valid then assign values to fields
        block.setPosition(position);
        return true;
    }

    /**
     * Explore squareBoard from position X,Y to destination position X,Y
     * Shortest Path Algorithm
     *
     * @param startposition Start position
     * @param destposition Destination position
     * @return true if explored successfully
     * @throws ToyRobotException
     */
    public String explore(Position startPosition, Position destPosition) throws ToyRobotException {


        if (squareBoard == null)
            throw new ToyRobotException("Invalid squareBoard object");

        if ( startPosition == null || destPosition == null )
            throw new ToyRobotException("Invalid position object");

        // validate the the destination position
        if (!squareBoard.isValidPosition(destPosition))
            return "false";

        // if position is valid then initialize the map

        Point startPoint = new Point (startPosition.getX(),startPosition.getY());
        Point endPoint = new Point (destPosition.getX(),destPosition.getY());

        map = initMap(map, block.getPosition());


        String route = findPath(map, startPoint, endPoint);

        //System.out.println("mAP BEING RESET HERE ");
		//Reset Map to avoid any carried over computation
        map = initMap(map, block.getPosition());

        //printMap(map);

        return route;
    }


    /**
     * Evals and executes a string command.
     *
     * @param inputString command string
     * @return string value of the executed command
     * @throws com.gvnn.trb.exception.ToyRobotException
     *
     */
    public String eval(String inputString) throws ToyRobotException {
        String[] args = inputString.split(" ");

        // validate command
        Command command;
        try {
            command = Command.valueOf(args[0]);
        } catch (IllegalArgumentException e) {
            throw new ToyRobotException("Invalid command");
        }
        if ((command == Command.PLACE || command == Command.BLOCK || command == Command.EXPLORER)
                && args.length < 2) {
            throw new ToyRobotException("The given command requires more parameters");
        }

        // validate PLACE params
        String[] params;
        int x = 0;
        int y = 0;
        //Direction commandDirection = null;
        if (command == Command.PLACE || command == Command.BLOCK || command == Command.EXPLORER) {
            params = args[1].split(",");
            try {
                x = Integer.parseInt(params[0]);
                y = Integer.parseInt(params[1]);
                //commandDirection = Direction.valueOf(params[2]);
            } catch (Exception e) {
                throw new ToyRobotException("Invalid command Parameters");
            }
        }

        String output;

        switch (command) {
            case PLACE:
                output = String.valueOf(placeToyRobot(new Position(x, y)));
                break;
            case BLOCK:
                output = String.valueOf(placeObstruction(new Position(x, y)));
                break;
            case REPORT:
                output = report();
                break;
            case EXPLORER:
                output = String.valueOf(explore(robot.getPosition(), new Position(x, y)));
                break;
            default:
                throw new ToyRobotException("Invalid command");
        }

        return output;
    }

    /**
     * Returns the X and Y of the robot
     */
    public String report() {

        String robotPosition = "";
        String blockPosition = "";

        if (robot.getPosition() == null)
            robotPosition = "(,)";
        else{
            robotPosition = "(" + robot.getPosition().getX() + "," + robot.getPosition().getY() + ")" ;
        }

        if (block.getPosition() == null)
            blockPosition = "(,)";
        else{

            for (Position blockCoords : block.getPosition()){


                String blockIndPosition = "(" + blockCoords.getX() + "," + blockCoords.getY()+ ")";
                blockPosition = blockPosition + blockIndPosition;
            }

        }

        return "E: " + robotPosition + " B:" + blockPosition;
    }

    //public Point[] findPath(int[][] map, Point position, Point destination) {
    public String findPath(int[][] map, Point position, Point destination) {

        LinkedList<Point> queue1 = new LinkedList<>();
        LinkedList<Point> queue2 = new LinkedList<>();

        queue1.add(position);
        //map[position.y][position.x] = -1;
        map[position.x][position.y] = -1;
        //map[0][2] = -1;

        int stepCount = 2;

        //System.out.println("sTARTING WITH MAP ");
        //printMap(map);

        //if (isBlocked(map, 0, 2)) {
        //    System.out.println("Coordinates 0 ,2 are blocked ");
//
        //}

        while (!queue1.isEmpty()) {
            if(queue1.size() >= map.length * map[0].length){
                throw new Error("Map overload");
            }



            for (Point point : queue1) {
                if (point.x == destination.x && point.y == destination.y) {

                    Point[] optimalPath = new Point[stepCount - 1];

                    computeSolution(map, point.x, point.y, stepCount - 1, optimalPath);
                    //squareBoard.resetMap(map);
                    String path="PATH: ";
                    java.util.Arrays.toString(optimalPath);
                    for (Point pathCoords : optimalPath){
                        String pathIndPosition = "(" + pathCoords.x + ","+ pathCoords.y + ")";
                        path = path + pathIndPosition;

                    }

                    //return optimalPath;
                    return path;
                }
                LinkedList<Point> finalQueue = queue2;
                int finalStepCount = stepCount;
                lookAround(map, point, (x, y) -> {

                    if (isBlocked(map, x, y)) {

                        return;
                    }


                    Point e = new Point(x, y);


                    finalQueue.add(e);
                    map[e.x][e.y] = -finalStepCount;
                });
            }

            //if (DEBUG) {
            //    squareBoard.printMap(map);
            //}

            queue1 = queue2;
            queue2 = new LinkedList<>();
            stepCount++;
        }
        //initMap(map);
        return null;
    }

    private boolean isBlocked(int[][] map, Point p) {
        return isBlocked(map, p.x, p.y);
    }

    private boolean isBlocked(int[][] map, int x, int y) {
        int i = map[x][y];


        //if(i <= 1)
        //    return true;
        //return false;

        return i < 0 || i == 1;
    }

    private void printMap(int[][] map) {
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0, mapLength = map.length; i < mapLength; i++) {
            int[] aMap = map[i];
            for (int x = 0; x < map[0].length; x++) {
                System.out.print(aMap[x] + "\t");
            }
            System.out.println();
        }
        System.out.println("****************************************");
    }

    public int[][] initMap(int[][] map, ArrayList<Position> Allpositions) {

        int rows = 5;
        int columns = 5;

        for (int i = 0; i < rows ; i++) {
            for (int j = 0; j < columns ; j++) {

                for (Position blockCoords : Allpositions) {
                    //System.out.println("********* "+ blockCoords.getX() +" ************************ "+ blockCoords.getY()+ " *******");
                    if (blockCoords.getX() == i && blockCoords.getY() == j) {
                        //System.out.println("********* bLOCKED  *******");

                        map[i][j] = 1;
                        //printMap(map);
                        break;						// Need to break here, otherwise wrong initilization

                    } else {
                        //System.out.println("********* uN-bLOCKED  *******");
                        map[i][j] = 0;
                    }

                }
                //printMap(map);
            }
        }

        //printMap(map);
        return map;

    }

    private void computeSolution(int[][] map, int x, int y, int stepCount, Point[] optimalPath) {
        if (isOutOfMap(map, x, y) || map[x][y] == 0) {
            return;
        }

        if ( -stepCount != map[x][y]) {
            return;
        }

        Point p = new Point(x, y);
        optimalPath[stepCount - 1] = p;
        lookAround(map, p, (x1, y1) -> computeSolution(map, x1, y1, stepCount - 1, optimalPath));
    }

    private void lookAround(int[][] map, Point p, Callback callback) {

        //Comment-out for not going diagonal
        //callback.look(map, p.x + 1, p.y + 1);
        //callback.look(map, p.x - 1, p.y + 1);
        //callback.look(map, p.x - 1, p.y - 1);
        //callback.look(map, p.x + 1, p.y - 1);
        callback.look(map, p.x + 1, p.y);
        callback.look(map, p.x - 1, p.y);
        callback.look(map, p.x, p.y + 1);
        callback.look(map, p.x, p.y - 1);
    }


    private static boolean isOutOfMap(int[][] map, Point p) {
        return isOutOfMap(map, p.x, p.y);
    }

    private static boolean isOutOfMap(int[][] map, int x, int y) {
        if (x < 0 || y < 0) {
            return true;
        }
        return map.length <= y || map[0].length <= x;
    }



    private interface Callback {
        default void look(int[][] map, int x, int y) {
            if (isOutOfMap(map, x, y)) {
                return;
            }
            onLook(x, y);
        }

        void onLook(int x, int y);
    }

}
