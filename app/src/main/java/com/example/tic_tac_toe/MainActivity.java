package com.example.tic_tac_toe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class MainActivity extends AppCompatActivity {

    Button buttonStart;
    TextView scorePlayerA;
    TextView scorePlayerB;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // init elements from xml
        buttonStart = findViewById(R.id.btn_start);
        scorePlayerA = findViewById(R.id.txt_player_a_score);
        scorePlayerB = findViewById(R.id.txt_player_b_score);

        // init prefs and the editor
        prefs = getSharedPreferences("game_prefs", MODE_PRIVATE);
        editor = prefs.edit();

        scoreManagement();
    }

    public void startGame(View view) {
        Intent startGame = new Intent(getApplicationContext(), GameActivity.class);
        startGame.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(startGame);
    }

    public void scoreManagement() {
        // init scores for players
        int scoreA = prefs.getInt("score_playerA", 0);
        int scoreB = prefs.getInt("score_playerB", 0);

        // set scores in the textViews
        scorePlayerA.setText(String.valueOf(scoreA));
        scorePlayerB.setText(String.valueOf(scoreB));
    }

    public void clearScore(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Do you really want to clear the scores?")
                .setTitle("Warning!")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // clear scores in prefs
                    editor.putInt("score_playerA", 0);
                    editor.putInt("score_playerB", 0);
                    editor.apply();

                    // clear scores in the textViews
                    scorePlayerA.setText("0");
                    scorePlayerB.setText("0");
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    public void goToLobbiesPage(View view) {
        Intent lobbies = new Intent(getApplicationContext(), LobbyActivity.class);
        lobbies.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(lobbies);
    }
}