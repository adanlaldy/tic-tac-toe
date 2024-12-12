package com.example.tic_tac_toe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

/**
 * This activity represents the lobby screen where users can choose
 * between different game servers or actions. It provides methods to
 * navigate to different parts of the app.
 */
public class LobbyActivity extends AppCompatActivity {

    /**
     * Called when the activity is created.
     * Initializes the activity's content and enables edge-to-edge display.
     *
     * @param savedInstanceState The saved state of the activity, if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enable edge-to-edge display mode
        setContentView(R.layout.activity_lobby); // Set the content view to the lobby layout
    }

    /**
     * Navigates the user to the first game server (Server 1) activity.
     * Clears the activity stack to ensure the user cannot return to the lobby.
     *
     * @param view The view that was clicked to trigger this action.
     */
    public void goToServer1(View view) {
        Intent server1 = new Intent(getApplicationContext(), MainActivity.class);
        server1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear activity stack
        startActivity(server1); // Start the server 1 activity
    }

    /**
     * Navigates the user to the second game server (Server 2) activity.
     * Clears the activity stack to ensure the user cannot return to the lobby.
     *
     * @param view The view that was clicked to trigger this action.
     */
    public void goToServer2(View view) {
        Intent server2 = new Intent(getApplicationContext(), MainActivity.class);
        server2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear activity stack
        startActivity(server2); // Start the server 2 activity
    }
}
