package com.jon.sudoku;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * This class handles the main Sudoku activity
 */
public class Sudoku extends AppCompatActivity {

    // Button array keeping every button on the grid
    private Button[][] gridSquares = new Button[9][9];

    // Difficulty level
    // 0 = easy, 1 = medium, 2 = hard
    private int difficulty = 0;

    // When true, the game will show invalid moves
    private boolean hints = true;

    // Stores the highlighted button
    private Button highlighted = null;

    // True when player wins
    private boolean win = false;

    /**
     * This method is called when the activity starts.
     * It draws the sudoku grid then starts the game.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the sudoku grid linear layout
        LinearLayout sudokuGrid = (LinearLayout) findViewById(R.id.sudokuGrid);

        // Draw the lines of the sudoku grid
        drawSudokuGrid(sudokuGrid);

        startGame();
    }

    /**
     * Creates the options menu on the app bar
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }

    /**
     * Called when new game is clicked. It opens dialog to confirm that user wants to play again.
     *
     * @param item
     */
    public void onClickNewGame(MenuItem item) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.start_new_game)
                .setPositiveButton(R.string.new_game, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startGame();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    /**
     * When hint checkbox is clicked, hints are enabled and disabled
     *
     * @param item
     */
    public void onClickHints(MenuItem item) {

        // enable check box functionality
        if (item.isChecked()) {
            item.setChecked(false);
            hints = false;
        } else {
            item.setChecked(true);
            hints = true;
        }
    }

    /**
     * Calls a dialog which asks users to select a difficulty.
     */
    private void startGame() {

        win = false;

        //Ask user what difficulty they would like
        new AlertDialog.Builder(this)
                .setTitle(R.string.pick_difficulty)
                .setItems(R.array.colors_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //set difficulty level
                        difficulty = which;

                        //clear the grid of text and color
                        clearGrid();

                        // Populate sudoku with numbers
                        populateGrid(GeneratePuzzle.generatePuzzle(difficulty));
                    }
                })
                .setCancelable(false)
                .show();
    }

    /**
     * Clears the text and color of each button on the sudoku grid
     */
    private void clearGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gridSquares[i][j].setText("");
                gridSquares[i][j].setBackgroundColor(Color.TRANSPARENT);
                gridSquares[i][j].setClickable(true);
            }
        }
    }

    /**
     * This method draws the sudoku grid
     *
     * @param sudokuGrid
     */
    private void drawSudokuGrid(LinearLayout sudokuGrid) {

        // Create 9 columns
        for (int i = 0; i < 9; i++) {
            LinearLayout column = createLayout(LinearLayout.VERTICAL);

            // Create 9 Buttons
            for (int j = 0; j < 9; j++) {

                // Create single grid square
                Button button = (Button) createButton(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        1
                );
                button.setBackgroundColor(Color.TRANSPARENT);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gridSquareClick((Button) view);
                    }
                });

                //Save button to gridSquares array
                gridSquares[i][j] = button;

                column.addView(button);

                if (j == 2 || j == 5) {
                    // add 2 large horizontal lines
                    column.addView(createHorizontalLine());
                } else if (j < 8) {
                    // add small horizontal lines
                    column.addView(createSmallHorizontalLine());
                }
            }
            sudokuGrid.addView(column);
            if (i == 2 || i == 5) {
                // add 2 large vertical lines
                sudokuGrid.addView(createVerticalLine());
            } else if (i < 8) {
                // add small Vertical lines
                sudokuGrid.addView(createSmallVerticalLine());
            }
        }
    }

    /**
     * Populates the grid with permanent puzzle digits
     *
     * @param puzzle
     */
    private void populateGrid(int[][] puzzle) {

        //For each grid square
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!(puzzle[i][j] == 0)) {
                    gridSquares[i][j].setText("" + puzzle[i][j]);
                    gridSquares[i][j].setBackgroundColor(getResources().getColor(R.color.set_squares));
                    gridSquares[i][j].setClickable(false);
                }
            }
        }
    }

    /**
     * Creates a button with the given parameters
     *
     * @param width
     * @param height
     * @param weight
     * @return
     */
    private Button createButton(int width, int height, int weight) {
        Button button = new Button(this);
        button.setLayoutParams(new LinearLayout.LayoutParams(
                width,
                height,
                weight
        ));
        return button;
    }

    /**
     * Creates a small vertical line
     *
     * @return
     */
    private View createSmallVerticalLine() {
        View verticalLine = new View(this);
        verticalLine.setLayoutParams(new LinearLayout.LayoutParams(
                (int) getResources().getDimension(R.dimen.small_line_width),
                ViewGroup.LayoutParams.MATCH_PARENT,
                0));
        verticalLine.setBackgroundColor(getResources().getColor(R.color.colorSmallLines));
        return verticalLine;
    }

    /**
     * Creates a small horizontal line
     *
     * @return
     */
    private View createSmallHorizontalLine() {
        View horizontalLine = new View(this);
        horizontalLine.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) getResources().getDimension(R.dimen.small_line_width),
                0));
        horizontalLine.setBackgroundColor(getResources().getColor(R.color.colorSmallLines));
        return horizontalLine;
    }

    /**
     * Creates a large vertical line
     *
     * @return
     */
    private View createVerticalLine() {
        View verticalLine = new View(this);
        verticalLine.setLayoutParams(new LinearLayout.LayoutParams(
                (int) getResources().getDimension(R.dimen.large_line_width),
                ViewGroup.LayoutParams.MATCH_PARENT,
                0));
        verticalLine.setBackgroundColor(getResources().getColor(R.color.colorLargeLines));
        return verticalLine;
    }

    /**
     * Creates a large horizontal line
     *
     * @return
     */
    private View createHorizontalLine() {
        View horizontalLine = new View(this);
        horizontalLine.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) getResources().getDimension(R.dimen.large_line_width),
                0));
        horizontalLine.setBackgroundColor(getResources().getColor(R.color.colorLargeLines));
        return horizontalLine;
    }

    /**
     * Create layout to hold grid buttons
     *
     * @param orientation
     * @return
     */
    private LinearLayout createLayout(int orientation) {
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                1));
        layout.setOrientation(orientation);
        return layout;
    }

    /**
     * This is called when a gridSquare is selected.
     * Highlight the new selected square
     *
     * @param button
     */
    public void gridSquareClick(Button button) {

        // if game is won then break out method
        if (win) {
            return;
        }

        // Set highlighted to button clicked
        highlighted = button;

        // Update grid colors
        updateGridColors();
    }

    /**
     * This method is called when a number is selected.
     * Highlighted button is given number selected.
     * If hints are enabled, check for illegal moves
     *
     * @param view
     */
    @SuppressWarnings("unused") //method is called directly from XML
    public void numberSelected(View view) {

        // if game is won, then break out method
        if (win) {
            return;
        }

        // get number from button
        String text = ((Button) view).getText().toString();

        // if a button is highlighted
        if (!(highlighted == null)) {
            // if CLR is selected, clear button text
            if (text.equals("CLR")) {
                highlighted.setText("");
            } else {
                //else set text in grid square to button pressed
                highlighted.setText(text);
            }
        }

        updateGridColors();

        checkForWin();
    }

    /**
     * Clear board of colors and recolor elements (e.g. selected grid, errors)
     */
    private void updateGridColors() {

        //Clear background color of all squares
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gridSquares[i][j].setBackgroundColor(Color.TRANSPARENT);
            }
        }

        // reset fixed digits
        populateGrid(GeneratePuzzle.generatePuzzle(difficulty));

        //highlight selected button
        highlighted.setBackgroundColor(getResources().getColor(R.color.highlightedSquare));

        // if hints are enabled, check for illegal moves to highlight errors
        if (hints) {
            checkIllegalMoves();
        }
    }

    /**
     * This method checks that all numbers on the grid are valid moves.
     * It calls
     */
    private void checkIllegalMoves() {
        // If the number selected is not a legal selection
        boolean[][] legalMoves = Logic.isLegalMove(gridSquares);

        // go through each button and check legal move
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // if false ...
                if (!legalMoves[i][j]) {
                    // ... Highlight errors dependant on the type of square it is
                    if (gridSquares[i][j] == highlighted) {
                        gridSquares[i][j].setBackgroundColor(getResources().getColor(R.color.illegalMove));
                    } else if (gridSquares[i][j].isClickable()) {
                        gridSquares[i][j].setBackgroundColor(getResources().getColor(R.color.conflictingMove));
                    } else {
                        gridSquares[i][j].setBackgroundColor(getResources().getColor(R.color.conflicting_set_squares));
                    }
                }
            }
        }

    }

    /**
     * Checks if all grid squares are filled in and there are no conflicts
     */
    private void checkForWin() {
        win = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (((ColorDrawable) gridSquares[i][j].getBackground()).equals(getResources().getColor(R.color.illegalMove)) ||
                        ((ColorDrawable) gridSquares[i][j].getBackground()).equals(getResources().getColor(R.color.conflictingMove)) ||
                        ((ColorDrawable) gridSquares[i][j].getBackground()).equals(getResources().getColor(R.color.conflicting_set_squares)) ||
                        gridSquares[i][j].getText().equals("")) {
                    win = false;
                }
            }
        }
        if (win) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.win_title)
                    .setMessage(R.string.win_message)
                    .setPositiveButton("New Game", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startGame();
                        }
                    })
                    .show();
        }
    }
}
