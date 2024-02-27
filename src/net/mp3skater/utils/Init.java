package net.mp3skater.utils;

import net.mp3skater.cells.Cell;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Init {

    public static void setPieces(ArrayList<Cell> cells) {
        // Adds the cells like in the file board.txt
        try {
            File board = new File("res/board.txt");
            Scanner sc = new Scanner(board);
            int y = 0;
            while (sc.hasNextLine()) {
                y++;
                String data = sc.nextLine();
                int x = 0;
                for(char c : data.toCharArray()) {
                    x++;
                    if(c != ' ')
                        cells.add(new Cell(x,y));
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to read board initialization file <board.txt>");
            e.printStackTrace();
        }
    }
}