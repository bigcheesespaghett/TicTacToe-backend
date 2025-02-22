package com.example.demo;

public class AI {
    private String aiPlayer;
    private String humanPlayer;

    private int maxDepth;

    public AI(String aiPlayer, String humanPlayer, int maxDepth) {
        this.aiPlayer = aiPlayer;
        this.humanPlayer = humanPlayer;
        this.maxDepth = maxDepth;
    }

    public int findBestMove(Board board) {
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;

        for (int move : board.getAvailableMoves()) {
            if (board.isSlotAvailable(move)) {
                board.makeMove(move, aiPlayer);
                int score = minimax(board, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                board.undoMove(move);

                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
            }
        }

        return bestMove;
    }

    private int minimax(Board board, int depth, int alpha, int beta, boolean isMaximizing) {
        if (board.checkWinner(aiPlayer)) return 10 - depth;
        if (board.checkWinner(humanPlayer)) return depth - 10;
        if (board.isFull() || depth == maxDepth) return 0;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int move : board.getAvailableMoves()) {
                // **Kun hvis feltet er ledigt**
                if (board.isSlotAvailable(move)) {
                    board.makeMove(move, aiPlayer);

                    int score = minimax(board, depth + 1, alpha, beta, false);

                    board.undoMove(move);

                    bestScore = Math.max(bestScore, score);
                    alpha = Math.max(alpha, bestScore);

                    if (beta <= alpha) break;
                }
            }
            return bestScore;
        }
        else {
            int bestScore = Integer.MAX_VALUE;
            for (int move : board.getAvailableMoves()) {
                // **Kun hvis feltet er ledigt**
                if (board.isSlotAvailable(move)) {
                    board.makeMove(move, humanPlayer);

                    int score = minimax(board, depth + 1, alpha, beta, true);

                    board.undoMove(move);

                    bestScore = Math.min(bestScore, score);
                    beta = Math.min(beta, bestScore);

                    if (beta <= alpha) break;
                }
            }
            return bestScore;
        }
    }
}
