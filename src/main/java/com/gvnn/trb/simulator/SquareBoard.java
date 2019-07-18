package com.gvnn.trb.simulator;

import java.awt.Point;
import java.util.*;

public class SquareBoard implements Board {

    int rows;
    int columns;
    int [][] map;

    public SquareBoard(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        /*
        for (int i=0; i<rows-1; i++)
            for (int j=0; j<columns-1; j++)
                this.map[i][j] = 0;

         */

    }




    @Override
    public boolean isValidPosition(Position position) {
        return !(
                position.getX() > this.columns || position.getX() < 0 ||
                        position.getY() > this.rows || position.getY() < 0
        );
    }

    //initMap should be part of board. Not used for the time being
    /*
    public int[][] initMap(int[][] map, ArrayList <Position> Allpositions) {

        System.out.println("Rows " + rows + " Cols " + columns);

        for (int i=0; i<rows-1; i++)
            for (int j=0; j<columns-1; j++)
            {
                for (Position blockCoords : Allpositions){
                    if (blockCoords.getX() == i && blockCoords.getY() == j){
                        this.map[i][j] = 1;
                    }
                    else
                    {
                        this.map[i][j] = 0;
                    }

                }
            }

        return map;
    }


     */


}
