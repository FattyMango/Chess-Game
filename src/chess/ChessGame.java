package chess;

import board.ChessBoard;
import engine.ChessEngine;
import engine.Engine;
import position.*;

import java.util.Objects;
import java.util.Scanner;

public class ChessGame implements Game{
    private Engine chessEngine;
    public ChessGame(Engine engine ){
        setEngine(Objects.requireNonNullElseGet(engine, () -> new ChessEngine(new ChessBoard())));
    }
    @Override
    public void setEngine(Engine engine) {
        this.chessEngine = engine;
    }
    @Override
    public void start() {

        Scanner scanner = new Scanner(System.in);

        while (!chessEngine.isGameOver()){
            try {
                System.out.println();
                printGame();
                printTurn();
                String command = scanner.next();
                String position = scanner.next();
                int [] move = new int[4];
                for (int i =0; i<4;i++)
                    move[i]=(int) position.charAt(i);


                if (!"move".equals(command) || !chessEngine.makeMove(new ChessPosition((move[1]-'0'),(move[0]-'`')),new ChessPosition((move[3]-'0'),(move[2]-'`'))))
                    System.out.println("Invalid move, try again.");
            }
            catch (Exception e){
                System.out.println("Invalid move, try again.");
            }

        }
        printGame();
        printWinner();

    }
    void printGame(){
        for (int i = 8; i>0; i--){
            System.out.print((i) + "|");
            for (int j = 1; j<=8; j++){
                Position position = new ChessPosition(i,j);
                if(chessEngine.getPiece(position) != null)
                    System.out.print(" "+chessEngine.getPiece(position)+" ");
                else
                    System.out.print(" -- ");
            }
            System.out.println();
        }
        System.out.println("  -------------------------------");
        System.out.println("   a   b   c   d   e   f   g   h");
    }
    public void printTurn(){
        System.out.print("Enter next move ");
        if(chessEngine.getTurn())
            System.out.print("(White player): ");
        else
            System.out.print("(Black player): ");
    }
    public void printWinner(){
    String winner = chessEngine.getWinner();
    System.out.println("Game is over.");
    if (winner.equals("Draw"))
        System.out.println("Draw.");
    else
        System.out.println(winner +" player won.");
    }

}
