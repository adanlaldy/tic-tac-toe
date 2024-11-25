package com.example.tic_tac_toe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class MainActivity extends AppCompatActivity {

    Button buttonStart;
    TextView scorePlayerA;
    TextView scorePlayerB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // init elements from xml
        buttonStart = findViewById(R.id.btn_start);
        scorePlayerA = findViewById(R.id.txt_player_a_score);
        scorePlayerB = findViewById(R.id.txt_player_b_score);

        // button to start a game
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        // init scores for players
        SharedPreferences prefs = getSharedPreferences("game_prefs", MODE_PRIVATE);
        int scoreA = prefs.getInt("score_playerA", 0);
        int scoreB = prefs.getInt("score_playerB", 0);

        // set scores
        scorePlayerA.setText(String.valueOf(scoreA));
        scorePlayerB.setText(String.valueOf(scoreB));
    }

    public void startGame() {
        Intent startGame = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(startGame);
    }
}