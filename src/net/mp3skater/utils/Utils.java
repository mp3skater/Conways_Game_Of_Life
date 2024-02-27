package net.mp3skater.utils;

import net.mp3skater.cells.Cell;

import java.util.ArrayList;

public class Utils {

    private static final char[][] init = {
            {'t', ' ', 'p'},
            {' ', 'p', 'p'},
            {' ', 'p', ' '}
    };

    public static void setPieces(ArrayList<Cell> cells) {
        System.out.println("Game initialized");

        for(int y = 0; y<3; y++) {
            for(int x = 0; x<3; x++) {
                if(init[y][x] != ' ')
                    cells.add(new Cell(x+5, y+1));
            }
        }
    }
}