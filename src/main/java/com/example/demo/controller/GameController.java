package com.example.demo.controller;

import com.example.demo.AI;
import com.example.demo.Board;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GameController {
    private String currentPlayer = "X";
    private boolean gameOver = false;
    private Board boardObject = new Board();
    private AI ai = new AI("O", "X", 5);

    @GetMapping("/board")
    public String[] getBoard() {
        return boardObject.getBoard();
    }


    @PostMapping("/move")
    public String makeMove(@RequestParam int position) {
        if (gameOver || !boardObject.getBoard()[position].matches("[1-9]")) {
            return "Invalid move!";
        }

        boardObject.getBoard()[position] = currentPlayer;
        boardObject.makeMove(position, currentPlayer); // Opdater Board objektet

        if (checkWinner(currentPlayer)) {
            gameOver = true;
            return "Player " + currentPlayer + " wins!";
        }

        if (Arrays.stream(boardObject.getBoard()).noneMatch(s -> s.matches("[1-9]"))) {
            gameOver = true;
            return "It's a draw!";
        }

        // Skift til AI's tur
        currentPlayer = "O";
        return "OK";
    }
    @PostMapping("/ai-move")
    public String aiMove() {
        if (gameOver) return "Game over!";
        if (!currentPlayer.equals("O")) return "Not AI's turn!";

        int bestMove = ai.findBestMove(boardObject);

        if (bestMove == -1 || !boardObject.isSlotAvailable(bestMove)) {
            return "No valid moves left!";
        }

        boardObject.makeMove(bestMove, "O");

        if (checkWinner("O")) {
            gameOver = true;
            return "AI wins!";
        }

        if (Arrays.stream(boardObject.getBoard()).noneMatch(s -> s.matches("[1-9]"))) {
            gameOver = true;
            return "It's a draw!";
        }

        currentPlayer = "X";
        return "OK";
    }


   @GetMapping("/reset")
   public String resetGame() {
       boardObject.resetBoard();
       currentPlayer = "X";
       gameOver = false;
       return "Game reset!";
   }


    private boolean checkWinner(String player) {
        int[][] winPatterns = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
                {0, 4, 8}, {2, 4, 6}  // Diagonals
        };
        for (int[] pattern : winPatterns) {
            if (boardObject.getBoard()[pattern[0]].equals(player) &&
                    boardObject.getBoard()[pattern[1]].equals(player) &&
                    boardObject.getBoard()[pattern[2]].equals(player)) {
                return true;
            }
        }
        return false;
    }

   private int findBestMove() {
       int bestScore = Integer.MIN_VALUE;
       int bestMove = -1;

       for (int i = 0; i < 9; i++) {
           if (boardObject.getBoard()[i].matches("[1-9]")) {
               boardObject.getBoard()[i] = "O";
               int score = minimax(false);
               boardObject.getBoard()[i] = String.valueOf(i + 1);

               if (score > bestScore) {
                   bestScore = score;
                   bestMove = i;
               }
           }
       }
       return bestMove;
   }
    private int minimax(boolean isMaximizing) {
        if (checkWinner("O")) return 10;  // AI wins
        if (checkWinner("X")) return -10; // Player wins
        if (Arrays.stream(boardObject.getBoard()).noneMatch(s -> s.matches("[1-9]"))) return 0; // Draw

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 9; i++) {
            if (boardObject.getBoard()[i].matches("[1-9]")) {
                boardObject.getBoard()[i] = isMaximizing ? "O" : "X";
                int score = minimax(!isMaximizing);
                boardObject.getBoard()[i] = String.valueOf(i + 1);

                if (isMaximizing) {
                    bestScore = Math.max(bestScore, score);
                } else {
                    bestScore = Math.min(bestScore, score);
                }
            }
        }
        return bestScore;
    }
}