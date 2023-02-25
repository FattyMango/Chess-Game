import board.ChessBoard;
import chess.*;
import engine.ChessEngine;

public class Main {
    public static void main(String[] args) {

    new ChessGame(new ChessEngine(new ChessBoard())).start();

    }
}