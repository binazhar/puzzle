
package com.gvnn.trb.simulator;

import com.gvnn.trb.exception.ToyRobotException;
import java.util.*;
import java.awt.Point;

public class Obstruction {

    //private ArrayList<Position> position;

    ArrayList <Position> AllPositions = new ArrayList<>();

    public Obstruction() {
    }

    public Obstruction(Position position) {
        AllPositions.add(position);
    }

    public boolean setPosition(Position position) {
        if (position == null)
            return false;

        for(Position p: AllPositions) {
            if (p.x == position.x && p.y == position.y) {
                return true;
            }
        }
        AllPositions.add(position);
        return true;
    }

    //Placing an object clears all the
    public boolean clearPosition() {
        AllPositions.removeAll(AllPositions);
        return true;
    }




    public ArrayList <Position> getPosition() {
        return AllPositions;
    }





}


