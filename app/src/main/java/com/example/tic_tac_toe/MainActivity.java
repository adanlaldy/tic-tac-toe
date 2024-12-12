package com.example.tic_tac_toe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity class handles the main screen of the Tic Tac Toe game,
 * including the display of player scores, starting a new game,
 * clearing the scores, and navigating to other pages like the lobby.
 */
public class MainActivity extends AppCompatActivity {

    // Button that starts the game when clicked.
    Button buttonStart;

    // TextView displaying the score for player A.
    TextView scorePlayerA;

    // TextView displaying the score for player B.
    TextView scorePlayerB;

    // SharedPreferences object for managing persistent game data (e.g., scores).
    SharedPreferences prefs;

    // SharedPreferences.Editor for editing the SharedPreferences data.
    SharedPreferences.Editor editor;

    /**
     * Called when the activity is created. Initializes UI elements, SharedPreferences,
     * and loads the score data from SharedPreferences.
     *
     * @param savedInstanceState The saved instance state, if available.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);  // Enables edge-to-edge layout (no system UI overlap)
        setContentView(R.layout.activity_main);

        // Initialize UI elements from XML layout
        buttonStart = findViewById(R.id.btn_start);
        scorePlayerA = findViewById(R.id.txt_player_a_score);
        scorePlayerB = findViewById(R.id.txt_player_b_score);

        // Initialize SharedPreferences and Editor
        prefs = getSharedPreferences("game_prefs", MODE_PRIVATE);
        editor = prefs.edit();

        // Load and display the scores for both players
        scoreManagement();
    }

    /**
     * Starts the game by launching the GameActivity.
     *
     * @param view The view that triggered this method (typically the start button).
     */
    public void startGame(View view) {
        Intent startGame = new Intent(getApplicationContext(), GameActivity.class);
        startGame.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // Clears the activity stack
        startActivity(startGame);
    }

    /**
     * Manages and updates the displayed scores for both players.
     * Retrieves the current scores from SharedPreferences and sets them in the respective TextViews.
     */
    public void scoreManagement() {
        // Retrieve scores from SharedPreferences
        int scoreA = prefs.getInt("score_playerA", 0);
        int scoreB = prefs.getInt("score_playerB", 0);

        // Display the scores in the respective TextViews
        scorePlayerA.setText(String.valueOf(scoreA));
        scorePlayerB.setText(String.valueOf(scoreB));
    }

    /**
     * Displays a confirmation dialog to the user, asking if they want to clear the scores.
     * If the user confirms, the scores are reset in both SharedPreferences and the TextViews.
     *
     * @param view The view that triggered this method (typically the clear score button).
     */
    public void clearScore(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Do you really want to clear the scores?")
                .setTitle("Warning!")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Reset the scores in SharedPreferences
                    editor.putInt("score_playerA", 0);
                    editor.putInt("score_playerB", 0);
                    editor.apply();

                    // Reset the scores in the TextViews
                    scorePlayerA.setText("0");
                    scorePlayerB.setText("0");
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    /**
     * Navigates to the lobby page where players can join or create a lobby.
     *
     * @param view The view that triggered this method (typically the button for lobby navigation).
     */
    public void goToLobbiesPage(View view) {
        Intent lobbies = new Intent(getApplicationContext(), LobbyActivity.class);
        lobbies.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clears the activity stack
        startActivity(lobbies);
    }
}
