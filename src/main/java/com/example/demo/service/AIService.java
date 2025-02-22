package com.example.demo.service;

import com.example.demo.AI;
import com.example.demo.Board;
import org.springframework.stereotype.Service;

@Service
public class AIService {
    private AI ai;

    public AIService() {
        this.ai = new AI("O", "X", 5); // AI spiller 'O'
    }

    public String aiMove(Board board) {
        int bestMove = ai.findBestMove(board);

        if (bestMove == -1 || !board.isSlotAvailable(bestMove)) {
            return "No valid moves left!";
        }

        board.makeMove(bestMove, "O");

        if (board.checkWinner("O")) {
            return "AI wins!";
        }

        if (board.isFull()) {
            return "It's a draw!";
        }

        return "OK";
    }
}