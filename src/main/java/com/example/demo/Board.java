
package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private String[] board;
    private final int SIZE = 9;

    // Constructor - Initialiserer boardet
    public Board() {
        board = new String[SIZE];
        for (int i = 0; i < SIZE; i++) {
            board[i] = String.valueOf(i + 1);
        }
    }

    public boolean isFull() {
        for (String cell : board) {
            if (cell.matches("[1-9]")) return false;
        }
        return true;
    }

    public boolean checkWinner(String player) {
        int[][] winPatterns = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
                {0, 4, 8}, {2, 4, 6}  // Diagonals
        };
        for (int[] pattern : winPatterns) {
            if (board[pattern[0]].equals(player) &&
                    board[pattern[1]].equals(player) &&
                    board[pattern[2]].equals(player)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSlotAvailable(int index) {
        return board[index].matches("[1-9]");
    }

    public boolean isDraw() {
        return Arrays.stream(board).noneMatch(s -> s.matches("[1-9]"));
    }

    public List<Integer> getAvailableMoves() {
        List<Integer> moves = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            if (isSlotAvailable(i)) moves.add(i);
        }
        return moves;
    }

    public void resetBoard() {
        for (int i = 0; i < board.length; i++) {
            board[i] = String.valueOf(i + 1);
        }
    }

    public void makeMove(int index, String player) {

        if (isSlotAvailable(index)) {
            board[index] = player;
        }
    }

    public void undoMove(int index) {
        board[index] = String.valueOf(index + 1);
    }

    public String[] getBoard() {
        return board;
    }
}