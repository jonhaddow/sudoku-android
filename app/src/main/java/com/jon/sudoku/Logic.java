package com.jon.sudoku;

import android.widget.Button;

/**
 * This class handles the logic of the sudoku game including
 * checking for invalid moves and checking
 */
public class Logic {

    /**
     * This method checks that the number entered is a legal move
     * @param gridSquares
     * @return It returns a boolean array indicating the buttons which cause the illegal move
     */
    public static boolean[][] isLegalMove(Button[][] gridSquares) {

        // extract text from each button
        String[][] gridText = getText(gridSquares);

        // Check each column, row and grid for repeating digits
        // if one is found then set that element in the array to false
        boolean[][] columnLegalMoves = checkColumn(gridText);
        boolean[][] rowLegalMoves = checkRow(gridText);
        boolean[][] gridLegalMoves = checkGrids(gridText);

        // holds an array of all invalid positions
        boolean[][] legalMoves = new boolean[9][9];

        // In each square, check which are legal moves
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (columnLegalMoves[i][j] && rowLegalMoves[i][j] && gridLegalMoves[i][j]) {
                    legalMoves[i][j] = true;
                } else {
                    legalMoves[i][j] = false;
                }
            }
        }
        return legalMoves;
    }

    /**
     * Extracts text from each button on sudoku grid
     * @param gridSquares
     * @return array of button texts
     */
    private static String[][] getText(Button[][] gridSquares) {
        String[][] strings = new String[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                strings[i][j] = gridSquares[i][j].getText()+"";
            }
        }
        return strings;
    }

    /**
     * Checks each column in grid for repeating digits
     * @param gridText
     * @return true for every valid position, false for illegal moves
     */
    private static boolean[][] checkColumn(String[][] gridText) {
        // Set each element in array to true
        boolean[][] result = initialiseBooleanArray();
        // Go through every column...
        for (int column = 0; column < 9; column++) {
            // ... and each square in that column ...
            for (int element = 0; element < 9; element++) {
                // ... and compare with every other grid square
                for (int element1 = element + 1; element1 < 9; element1++) {
                    // if they are equal and not equal to 0...
                    if (!(gridText[column][element].equals("")) &&
                            gridText[column][element].equals(gridText[column][element1])) {
                        result[column][element] = false;
                        result[column][element1] = false;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Checks each row in grid for repeating digits
     * @param gridText
     * @return true for every valid position, false for illegal moves
     */
    private static boolean[][] checkRow(String[][] gridText) {
        // Set each element in array to true
        boolean[][] result = initialiseBooleanArray();
        // go through each row
        for (int row = 0; row < 9; row++) {
            // go through each grid square
            for (int element = 0; element < 9; element++) {
                // and compare with every other grid square
                for (int element1 = element + 1; element1 < 9; element1++) {
                    // if they are equal and not equal to 0...
                    if (!(gridText[element][row].equals("")) &&
                            gridText[element][row].equals(gridText[element1][row])) {
                        result[element][row] = false;
                        result[element1][row] = false;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Check each small grid for repeating digits
     * @param gridText
     * @return true for every valid position, false for illegal moves
     */
    private static boolean[][] checkGrids(String[][] gridText) {

        // A grid array stores the values of each square
        // and splits it into their corresponding grid
        String[][] grid = populateGridArray(gridText);

        boolean[][] gridResult = initialiseBooleanArray();

        // Go through each small sudoku grid...
        for (int gridNumber = 0; gridNumber < 9; gridNumber++) {
            // ... and through each square that grid
            for (int square = 0; square < 9; square++) {
                // compare text with every other square in the grid
                for (int otherSquare = square + 1; otherSquare < 9; otherSquare++) {
                    if (!grid[gridNumber][square].equals("") &&
                            grid[gridNumber][square].equals(grid[gridNumber][otherSquare])) {
                        gridResult[gridNumber][square] = false;
                        gridResult[gridNumber][otherSquare] = false;
                    }
                }
            }
        }

        //convert gridArray result to button array result
        boolean[][] result = convertToButtonArray(gridResult);

        return result;
    }

    /**
     * This method takes the value from every square on the grid
     * and sort them into an array for every small grid.
     * @param gridText
     * @return
     */
    private static String[][] populateGridArray(String[][] gridText) {
        // This grid array stores the values of each square
        // and splits it into their corresponding grid
        String[][] grid = new String[9][9];

        // Keep track of the grid number we're on
        int gridCount = 0;
        // for loop iterating to the first square in each small sudoku grid
        for (int column = 0; column < 9; column+=3) {
            for (int row = 0; row < 9; row+=3) {

                //  Keep track of the square number we're on
                int squareCount =0;
                // for loop iterating through each square in small sudoku grid
                for (int columnExtra = 0; columnExtra < 3; columnExtra++) {
                    for (int rowExtra = 0; rowExtra < 3; rowExtra++) {
                        // add square to array
                        grid[gridCount][squareCount] = gridText[column+columnExtra][row+rowExtra];

                        // increment square count each time this for each
                        squareCount++;
                    }
                }

                // increment grid count
                gridCount++;
            }
        }

        return grid;
    }

    /**
     * Take results in the grid array format and convert back to the straight button
     * array format.
     * @param gridResult
     * @return
     */
    private static boolean[][] convertToButtonArray(boolean[][] gridResult) {
        boolean[][] result = new boolean[9][9];

        // Keep track of the grid number we're on
        int gridCount = 0;
        // for loop iterating to the first square in each small sudoku grid
        for (int column = 0; column < 9; column+=3) {
            for (int row = 0; row < 9; row+=3) {

                //  Keep track of the square number we're on
                int squareCount =0;
                // for loop iterating through each square in small sudoku grid
                for (int columnExtra = 0; columnExtra < 3; columnExtra++) {
                    for (int rowExtra = 0; rowExtra < 3; rowExtra++) {
                        // add square to array
                        result[gridCount][squareCount] = gridResult[column+columnExtra][row+rowExtra];

                        // increment square count each time this for each
                        squareCount++;
                    }
                }

                // increment grid count
                gridCount++;
            }
        }

        return result;
    }

    /**
     * This creates a 2d boolean array and sets every value to true;
     * @return
     */
    private static boolean[][] initialiseBooleanArray() {
        boolean[][] result = new boolean[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                result[i][j] = true;
            }
        }
        return result;
    }
}
