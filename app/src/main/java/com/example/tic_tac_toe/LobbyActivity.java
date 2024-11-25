package com.example.tic_tac_toe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LobbyActivity extends AppCompatActivity {

    Button buttonServer1;
    Button buttonServer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lobby);

        buttonServer1 = findViewById(R.id.btn_server_1);
        buttonServer2 = findViewById(R.id.btn_server_2);
    }

    public void goToServer1(View view) {
        Intent server1 = new Intent(getApplicationContext(), MainActivity.class);
        server1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(server1);
    }

    public void goToServer2(View view) {
        Intent server2 = new Intent(getApplicationContext(), MainActivity.class);
        server2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(server2);
    }
}