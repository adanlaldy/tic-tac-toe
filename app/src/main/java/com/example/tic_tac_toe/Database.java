package com.example.tic_tac_toe;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This class manages the game's state, both locally and on Firebase.
 * It provides methods to retrieve and update the game state, as well as to reset it.
 */
public class Database {

    // Local array to store the current game state (0 for X, 1 for O, 2 for empty)
    private final int[] currentGameState;

    // Firebase reference to store the game state
    private final DatabaseReference gameState;

    /**
     * Returns the current game state stored locally as an array of integers.
     * Each index in the array represents a cell in the Tic-Tac-Toe grid.
     *
     * @return An array representing the current state of the game (0 = X, 1 = O, 2 = empty).
     */
    public int[] getCurrentGameState() {
        return currentGameState;
    }

    /**
     * Resets the current game state to the initial state where all positions are empty (value 2).
     * This method updates the local game state array.
     */
    public void resetCurrentGameState() {
        Arrays.fill(currentGameState, 2); // Fill the game state array with 2 (empty) values
    }

    /**
     * Constructor that initializes Firebase and sets the initial game state in both
     * the local game state array and the Firebase database.
     * It creates a "game_state" reference on Firebase and initializes it with 9 empty spots (value 2).
     */
    public Database() {
        // Initializing Firebase database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        gameState = database.getReference("game_state"); // Reference to the game state in Firebase

        // Initialize the game state with 9 empty positions (2 represents empty)
        Map<String, Integer> initialState = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            initialState.put(String.valueOf(i), 2);  // Set every position to 2 (empty)
        }
        gameState.setValue(initialState); // Push initial game state to Firebase

        // Initialize the local game state with empty positions (2 represents empty)
        currentGameState = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2};
    }

    /**
     * Sets the game state at a specific position for the active player, both locally and on Firebase.
     *
     * @param index        The index of the position in the Tic-Tac-Toe grid (0-8).
     * @param activePlayer The active player making the move (0 for X, 1 for O).
     */
    public void setGameState(int index, int activePlayer) {
        currentGameState[index] = activePlayer; // Update local game state
        gameState.child(String.valueOf(index)).setValue(activePlayer); // Update Firebase game state
    }
}
