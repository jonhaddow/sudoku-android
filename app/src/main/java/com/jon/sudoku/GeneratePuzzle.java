package com.jon.sudoku;

/**
 * Handles the different puzzle set ups
 */
public class GeneratePuzzle {

    private static final int[][] easyPuzzle = {
            {0,6,1,8,0,0,0,0,7},
            {0,8,9,2,0,5,0,4,0},
            {0,0,0,0,4,0,9,0,3},
            {2,0,0,1,6,0,3,0,0},
            {6,7,0,0,0,0,0,5,1},
            {0,0,4,0,2,3,0,0,8},
            {7,0,5,0,9,0,0,0,0},
            {0,9,0,4,0,2,7,3,0},
            {1,0,0,0,0,8,4,6,0}
    };
    private static final int[][] mediumPuzzle = {
            {0,5,0,3,6,0,0,0,0},
            {2,8,0,7,0,0,0,0,0},
            {0,0,0,0,0,8,0,9,0},
            {6,0,0,0,0,0,0,8,3},
            {0,0,4,0,0,0,2,0,0},
            {8,9,0,0,0,0,0,0,6},
            {0,7,0,5,0,0,0,0,0},
            {0,0,0,0,0,1,0,3,9},
            {0,0,0,0,4,3,0,6,0}
    };
    private static final int[][] hardPuzzle = {
            {0,7,0,0,0,0,0,9,0},
            {0,0,0,0,5,0,4,0,2},
            {0,0,0,0,0,0,0,3,0},
            {6,0,0,0,1,3,2,0,0},
            {0,0,9,0,8,0,0,0,0},
            {0,3,1,0,0,6,0,0,0},
            {4,6,0,0,0,0,0,0,1},
            {0,0,8,0,0,4,6,0,0},
            {0,0,0,0,3,5,0,0,0}
    };

    public static int[][] generatePuzzle(int difficulty) {
        switch (difficulty) {
            case 0:
                return easyPuzzle;

            case 1:
                return mediumPuzzle;

            case 2:
                return hardPuzzle;
        }
        return easyPuzzle;
    }
}
