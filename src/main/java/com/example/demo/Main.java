/*package com.example.demo;

import com.example.demo.AI;
import com.example.demo.Board;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Board board = new Board();
        AI ai = new AI("O", "X", 5);

        System.out.println("Welcome to Tic-Tac-Toe!");
        board.printBoard();

        while (true) {
            // Player's turn
            System.out.print("Your turn! Enter a slot number (1-9): ");
            int numInput;

            try {
                numInput = scanner.nextInt();
                if (numInput < 1 || numInput > 9 || !board.getBoard()[numInput - 1].matches("[1-9]")) {
                    System.out.println("Invalid input! Try again.");
                    continue;
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Try again.");
                scanner.next(); // Clear input buffer
                continue;
            }

            board.makeMove(numInput - 1, "X");
            board.printBoard();

            if (board.checkWinner("X")) {
                System.out.println("Congratulations! You win!");
                break;
            }
            if (board.isFull()) {
                System.out.println("It's a draw!");
                break;
            }

            // AI's turn
            System.out.println("AI is thinking...");
            int aiMove = ai.findBestMove(board);
            board.makeMove(aiMove, "O");
            board.printBoard();

            if (board.checkWinner("O")) {
                System.out.println("AI wins! Better luck next time.");
                break;
            }
            if (board.isFull()) {
                System.out.println("It's a draw!");
                break;
            }
        }
        scanner.close();
    }
}*/