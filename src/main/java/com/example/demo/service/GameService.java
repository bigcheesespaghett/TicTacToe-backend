package com.example.demo.service;

import com.example.demo.Board;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private Board board;
    private String currentPlayer;
    private boolean gameOver;

    public GameService() {
        this.board = new Board();
        this.currentPlayer = "X";
        this.gameOver = false;
    }

    public String[] getBoard() {
        return board.getBoard();
    }

    public String makeMove(int position) {
        if (gameOver || !board.isSlotAvailable(position)) {
            return "Invalid move!";
        }

        board.makeMove(position, currentPlayer);

        if (board.checkWinner(currentPlayer)) {
            gameOver = true;
            return "Player " + currentPlayer + " wins!";
        }

        if (board.isDraw()) {
            gameOver = true;
            return "It's a draw!";
        }

        currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";
        return "OK";
    }

    public void resetGame() {
        board.resetBoard();
        currentPlayer = "X";
        gameOver = false;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoardObject() {
        return board;
    }
}