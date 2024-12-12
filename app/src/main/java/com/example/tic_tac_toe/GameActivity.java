package com.example.tic_tac_toe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity that manages the Tic Tac Toe game logic, including handling player moves,
 * checking for a winner, updating the game board, and managing the score.
 */
public class GameActivity extends AppCompatActivity {

    // Database object to manage game state.
    private Database myDatabase;

    // TextView that displays the current game status (e.g., whose turn it is or who won).
    private TextView status;

    // Boolean flag to indicate if the game is active or not.
    private boolean gameActive = true;

    // Represents the currently active player (0 for X, 1 for O).
    private int activePlayer = 0;

    // 2D array representing the possible win positions on the Tic Tac Toe board.
    private final int[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};

    // Counter to track the number of taps on the board.
    private static int counter = 0;

    // ImageView object representing the tapped grid cell.
    private ImageView img;

    /**
     * Initializes the game activity, sets up necessary components, and loads the game state.
     *
     * @param savedInstanceState The previously saved instance state, if available.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        status = findViewById(R.id.status);
        myDatabase = new Database();
    }

    /**
     * Navigates to the score page where the player can view the game statistics.
     *
     * @param view The view that triggered the navigation to the score page.
     */
    public void goToScorePage(View view) {
        Intent score = new Intent(getApplicationContext(), MainActivity.class);
        score.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(score);
    }

    /**
     * Resets the game, clearing the game state and updating the board.
     *
     * @param view The view that triggered the reset action.
     */
    public void gameReset(View view) {
        gameActive = true;
        activePlayer = 0;

        // Set all positions to null (state = 2)
        myDatabase.resetCurrentGameState();

        // Remove all images from the grid
        ((ImageView) findViewById(R.id.block1)).setImageResource(0);
        ((ImageView) findViewById(R.id.block2)).setImageResource(0);
        ((ImageView) findViewById(R.id.block3)).setImageResource(0);
        ((ImageView) findViewById(R.id.block4)).setImageResource(0);
        ((ImageView) findViewById(R.id.block5)).setImageResource(0);
        ((ImageView) findViewById(R.id.block6)).setImageResource(0);
        ((ImageView) findViewById(R.id.block7)).setImageResource(0);
        ((ImageView) findViewById(R.id.block8)).setImageResource(0);
        ((ImageView) findViewById(R.id.block9)).setImageResource(0);

        // Set initial status text
        status.setText(getString(R.string.x_turn));
    }

    /**
     * Handles the player's tap on a grid cell. Updates the game state, checks for a winner,
     * and updates the screen accordingly.
     *
     * @param view The view (Tic Tac Toe grid cell) that was tapped.
     */
    public void playerTap(View view) {
        img = (ImageView) view;

        // If the game is not active, reset the game
        if (!gameActive) {
            gameReset(view);
            // Reset the tap counter
            counter = 0;
        }
        updatePositions();
        checkIfSomeoneHasWon();
    }

    /**
     * Updates the UI by adding a translation effect to the tapped image, changing the image to
     * reflect the active player (X or O).
     */
    private void updateUserScreen() {
        // This will give a motion effect to the image
        img.setTranslationY(-1000f);
        img.setImageResource(activePlayer == 0 ? R.drawable.x : R.drawable.o);
        img.animate().translationYBy(1000f).setDuration(300);
    }

    /**
     * Updates the positions on the game board, marks the tapped cell, and switches the active player.
     */
    private void updatePositions() {
        int tappedImage = Integer.parseInt(img.getTag().toString());

        // If the tapped image is empty (state is 2)
        if (myDatabase.getCurrentGameState()[tappedImage] == 2) {

            // Increase the counter after each tap
            counter++;

            // Mark the position in the Firebase database
            myDatabase.setGameState(tappedImage, activePlayer);

            // Update the UI to reflect the new state
            updateUserScreen();

            // Change the status text based on the active player
            if (gameActive) {
                activePlayer = (activePlayer == 0) ? 1 : 0;
                status.setText(activePlayer == 0 ? getString(R.string.x_turn) : getString(R.string.o_turn));
            }
        }
    }

    /**
     * Updates the score for the specified player.
     *
     * @param player The player for whom the score should be updated (e.g., "score_playerA").
     */
    private void updateScore(String player) {
        SharedPreferences prefs = getSharedPreferences("game_prefs", MODE_PRIVATE);
        int currentScore = prefs.getInt(player, 0);
        currentScore++;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(player, currentScore);
        editor.apply();
    }


    /**
     * Checks if any player has won the game based on the current game state.
     * The function checks for winning combinations on the board.
     */
    private void checkIfSomeoneHasWon() {
        // A minimum of 5 taps are needed to declare a winner
        if (counter > 4) {
            for (int[] winPosition : winPositions) {
                if (myDatabase.getCurrentGameState()[winPosition[0]] == myDatabase.getCurrentGameState()[winPosition[1]] &&
                        myDatabase.getCurrentGameState()[winPosition[1]] == myDatabase.getCurrentGameState()[winPosition[2]] &&
                        myDatabase.getCurrentGameState()[winPosition[0]] != 2) {

                    // Game over if someone wins
                    gameActive = false;
                    if (myDatabase.getCurrentGameState()[winPosition[0]] == 0) {
                        status.setText(getString(R.string.x_wins));
                        updateScore("score_playerA");
                    } else {
                        status.setText(getString(R.string.o_wins));
                        updateScore("score_playerB");
                    }
                }
            }
            // Set the status if the match ends in a draw
            if (counter == 9 && gameActive) {
                gameActive = false;
                status.setText(getString(R.string.match_draw));
            }
        }
    }
}
